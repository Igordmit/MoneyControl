package application.api;

import application.entity.TypeIncome;
import application.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "income")
public class IncomeController {

    private final IncomeService incomeService;
    //Работа со статьями дохода
    @PostMapping(value = "/types")
    public TypeIncome createType(@RequestParam String name){
        return incomeService.createType(name);
    }

    @DeleteMapping("/types")
    public void deleteType(@RequestParam Long id){
        incomeService.deleteType(id);
    }

    @GetMapping("/alltypes")
    public List<TypeIncome> getAllTypes()
    {
        return incomeService.getAllTypes();
    }

    @PostMapping("/updatetype")
    public TypeIncome updateType(@RequestParam Long id, String name){
        return incomeService.updateType(id, name);
    }

    @GetMapping("/gettype")
    public TypeIncome getType(@RequestParam Long id){
        return incomeService.getType(id);
    }

    //Работа с доходами
    @PostMapping
    public void createIncome(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, Long typeid, Double summ, String comment, Long accountid){
        incomeService.createIncome(date, typeid, summ, comment, accountid);
    }

    @DeleteMapping
    public void deleteIncome(@RequestParam Long id){
        incomeService.deleteIncome(id);
    }
}
