package bifast.inbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.inbound.model.BankCode;


@Repository
public interface BankCodeRepository extends JpaRepository<BankCode, String> {

	Optional<BankCode> findByBicCode (String bicCode);
	
}
