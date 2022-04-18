package application.service;

import application.entity.Account;
import application.entity.DailyIncome;
import application.entity.TypeIncome;
import application.exceptions.InsufficientBalanceException;
import application.repository.DailyIncomeRepository;
import application.repository.TypeIncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final TypeIncomeRepository typeIncomeRepository;
    private final DailyIncomeRepository dailyIncomesRepository;
    private final AccountService accountService;

    //Статьи доходов
    @Transactional
    public TypeIncome createType(String name){
        return typeIncomeRepository.save(TypeIncome.builder()
                .name(name)
                .build());
    }

    @Transactional
    public void deleteType(Long id){
        typeIncomeRepository.delete(getType(id));
    }

    @Transactional
    public List<TypeIncome> getAllTypes() {
        return typeIncomeRepository.findAll();
    }

    @Transactional
    public TypeIncome updateType(Long id, String name){
        TypeIncome updatedTypeExpense = typeIncomeRepository.getById(id);
        updatedTypeExpense.setName(name);
        return typeIncomeRepository.save(updatedTypeExpense);
    }

    @Transactional
    public TypeIncome getType(Long id){
        return typeIncomeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Не найдена статья доходов с идентификатором "+ Long.valueOf(id)));
    }

    //доходы
    @Transactional
    public void createIncome(Date date, Long typeid, Double summ, String comment, Long accountid){

        Account account;
        try {
            account = accountService.get(accountid);
            accountService.increaseValue(account, summ);
        }catch (EntityNotFoundException entityEx){

        }
        TypeIncome typeIncome = getType(typeid);
        dailyIncomesRepository.save(DailyIncome.builder()
                .date(date)
                .type(typeIncome)
                .comment(comment)
                .summ(summ)
                .build());
    }

    @Transactional
    public void deleteIncome(Long id){
        dailyIncomesRepository.deleteById(id);
    }
}
