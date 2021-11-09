package bifast.corebank.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query(value = "select nextval('corebank_sequence')", nativeQuery = true)
	Long getCorebankSequence();
}
