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
		
		System.out.println("category " + creditRequest.getCategoryPurpose());
		if (creditRequest.getCategoryPurpose().equals("02")) {
			creditResponse.setStatus("RJCT");
			creditResponse.setReason("U112");
		}
		else {
			creditResponse.setStatus("ACTC");
			creditResponse.setReason("U000");
		}
		
		exchange.getMessage().setBody(creditResponse);

	}

}
