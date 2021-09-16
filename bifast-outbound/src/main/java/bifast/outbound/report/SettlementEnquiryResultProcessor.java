package bifast.outbound.report;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class SettlementEnquiryResultProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		ResponsePojo response = exchange.getMessage().getBody(ResponsePojo.class);
		
		if (!(null == response.getBusinessMessage())) {
			BusinessMessage businessMessage = response.getBusinessMessage();
			exchange.getMessage().setBody(businessMessage);
		}
		else
			exchange.getMessage().setBody(null);

	}

}
