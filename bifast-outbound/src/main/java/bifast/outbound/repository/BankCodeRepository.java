package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.BankCode;

@Repository
public interface BankCodeRepository extends JpaRepository<BankCode, String> {

	Optional<BankCode> findByBicCode (String bicCode);
	
}
