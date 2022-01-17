package bifast.outbound.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query(value = "select nextval('corebank_sequence')", nativeQuery = true)
	Long getCorebankSequence();
	
	public List<CorebankTransaction> findAllByTransactionTypeAndKomiTrnsId (String transactionType, String komiTrnsId);
}
