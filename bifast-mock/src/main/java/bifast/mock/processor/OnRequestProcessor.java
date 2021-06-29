package bifast.mock.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class OnRequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage req = exchange.getIn().getBody(BusinessMessage.class);
		String trnType = req.getAppHdr().getBizMsgIdr().substring(16, 19);
		
		if (req.getAppHdr().getMsgDefIdr().startsWith("pacs.008")) {
			if (trnType.equals("510")) exchange.getMessage().setHeader("msgType", "AccountEnquiryRequest");
			else if (trnType.equals("010")) exchange.getMessage().setHeader("msgType", "CreditTransferRequest");
		}
		
		else if (req.getAppHdr().getMsgDefIdr().startsWith("pacs.009")) {
			
		} 
		
	}

}
