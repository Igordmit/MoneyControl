package application.service;

import application.entity.DailyExpense;
import application.entity.DailyIncome;
import application.repository.DailyExpenseRepository;
import application.repository.DailyIncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DailyExpenseRepository dailyExpenseRepository;
    private final DailyIncomeRepository dailyIncomeRepository;

    public List<DailyExpense> getExpenseBetweenDates(Date dateStart, Date dateStop) {
        List<DailyExpense> d = dailyExpenseRepository.findDailyExpensesByDateBetween(dateStart, dateStop);
        return d;
    }

    public List<DailyIncome> getIncomesBetweenDates(Date dateStart, Date dateStop) {
        List<DailyIncome> d = dailyIncomeRepository.findDailyIncomesByDateBetween(dateStart, dateStop);
        return d;
    }
}
