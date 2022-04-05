package komi.control.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query(nativeQuery = true, 
			value = "select cb.komi_noref , "
					+ "e2e_id , "
					+ "cb.cstm_account_no , cb.cstm_account_name , "
					+ "cb.transaction_type , "
					+ "cb.credit_amount , cb.debit_amount , "
					+ "ct.orign_bank , ct.recpt_bank , "
					+ "cb.reason, cb.response  , ct.sttl_bizmsgid "
					+ "from kc_corebank_transaction cb "
					+ "join kc_credit_transfer ct on ct.komi_trns_id = cb.komi_trns_id "
					+ "where cb.trns_date = :trnsDate "
					+ "order by cb.id ")
			public List<Object[]> getCoreBankTransaction (@Param("trnsDate") String trnsDate);
}
