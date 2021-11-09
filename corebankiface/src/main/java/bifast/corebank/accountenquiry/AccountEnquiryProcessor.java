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
		
		
		CbAccountEnquiryResponsePojo response = new CbAccountEnquiryResponsePojo();
		response.setKomiTrnsId(aeRequest.getKomiTrnsId());
		response.setCategoryPurpose(aeRequest.getCategoryPurpose());
		response.setAccountNumber(aeRequest.getAccountNumber());
		response.setAccountType("CACC");
		response.setCreditorId("995955858");
		response.setCreditorName("Bambang");
		response.setCreditorType("01");
		
		response.setReason("U000");
		response.setResidentStatus("01");
		response.setStatus("ACTC");
		response.setTownName("0300");

		exchange.getMessage().setBody(response);
	
	}

}
