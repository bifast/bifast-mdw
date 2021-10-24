package bifast.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bifast.outbound.model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {

	Optional<Parameter> findByCode (String code);
	Optional<Parameter> findByModuleAndCode (String module, String code);
	
	
}
