package bifast.admin.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbAccountEnquiryRequestPojo;
import bifast.admin.pojo.CbAccountEnquiryResponsePojo;

@Component
public class AccountEnquiryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		CbAccountEnquiryRequestPojo request = exchange.getMessage().getBody(CbAccountEnquiryRequestPojo.class);
		
		CbAccountEnquiryResponsePojo response = new CbAccountEnquiryResponsePojo();
		
		response.setAccountNumber(request.getAccountNumber());
		response.setAccountType("CACC");
		response.setCreditorId("995955858");
		response.setCreditorName("Bambang");
		response.setCreditorType("01");
		response.setDateTime("2021-01-06T09:09:01.000");
		response.setMerchantType("0202");
		response.setNoRef("4005000505");
		response.setReason("U000");
		response.setResidentStatus("01");
		response.setStatus("ACTC");
		response.setTerminalId("00000");
		response.setTownName("0300");
		response.setTransactionId("000000");
		
		exchange.getMessage().setBody(response);
	}

}
