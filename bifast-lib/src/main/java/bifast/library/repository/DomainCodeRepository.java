package bifast.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.library.model.DomainCode;

@Repository
public interface DomainCodeRepository extends JpaRepository<DomainCode, Long> {

	public Optional<DomainCode> findByGrpAndValue (String grp, String value);
	public Optional<DomainCode> findByGrpAndKey (String grp, String value);
	
}
