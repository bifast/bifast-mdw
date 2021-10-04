package bifast.inbound.report;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class PackResponseProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage result = exchange.getMessage().getBody(BusinessMessage.class);
		

		ResponseWrapperPojo response = new ResponseWrapperPojo();
		if (null==result) {
			System.out.println("body null");

			RequestPojo request = exchange.getMessage().getHeader("enq_request", RequestPojo.class);
			NotFoundResponsePojo notFoundResponse = new NotFoundResponsePojo(request);
			
			exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
			response.setMessageNotFound(notFoundResponse);
		}
		
		else {
			response.setBusinessMessage(result);
		}
	
		exchange.getMessage().setBody(response);
	}
}
