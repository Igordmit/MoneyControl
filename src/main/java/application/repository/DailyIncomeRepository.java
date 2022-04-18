package application.repository;

import application.entity.DailyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyIncomeRepository extends JpaRepository<DailyIncome, Long> {
}
