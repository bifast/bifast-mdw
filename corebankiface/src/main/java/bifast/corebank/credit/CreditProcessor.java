package bifast.corebank.credit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CreditProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {

		CbCreditRequestPojo creditRequest = exchange.getMessage().getBody(CbCreditRequestPojo.class);

		//TODO process Account Balance
		
		
		CbCreditResponsePojo creditResponse = new CbCreditResponsePojo();
		creditResponse.setKomiTrnsId(creditRequest.getKomiTrnsId());
		
		exchange.getMessage().setBody(creditResponse);

	}

}
