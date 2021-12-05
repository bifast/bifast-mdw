package bifast.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.RejectCode;

@Repository
public interface RejectCodeRepository extends JpaRepository<RejectCode, String> {
	

}
