package bifast.admin.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbDebitRequestPojo;
import bifast.admin.pojo.CbDebitResponsePojo;

@Component
public class DebitProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		CbDebitRequestPojo request = exchange.getMessage().getBody(CbDebitRequestPojo.class);
		
		CbDebitResponsePojo response = new CbDebitResponsePojo();
		
		response.setTransactionId("000000");
		response.setDateTime("2021-01-06T09:09:01.000");
		response.setMerchantType("0202");
		response.setNoRef("4005000505");
		
		if (request.getTerminalId().startsWith("B")) {
			response.setReason("U110");
			response.setStatus("RJCT");
		}
		else {
			response.setReason("U000");
			response.setStatus("ACTC");			
		}
		
		response.setTerminalId("00000");
		
		response.setAccountNumber(request.getCreditorAccountNumber());
		response.setAdditionalInfo("Iya apa");
		
		exchange.getMessage().setBody(response);
		
		System.out.println("response debit");
	}

}
