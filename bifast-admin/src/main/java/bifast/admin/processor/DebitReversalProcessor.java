package bifast.admin.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbDebitResponsePojo;
import bifast.admin.pojo.CbDebitReversalPojo;

@Component
public class DebitReversalProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		CbDebitReversalPojo request = exchange.getMessage().getBody(CbDebitReversalPojo.class);
		
		CbDebitResponsePojo response = new CbDebitResponsePojo();
		
		response.setTransactionId("000000");
		response.setDateTime("2021-01-06T09:09:01.000");
		response.setMerchantType("0202");
		response.setNoRef("4005000505");
		response.setReason("U000");
		response.setStatus("ACTC");
		response.setTerminalId("00000");
		
		response.setAccountNumber(request.getCreditorAccountNumber());
		response.setAdditionalInfo("Iya apa");
		
		exchange.getMessage().setBody(response);
		
		System.out.println("response debit");
	}

}
