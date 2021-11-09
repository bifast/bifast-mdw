package bifast.corebank.credit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CreditProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {

		CbCreditRequestPojo creditRequest = exchange.getMessage().getBody(CbCreditRequestPojo.class);

		CbCreditResponsePojo creditResponse = new CbCreditResponsePojo();
		creditResponse.setKomiTrnsId(creditRequest.getKomiTrnsId());
		
		creditResponse.setAccountNumber(creditRequest.getCreditorAccountNumber());
		creditResponse.setAdditionalInfo("");
		creditResponse.setReason("ACTC");
		creditResponse.setStatus("U000");
		
		exchange.getMessage().setBody(creditResponse);

	}

}
