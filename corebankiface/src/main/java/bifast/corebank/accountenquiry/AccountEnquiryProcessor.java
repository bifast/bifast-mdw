package bifast.corebank.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CbAccountEnquiryRequestPojo aeRequest = exchange.getMessage().getBody(CbAccountEnquiryRequestPojo.class);

		//TODO process A-E
		
		
		CbAccountEnquiryResponsePojo aeResponse = new CbAccountEnquiryResponsePojo();
		aeResponse.setKomiTrnsId(aeRequest.getKomiTrnsId());
		
		exchange.getMessage().setBody(aeResponse);
	
	}

}
