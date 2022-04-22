package application.repository;

import application.entity.DailyIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyIncomeRepository extends JpaRepository<DailyIncome, Long> {

    List<DailyIncome> findDailyIncomesByDateBetween(Date dateStart, Date dateStop);
}
