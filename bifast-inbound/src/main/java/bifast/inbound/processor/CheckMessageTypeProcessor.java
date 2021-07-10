package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CheckMessageTypeProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage inputMsg = exchange.getMessage().getBody(BusinessMessage.class);
		String msgIdr = inputMsg.getAppHdr().getMsgDefIdr();
		String trnType = inputMsg.getAppHdr().getBizMsgIdr().substring(16,19);
		
		String msgType = "";
		
		if (msgIdr.startsWith("pacs.002")) {
			msgType = "SETTLEMENT";
		}
		
		else if (msgIdr.startsWith("pacs.008")) {
			if (trnType.equals("510"))
				msgType = "ACCTENQR";
			else if (trnType.equals("010"))
				msgType = "CRDTTRN";
			else if (trnType.equals("011"))
				msgType = "REVCRDTTRN";
		}
		
		else if (msgIdr.startsWith("pacs.009")) {
			msgType = "FICDTTRN";
		}

		else if (msgIdr.startsWith("admi.004")) {
			msgType = "SYSNOTIF";
		}

		exchange.getMessage().setHeader("rcv_msgType", msgType);
		
	}
	
}
