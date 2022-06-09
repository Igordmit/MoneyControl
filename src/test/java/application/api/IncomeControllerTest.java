package application.api;

import application.entity.*;
import application.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IncomeControllerTest {

    @Autowired
    private MockMvc moc;

    @Autowired
    private TypeIncomeRepository typeIncomeRepository;

    @Autowired
    private DailyIncomeRepository dailyIncomeRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @AfterEach
    public  void resetDB(){
        dailyIncomeRepository.deleteAll();
        accountsRepository.deleteAll();
        typeIncomeRepository.deleteAll();
    }

    private TypeIncome CreateTypeIncome(String name){
        TypeIncome typeIncome = TypeIncome.builder().name(name).build();
        return typeIncomeRepository.save(typeIncome);
    }

    private DailyIncome CreateDailyIncome(Date date, TypeIncome type, Double sum, String comm)
    {
        DailyIncome dailyIncome = DailyIncome.builder()
                .date(date)
                .comment(comm)
                .summ(sum)
                .type(type).build();

        return dailyIncomeRepository.save(dailyIncome);
    }

    private Account CreateTestAccount(String name, Double value){
        Account account = Account.builder()
                .name(name)
                .value(value)
                .mainFlag(false)
                .build();

        return accountsRepository.save(account);
    }

    @Test
    void createType() throws Exception {
        moc.perform(post("/income/types?name=test type income"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test type income"));
    }

    @Test
    void deleteType() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 1");
        moc.perform(delete("/income/types?id={id}", typeIncome.getId())).andExpect(status().isOk());
    }

    @Test
    void getAllTypes() throws Exception {
        CreateTypeIncome("test type 2");
        CreateTypeIncome("test type 3");
        ResultActions resultActions = moc.perform(get("/income/alltypes")).andExpect(status().isOk());
    }

    @Test
    void updateType() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 4");
        moc.perform(post("/income/updatetype?id={id}&name=new name",typeIncome.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new name"))
                .andExpect(jsonPath("$.id").value(typeIncome.getId()));
    }

    @Test
    void getType() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 5");
        moc.perform(get("/income/gettype?id={id}",typeIncome.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test type 5"))
                .andExpect(jsonPath("$.id").value(typeIncome.getId()));
    }

    @Test
    void createIncome() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 6");
        Account account = CreateTestAccount("test account", 500D);
        moc.perform(post("/income?date=2022-04-04&typeid={typeid}&summ=200&comment=my com&accountid={accid}", typeIncome.getId(), account.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteIncome() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 6");
        Date date = new Date(System.currentTimeMillis());
        DailyIncome dailyIncome = CreateDailyIncome(date, typeIncome, 500D, "test income");
        moc.perform(delete("/income?id={id}", dailyIncome.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getIncomeBetweenDates() throws Exception {
        TypeIncome typeIncome = CreateTypeIncome("test type 6");
        Account account = CreateTestAccount("test account", 1000D);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formatter.parse("04-04-2022");
        Date date2 = formatter.parse("06-04-2022");
        CreateDailyIncome(date1, typeIncome, 500D, "test income");
        CreateDailyIncome(date1, typeIncome, 50D, "test income_1");

        moc.perform(get("/income/report?dateStart=2022-04-03&dateStop=2022-04-08"))
                .andExpect(status().isOk());
    }
}