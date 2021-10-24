package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.flat.FlatAdmi002Pojo;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.pojo.flat.FlatPrxy006Pojo;
import bifast.outbound.service.FlattenIsoMessageService;

@Component
public class FlatResponseProcessor implements Processor {

	@Autowired
	private FlattenIsoMessageService flattenMessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {
			
		BusinessMessage busMesg = exchange.getIn().getBody(BusinessMessage.class);
		
		if (busMesg.getAppHdr().getMsgDefIdr().equals("pacs.002.001.10")) {
						
			FlatPacs002Pojo flatPacs002 = flattenMessageService.flatteningPacs002(busMesg);
			exchange.getMessage().setBody(flatPacs002);

		}
		
		else if (busMesg.getAppHdr().getMsgDefIdr().equals("admi.002.001.01")) {
			
			FlatAdmi002Pojo flatAdmi002 = flattenMessageService.flatteningAdmi002(busMesg);
			exchange.getMessage().setBody(flatAdmi002);

		}
		
		else if (busMesg.getAppHdr().getMsgDefIdr().equals("prxy.002.001.01")) {
			
			FlatPrxy002Pojo flatPrxy002 = flattenMessageService.flatteningPrxy002(busMesg);
			exchange.getMessage().setBody(flatPrxy002);
		}
		
		else if (busMesg.getAppHdr().getMsgDefIdr().equals("prxy.004.001.01")) {
			
			FlatPrxy004Pojo flatPrxy004 = flattenMessageService.flatteningPrxy004(busMesg);
			exchange.getMessage().setBody(flatPrxy004);
		}
		
		else if (busMesg.getAppHdr().getMsgDefIdr().equals("prxy.006.001.01")) {
			
			FlatPrxy006Pojo flatPrxy006 = flattenMessageService.flatteningPrxy006(busMesg);
			exchange.getMessage().setBody(flatPrxy006);
		}
		
	}

}
