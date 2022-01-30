package bifast.inbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

//	@Query(value = "select nextval('kc_inboundseq')", nativeQuery = true)
//	Long getKomiSequence();
	
	public Optional<CorebankTransaction> findByTransactionTypeAndKomiTrnsId (String trnsType, String komiTrnsId);

}
