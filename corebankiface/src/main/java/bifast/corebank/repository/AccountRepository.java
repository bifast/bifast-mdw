package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.Account;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT account FROM Account account WHERE account.accountNo =:accountNumber ")
    Account getAccountByAccountNumber(@Param("accountNumber") String accountNumber);
	
    @Query("SELECT cb FROM Account cb WHERE cb.intrRefId =:transactionId and cb.accountNo = :accountNumber and cb.amount = :amount")
    Account getAccountInquiry(@Param("transactionId") String transactionId,
    						  @Param("accountNumber") String accountNumber,
    						  @Param("amount") BigDecimal amount);


}
