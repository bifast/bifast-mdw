package komi.control.rekon;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import komi.control.repository.CorebankTransactionRepository;

@Component
public class RekonProcessor implements Processor {
	@Autowired CorebankTransactionRepository corebankRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		String tgl = exchange.getMessage().getHeader("date", String.class);
		
		
	}

}
