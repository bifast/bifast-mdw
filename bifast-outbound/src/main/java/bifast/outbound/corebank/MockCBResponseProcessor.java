package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MockCBResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		Object cbRequestObj = exchange.getMessage().getBody(Object.class);
		
		String requestClassName = cbRequestObj.getClass().getName();
		
		
		CBInstructionWrapper cbResponse = new CBInstructionWrapper();

		String status = "";
		
		if (requestClassName.equals("bifast.outbound.corebank.CBDebitInstructionRequestPojo")) {

			CBDebitInstructionRequestPojo request = (CBDebitInstructionRequestPojo) cbRequestObj;
			
			if (request.getTransactionId().startsWith("9")) 
				status = "FAILED";
			else 
				status = "SUCCESS";
				
			CBDebitInstructionResponsePojo response = new CBDebitInstructionResponsePojo();	
			
			response.setAccountNumber("HAHA");
			response.setStatus(status);
			response.setAddtInfo("Info tambahan disini");
			
			cbResponse.setCbDebitInstructionResponse(response);
			exchange.getIn().setBody(cbResponse);
		}
		
		else {
			
			CBFITransferRequestPojo request = (CBFITransferRequestPojo) cbRequestObj;
			
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
