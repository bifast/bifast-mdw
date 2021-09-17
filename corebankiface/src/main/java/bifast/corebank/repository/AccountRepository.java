package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.Account;
import bifast.corebank.model.AccountEnquiry;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT account FROM Account account WHERE account.accountNo = :accountNumber ")
    Account getAccountByAccountNumber(@Param("accountNumber") String accountNumber);


}
