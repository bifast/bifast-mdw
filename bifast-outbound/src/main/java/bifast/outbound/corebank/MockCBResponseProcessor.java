package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MockCBResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CBInstructionWrapper cbRequestObj = exchange.getMessage().getBody(CBInstructionWrapper.class);
		CBInstructionWrapper cbResponse = new CBInstructionWrapper();

		if (!(null == cbRequestObj.getCbDebitInstructionRequest())) {

			CBDebitInstructionResponsePojo response = new CBDebitInstructionResponsePojo();		
			response.setAccountNumber("HAHA");
			response.setStatus("SUCCESS");
			response.setAddtInfo("rekening tidak ada");
			
			cbResponse.setCbDebitInstructionResponse(response);
			exchange.getIn().setBody(cbResponse);
		}
		else {
			CBFITransferResponsePojo response = new CBFITransferResponsePojo();
			response.setStatus("SUCCESS");
			response.setAddtInfo("rekening ada");
			exchange.getIn().setBody(response);
			
			cbResponse.setCbFITransferResponse(response);
			exchange.getIn().setBody(cbResponse);
		}

	}

}
