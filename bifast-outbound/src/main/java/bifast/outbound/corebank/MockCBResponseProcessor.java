package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MockCBResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CBInstructionWrapper cbRequestWr = exchange.getMessage().getBody(CBInstructionWrapper.class);

//		CBInstructionWrapper cbResponse = new CBInstructionWrapper();

		String status = "";
		CBDebitInstructionResponsePojo response = new CBDebitInstructionResponsePojo();	
		
		if (!(null == cbRequestWr.getCbDebitInstructionRequest()))  {
			
			CBDebitInstructionRequestPojo request = cbRequestWr.getCbDebitInstructionRequest();
		
			if (request.getTransactionId().startsWith("9")) 
				status = "FAILED";
			else 
				status = "SUCCESS";
			
			response.setTransactionId(request.getTransactionId());
			response.setAccountNumber("HAHA");
			response.setStatus(status);
			response.setAddtInfo("Info tambahan disini");
			
		}
		
		else {
			
			CBFITransferRequestPojo request = cbRequestWr.getCbFITransferRequest();

			response.setTransactionId(request.getTransactionId());

			if (request.getTransactionId().startsWith("9")) 
				status = "FAILED";
			else 
				status = "SUCCESS";

		}
		response.setStatus(status);
		response.setAddtInfo("Info tambahan disini");

//		cbResponse.setCbDebitInstructionResponse(response);
		exchange.getIn().setBody(response);
	}

}
