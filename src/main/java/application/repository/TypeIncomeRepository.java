package application.repository;

import application.entity.TypeIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeIncomeRepository extends JpaRepository<TypeIncome, Long> {
}
