package application.api;

import application.entity.Account;
import application.entity.DailyExpense;
import application.entity.TypeExpense;
import application.repository.AccountsRepository;
import application.repository.DailyExpenseRepository;
import application.repository.TypeExpenseRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc moc;

    @Autowired
    private TypeExpenseRepository typeExpenseRepository;

    @Autowired
    private DailyExpenseRepository dailyExpenseRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @AfterEach
    public  void resetDB(){
        dailyExpenseRepository.deleteAll();
        accountsRepository.deleteAll();
        typeExpenseRepository.deleteAll();
    }

    private TypeExpense CreateTypeExpense(String name){
        TypeExpense typeExpense = TypeExpense.builder().name(name).build();
        return typeExpenseRepository.save(typeExpense);
    }

    private DailyExpense CreateDailyExpense(Date date, TypeExpense type, Double sum, String comm)
    {
        DailyExpense dailyExpense = DailyExpense.builder()
                .date(date)
                .comment(comm)
                .summ(sum)
                .type(type).build();

        return dailyExpenseRepository.save(dailyExpense);
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
        moc.perform(post("/expense/types?name=test type expense"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test type expense"));
    }

    @Test
    void deleteType() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 1");
        moc.perform(delete("/expense/types?id={id}", typeExpense.getId())).andExpect(status().isOk());
    }

    @Test
    void getAllTypes() throws Exception  {
        CreateTypeExpense("test type 2");
        CreateTypeExpense("test type 3");
        ResultActions resultActions = moc.perform(get("/expense/alltypes")).andExpect(status().isOk());
    }

    @Test
    void updateType() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 4");
        moc.perform(post("/expense/updatetype?id={id}&name=new name",typeExpense.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new name"))
                .andExpect(jsonPath("$.id").value(typeExpense.getId()));
    }

    @Test
    void getType() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 5");
        moc.perform(get("/expense/gettype?id={id}",typeExpense.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test type 5"))
                .andExpect(jsonPath("$.id").value(typeExpense.getId()));
    }

    @Test
    void createExpense() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 6");
        Account account = CreateTestAccount("test account", 500D);
        moc.perform(post("/expense?date=2022-04-04&typeid={typeid}&summ=200&comment=my com&accountid={accid}", typeExpense.getId(), account.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteExpense() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 6");
        Date date = new Date(System.currentTimeMillis());
        DailyExpense dailyExpense = CreateDailyExpense(date, typeExpense, 500D, "test expense");
        moc.perform(delete("/expense?id={id}", dailyExpense.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getExpenseBetweenDates() throws Exception  {
        TypeExpense typeExpense = CreateTypeExpense("test type 6");
        Account account = CreateTestAccount("test account", 1000D);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formatter.parse("04-04-2022");
        Date date2 = formatter.parse("06-04-2022");
        CreateDailyExpense(date1, typeExpense, 500D, "test expense");
        CreateDailyExpense(date1, typeExpense, 50D, "test expense_1");

        moc.perform(get("/expense/report?dateStart=2022-04-03&dateStop=2022-04-08"))
                .andExpect(status().isOk());
    }
}