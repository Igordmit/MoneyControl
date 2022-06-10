package application.service;

import application.entity.Account;
import application.entity.DailyExpense;
import application.entity.TypeExpense;
import application.exceptions.InsufficientBalanceException;
import application.repository.DailyExpenseRepository;
import application.repository.TypeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final TypeExpenseRepository typeExpenseRepository;
    private final DailyExpenseRepository dailyExpenceRepository;
    private final AccountService accountService;

    //Статьи затрат
    @Transactional
    public TypeExpense createType(String name){
        return typeExpenseRepository.save(TypeExpense.builder()
                .name(name)
                .build());
    }

    @Transactional
    public void deleteType(Long id){
        typeExpenseRepository.delete(getType(id));
    }

    @Transactional
    public List<TypeExpense> getAllTypes() {
        return typeExpenseRepository.findAll();
    }

    @Transactional
    public TypeExpense updateType(Long id, String name){
        TypeExpense updatedTypeExpense = getType(id);
        updatedTypeExpense.setName(name);
        return typeExpenseRepository.save(updatedTypeExpense);
    }

    @Transactional
    public TypeExpense getType(Long id){
        return typeExpenseRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Не найдена статья завтрат с идентификатором "+ Long.valueOf(id)));
    }

    @Transactional
    public void createExpense(Date date, Long typeid, Double summ, String comment, Long accountid){

        Account account;
        try {
            account = accountService.get(accountid);
            accountService.decreaseValue(account, summ);
        }catch (InsufficientBalanceException e){
            return;
        }catch (EntityNotFoundException entityEx){
            return;
        }
        TypeExpense typeExpense = getType(typeid);
        dailyExpenceRepository.save(DailyExpense.builder()
                .date(date)
                .type(typeExpense)
                .comment(comment)
                .summ(summ)
                .build());
    }

    @Transactional
    public void deleteExpense(Long id){
        dailyExpenceRepository.deleteById(id);
    }

}
