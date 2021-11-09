package bifast.corebank.customerinfo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CustomerInfoProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {

		CbCustomerInfoRequestPojo custInfoRequest = exchange.getMessage().getBody(CbCustomerInfoRequestPojo.class);

		//TODO process Account Balance
		
		
		CbCustomerInfoResponsePojo custInfoResponse = new CbCustomerInfoResponsePojo();
		custInfoResponse.setKomiTrnsId(custInfoRequest.getKomiTrnsId());
		
		exchange.getMessage().setBody(custInfoResponse);

	}

}
