package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.AccountEnquiry;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountEnquiryRepository extends JpaRepository<AccountEnquiry, Long> {

    @Query("SELECT accountEnquiry FROM AccountEnquiry accountEnquiry WHERE accountEnquiry.intrRefId =:transactionId and accountEnquiry.accountNo = :accountNumber and accountEnquiry.amount = :amount")
    AccountEnquiry getAccountInquiry(@Param("transactionId") String transactionId,
    						  @Param("accountNumber") String accountNumber,
    						  @Param("amount") BigDecimal amount);
    
    @Query("SELECT accountEnquiry FROM AccountEnquiry accountEnquiry WHERE accountEnquiry.accountNo = :accountNumber ")
    AccountEnquiry getAccountInquiryByAccountNumber(@Param("accountNumber") String accountNumber);

}
