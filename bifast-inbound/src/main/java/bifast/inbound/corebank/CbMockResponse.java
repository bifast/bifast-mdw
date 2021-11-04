package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;

@Component
public class CbMockResponse implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		String trxType = exchange.getMessage().getHeader("cb_requestName", String.class);
		
		if (trxType.equals("account-enquiry")) {
			
			CbAccountEnquiryResponsePojo aeResp = new CbAccountEnquiryResponsePojo();

			aeResp.setReason("U000");
			aeResp.setStatus("ACTC");

			aeResp.setAccountNumber("account");
			aeResp.setAccountType("type");
			aeResp.setCreditorId("crdit");
			aeResp.setCreditorName("crdname");
			aeResp.setCreditorType("type");
			aeResp.setDateTime("dateTime");
			aeResp.setMerchantType("01");
			aeResp.setNoRef("020202020");
			aeResp.setResidentStatus("01");
			aeResp.setTerminalId("000000");
			aeResp.setTownName("0003");
			aeResp.setTransactionId("0000000");
			
			exchange.getMessage().setBody(aeResp);
			
		}
	}

}
