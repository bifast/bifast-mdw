package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;


@Component
public class BuildCBResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CbAccountEnquiryResponsePojo response = new CbAccountEnquiryResponsePojo();
		
		CbAccountEnquiryRequestPojo request = exchange.getMessage().getBody(CbAccountEnquiryRequestPojo.class);
		
		if (request.getAccountNumber().startsWith("11")) {
			response.setAccountNumber(request.getAccountNumber());
			response.setAccountType("CACC");
			response.setCreditorId("22222");
			response.setCreditorName("Johansyah");
			response.setCreditorType("01");
	//		response.setErrorMessage(null);
//			response.setRequestStatus("SUCCESS");
			response.setResidentStatus("01");
			response.setTownName("Jakarta");
			response.setTransactionId(request.getTransactionId());
			
		}
		
		else {
			response.setAccountNumber(request.getAccountNumber());
			response.setAccountType("CACC");
//			response.setAdditionInfo("Rekening bermasalah");
			response.setCreditorId("22222");
			response.setCreditorName("Antonio");
			response.setCreditorType("01");
	//		response.setErrorMessage(null);
//			response.setRequestStatus("SUCCESS");
			response.setResidentStatus("01");
			response.setTownName("Jakarta");
			response.setTransactionId(request.getTransactionId());
		}
		
		exchange.getMessage().setBody(response);
		
	}


}
