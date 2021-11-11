package bifast.corebank.debitreversal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class DebitReversalProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		CbDebitReversalRequestPojo debitRequest = exchange.getMessage().getBody(CbDebitReversalRequestPojo.class);
		
		//TODO send ke corebank
		
		//TODO save history table
		
		CbDebitReversalResponsePojo debitResponse = new CbDebitReversalResponsePojo();
		debitResponse.setKomiTrnsId(debitRequest.getKomiTrnsId());
		debitResponse.setAccountNumber(debitRequest.getOrgnlAccountNumber());
		debitResponse.setOrgnlKomiTrnsId(debitRequest.getKomiTrnsId());
		debitResponse.setReason("U000");
		debitResponse.setStatus("ACTC");
		
		//TODO build response message
		
		exchange.getMessage().setBody(debitResponse);
	
	}
}
