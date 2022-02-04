package bifast.mock.isoapt;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.mock.isoapt.pojo.CIFRequest;
import bifast.mock.isoapt.pojo.CIFResponse;

@Component
public class CIFProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CIFRequest req = exchange.getMessage().getBody(CIFRequest.class);
		
		CIFResponse resp = new CIFResponse();
		
		resp.setAccountNumber(req.getAccountNumber());
		resp.setCreditorId("111");
		resp.setCreditorName("as");
		resp.setCreditorType("01");
		resp.setTownName("0300");
		resp.setResidentStatus("01");

		resp.setNoRef("ACTC");
		resp.setDateTime("");
		resp.setMerchantType("6010");
		resp.setTerminalId(req.getTerminalId());
		resp.setTransactionId(req.getTransactionId());

		resp.setReason("U000");
		resp.setStatus("ACTC");


		exchange.getMessage().setBody(resp);
		
	}

}
