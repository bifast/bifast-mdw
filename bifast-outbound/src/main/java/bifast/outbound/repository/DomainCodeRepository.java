package bifast.outbound.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.DomainCode;


@Repository
public interface DomainCodeRepository extends JpaRepository<DomainCode, Long> {

	public Optional<DomainCode> findByGrpAndValue (String grp, String value);
	public Optional<DomainCode> findByGrpAndKey (String grp, String value);
	public List<DomainCode> findByGrp (String grp);
	
}
