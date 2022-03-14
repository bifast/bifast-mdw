package komi.control.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import komi.control.model.CorebankTransaction;

@Repository
public interface CorebankTransactionRepository extends JpaRepository<CorebankTransaction, Long> {

	@Query("select c FROM CorebankTransaction c category where trnsDate =:trnsDate")
	public List<CorebankTransaction> getCoreBankTransaction (@Param("trnsDate") String trnsDate);
}
