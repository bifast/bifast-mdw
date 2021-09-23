package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.CbAccount;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<CbAccount, Long> {

	@Query("SELECT a FROM CbAccount a WHERE a.accountNo =:accountNumber ")
    CbAccount getAccountByAccountNumber(@Param("accountNumber") String accountNumber);
	
    @Query("SELECT a FROM CbAccount a WHERE a.intrRefId =:transactionId and a.accountNo = :accountNumber and a.amount = :amount")
    CbAccount getAccountInquiry(@Param("transactionId") String transactionId,
    						  @Param("accountNumber") String accountNumber,
    						  @Param("amount") BigDecimal amount);


}
