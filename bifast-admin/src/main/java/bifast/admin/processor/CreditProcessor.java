package bifast.admin.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbCreditRequestPojo;
import bifast.admin.pojo.CbCreditResponsePojo;

@Component
public class CreditProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		CbCreditRequestPojo request = exchange.getMessage().getBody(CbCreditRequestPojo.class);
		
		CbCreditResponsePojo response = new CbCreditResponsePojo();
		
		response.setTransactionId("000000");
		response.setDateTime("2021-01-06T09:09:01.000");
		response.setMerchantType("0202");
		response.setTerminalId("00000");
	
		response.setNoRef(request.getNoRef());
		response.setReason("U000");
		response.setStatus("ACTC");
		
		response.setAccountNumber(request.getCreditorAccountNumber());
		response.setAdditionalInfo("Iya apa");
		
		exchange.getMessage().setBody(response);
		
		System.out.println("response credit");
	}

}
