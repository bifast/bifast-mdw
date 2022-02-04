package bifast.mock.isoapt;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.mock.isoapt.pojo.CreditResponse;
import bifast.mock.isoapt.pojo.DebitRequest;

@Component
public class DebitRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		DebitRequest req = exchange.getMessage().getBody(DebitRequest.class);
		
		CreditResponse resp = new CreditResponse();
		
		resp.setAccountNumber(req.getCreditorAccountNumber());
		resp.setAdditionalInfo(req.getPaymentInformation());
		
		resp.setDateTime(req.getDateTime());
		resp.setMerchantType(req.getMerchantType());
		resp.setNoRef(req.getNoRef());

		if (req.getPaymentInformation().equals("CB-RJCT")) {
			resp.setReason("U149");
			resp.setStatus("RJCT");			
		}
		else {
			resp.setReason("U000");
			resp.setStatus("ACTC");
		}
		
		resp.setTerminalId(req.getTerminalId());
		resp.setTransactionId(req.getTransactionId());
		
		exchange.getMessage().setBody(resp);
		
	}

}
