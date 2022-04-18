package application.api;

import application.dto.ExpenseRecordDto;
import application.entity.*;
import application.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "expense")
public class ExpenseController {
    private final ExpenseService expenseService;


    @PostMapping(value = "/types")
    public TypeExpense createType(@RequestParam String name){
        return expenseService.createType(name);
    }

    @DeleteMapping("/types")
    public void deleteType(@RequestParam Long id){
        expenseService.deleteType(id);
    }

    @GetMapping("/alltypes")
    public List<TypeExpense> getAllTypes()
    {
        return expenseService.getAllTypes();
    }

    @PostMapping("/updatetype")
    public TypeExpense updateType(@RequestParam Long id, String name){
        return expenseService.updateType(id, name);
    }

    @GetMapping("/gettype")
    public TypeExpense getType(@RequestParam Long id){
        return expenseService.getType(id);
    }

    //Работа с затратами
    @PostMapping
    public void createExpense(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, Long typeid, Double summ, String comment, Long accountid){
        expenseService.createExpense(date, typeid, summ, comment, accountid);

    }

    @DeleteMapping
    public void deleteExpense(@RequestParam Long id){
        expenseService.deleteExpense(id);
    }
}
