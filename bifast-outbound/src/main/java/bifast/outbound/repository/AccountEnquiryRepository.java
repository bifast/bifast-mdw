package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.AccountEnquiry;

@Repository
public interface AccountEnquiryRepository extends JpaRepository<AccountEnquiry, Long> {

}
