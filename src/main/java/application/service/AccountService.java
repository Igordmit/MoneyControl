package application.service;

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
    public void moveFunds(Long idfrom, Long idto, Double value){
        Account accountFrom = get(idfrom);
        Account accountTo = get(idto);
        try {
            accountFrom = decreaseValue(accountFrom, value);
        } catch (InsufficientBalanceException e){

        }
        accountTo = increaseValue(accountTo, value);
    }
}
