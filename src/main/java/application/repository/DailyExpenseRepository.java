package application.repository;

import application.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, Long> {
}
