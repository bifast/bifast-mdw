package komi.control.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import komi.control.model.Parameter;
import komi.control.repository.ParameterRepository;


@Service
public class ParameterService {
	
	@Autowired
	private ParameterRepository parameterRepository;

	
	public Optional<Parameter> findByParamname (String paramname) {
		  
		return parameterRepository.findByParamname(paramname);
	}
	
	

}
