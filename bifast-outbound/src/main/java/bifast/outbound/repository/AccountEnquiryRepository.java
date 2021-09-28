package bifast.outbound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.AccountEnquiry;

@Repository
public interface AccountEnquiryRepository extends JpaRepository<AccountEnquiry, Long> {

	List<AccountEnquiry> findAllByBizMsgIdr (String bizMsgIdr);
	
}
