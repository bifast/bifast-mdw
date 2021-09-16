package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MockCBDebitResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CBDebitInstructionResponsePojo resp = new CBDebitInstructionResponsePojo();
//		resp.setAccountNumber("HAHA");
//		resp.setStatus("FAILED");
//		resp.setAddtInfo("rekening tidak ada");

		resp.setAccountNumber("HAHA");
		resp.setStatus("SUCCESS");
		resp.setAddtInfo("rekening ada");

		exchange.getIn().setBody(resp);
	}

}
