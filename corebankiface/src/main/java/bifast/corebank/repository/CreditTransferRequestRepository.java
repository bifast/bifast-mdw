package bifast.corebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.corebank.model.AccountEnquiry;
import bifast.corebank.model.CreditTransferRequest;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CreditTransferRequestRepository extends JpaRepository<CreditTransferRequest, Long> {


}
