package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CBDebitInstructionRequestPojo;
import bifast.outbound.corebank.pojo.CBDebitInstructionResponsePojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.repository.CorebankTransactionRepository;

@Component
public class MockCBResponseProcessor implements Processor {
	@Autowired
	private CorebankTransactionRepository cbRepo;
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
		
		response.setStatus(status);
		response.setAddtInfo("Info tambahan disini");

//		cbResponse.setCbDebitInstructionResponse(response);
		
//		ChannelResponseWrapper chnlResponseWr = new ChannelResponseWrapper();
//		chnlResponseWr.setCbDebitInstructionResponse(response);
		exchange.getIn().setBody(response);
//		exchange.getIn().setBody(chnlResponseWr);
	}

}
