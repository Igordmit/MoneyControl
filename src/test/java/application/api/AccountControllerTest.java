package application.api;

import application.entity.Account;
import application.repository.AccountsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountsRepository accountsRepository;

    public Account CreateTestAccount(String name, Double value){
        Account account = Account.builder()
                .name(name)
                .value(value)
                .build();

        return accountsRepository.save(account);
    }

    @AfterEach
    public void resetDB(){
        accountsRepository.deleteAll();
    }

    @Test
    void add() throws Exception {
        this.mvc
                .perform(post("/account?name=testAccount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testAccount"))
                .andExpect(jsonPath("$.value").value(Long.valueOf(0L)));
    }

    @Test
    void deleteAcc() throws Exception {
        Account account = CreateTestAccount("test account", 0D);
        this.mvc
                .perform(delete("/account?id={id}", account.getId()))
                .andExpect(status().isOk());
    }


    @Test
    void rename() throws Exception {
        Account account = CreateTestAccount("test account 1", 0D);
        this.mvc
                .perform(post("/account/rename?id={id}&newName=test account 2", account.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test account 2"));
    }

    @Test
    void moveFunds() throws Exception {
        Account accountFrom = CreateTestAccount("test account 2", 1000D);
        Account accountTo = CreateTestAccount("test account 2", 0D);
        this.mvc
                .perform(post("/account/move?idfrom={id1}&idto={id2}&value=500", accountFrom.getId(), accountTo.getId()))
                .andExpect(status().isOk());
    }
}