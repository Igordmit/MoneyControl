package application.service;

import application.dto.AccountsDto;
import application.entity.Account;
import application.exceptions.InsufficientBalanceException;
import application.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountsRepository accountsRepository;

    @Transactional
    public Account add(String name){
        return accountsRepository.save(Account.builder()
                .name(name)
                .mainFlag(false)
                .value(0.0)
                .build());
    }

    @Transactional
    public void delete(Long id){
        accountsRepository.delete(accountsRepository.getById(id));
    }

    @Transactional
    public Account rename(Long id, String name){
        Account account = accountsRepository.getById(id);
        account.setName(name);
        return accountsRepository.save(account);
    }

    @Transactional
    public Account get(Long id){
        return accountsRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Лицевой счет не найден по идентификатору " + String.valueOf(id)));
    }

    @Transactional
    public Account decreaseValue(Account account, Double value) throws InsufficientBalanceException {
        if(account.getValue()<value) throw new InsufficientBalanceException();
        account.setValue(account.getValue() - value);
        return accountsRepository.save(account);
    }

    @Transactional
    public Account increaseValue(Account account, Double value){
        account.setValue(account.getValue() + value);
        return accountsRepository.save(account);
    }

    @Transactional
    public AccountsDto moveFunds(Long idfrom, Long idto, Double value){
//        Account accountFrom = Account.builder().build();
//        Account accountTo = Account.builder().build();
//        try {
//            accountFrom = decreaseValue(idfrom, value);
//        } catch (InsufficientBalanceException e){
           return AccountsDto.builder()
                    .errorName("Недостаточно средств для списания со счета")
                    .build();
//        }
//        //accountTo = increaseTheValue(idto, value);
//        return AccountsDto.builder()
//                .nameFrom(accountFrom.getName())
//                .idfrom(accountFrom.getId())
//                .valueFrom(accountFrom.getValue())
//                .nameto(accountTo.getName())
//                .idto(accountTo.getId())
//                .valueto(accountTo.getValue()).build();
    }
}

//ToDo  Разбить контроллер
// в функции изменения значения счетов передавать сущности, а не id
// Переделать структуру хранения данных по счетам