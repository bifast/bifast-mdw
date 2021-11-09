package bifast.corebank.debit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class DebitProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		CbDebitRequestPojo debitRequest = exchange.getMessage().getBody(CbDebitRequestPojo.class);
		
		//TODO send ke corebank
		
		//TODO save history table
		
		CbDebitResponsePojo debitResponse = new CbDebitResponsePojo();
		debitResponse.setKomiTrnsId(debitRequest.getKomiTrnsId());
		//TODO build response message
		
		exchange.getMessage().setBody(debitResponse);
	
	}
}
