package application.repository;

import application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    @Query(value = "select * from accounts where main_flag = true", nativeQuery = true)
    Account getMainAccount();
}
