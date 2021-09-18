package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MockCBFIResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CBFITransferResponsePojo resp = new CBFITransferResponsePojo();

		resp.setTransactionStatus("SUCCESS");
		resp.setAddtInfo("Nggak ada");
		resp.setTransactionId("0000");
		
		
		exchange.getIn().setBody(resp);
	}

}