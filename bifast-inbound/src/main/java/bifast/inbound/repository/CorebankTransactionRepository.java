package bifast.inbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query(value="select cbt.komi_noref "
				+ "from kc_corebank_transaction cbt " 
				+ "join kc_credit_transfer ct on ct.komi_trns_id = cbt.komi_trns_id "
				+ "and cbt.transaction_type = 'Debit' "
				+ "where ct.e2e_id = :end2endId", 
			nativeQuery = true)
	public Optional<String> getOrgnlNorefByEndToEndId (@Param("end2endId") String end2endId);
	
	public Optional<CorebankTransaction> findByTransactionTypeAndKomiTrnsId (String trnsType, String komiTrnsId);

}
