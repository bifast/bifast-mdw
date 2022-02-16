package komi.control.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import komi.control.model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {

	Optional<Parameter> findByParamname (String code);
	
	
}
