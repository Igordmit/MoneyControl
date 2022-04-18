package application.repository;

import application.entity.TypeExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeExpenseRepository extends JpaRepository<TypeExpense, Long> {
}
