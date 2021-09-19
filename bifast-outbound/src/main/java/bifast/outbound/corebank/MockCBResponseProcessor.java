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

		String status = "";
		
		if (!(null == cbRequestObj.getCbDebitInstructionRequest())) {

			CBDebitInstructionRequestPojo request = (CBDebitInstructionRequestPojo) cbRequestObj.getCbDebitInstructionRequest();
			
			if (request.getTransactionId().startsWith("9")) 
				status = "FAILED";
			else 
				status = "SUCCESS";
				
			CBDebitInstructionResponsePojo response = new CBDebitInstructionResponsePojo();		
			response.setAccountNumber("HAHA");
			response.setStatus(status);
			response.setAddtInfo("rekening tidak ada");
			
			cbResponse.setCbDebitInstructionResponse(response);
			exchange.getIn().setBody(cbResponse);
		}
		
		else {
			
			CBFITransferRequestPojo request = (CBFITransferRequestPojo) cbRequestObj.getCbFITransferRequest();
			
			if (request.getTransactionId().startsWith("9")) 
				status = "FAILED";
			else 
				status = "SUCCESS";

			CBFITransferResponsePojo response = new CBFITransferResponsePojo();
			response.setStatus("SUCCESS");
			response.setAddtInfo("rekening ada");
			exchange.getIn().setBody(response);
			
			cbResponse.setCbFITransferResponse(response);
			exchange.getIn().setBody(cbResponse);
		}

	}

}
