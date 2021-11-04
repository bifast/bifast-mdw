package bifast.inbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query(value = "select nextval('kc_inboundseq')", nativeQuery = true)
	Long getKomiSequence();

}
