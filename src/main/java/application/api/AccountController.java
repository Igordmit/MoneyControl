package application.api;

import application.entity.Account;
import application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "account")
public class AccountController {

    private final AccountService accountService;
    //Работа со счетами
    @PostMapping
    public Account add(@RequestParam String name){
        return accountService.add(name);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        accountService.delete(id);
    }

    @PostMapping(value="/rename")
    public Account rename(@RequestParam Long id, String newName) {
        return accountService.rename(id, newName);
    }

//    @PostMapping(value="/move")
//    public void moveFunds(@RequestParam Long idfrom, Long idto, Double value){
//        return accountService.moveFunds(idfrom, idto, value);
//    }
}
