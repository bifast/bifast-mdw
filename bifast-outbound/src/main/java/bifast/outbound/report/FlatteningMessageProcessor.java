package bifast.outbound.report;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.flat.FlatMessageWrapper;
import bifast.outbound.pojo.flat.FlatPacs008Pojo;
import bifast.outbound.service.FlattenIsoMessageService;
import bifast.outbound.service.UtilService;

@Component
public class FlatteningMessageProcessor implements Processor {

	@Autowired
	private FlattenIsoMessageService flattingMessageService;
	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);

		if (!(null == bm)) {
			FlatMessageWrapper responseWr = new FlatMessageWrapper();
	
			String msgType = utilService.getMsgType(bm.getAppHdr().getMsgDefIdr(), bm.getAppHdr().getBizMsgIdr());
			
			if (msgType.equals("AccountEnquiryRequest")) {
				
				FlatPacs008Pojo pacs008 = flattingMessageService.flatteningPacs008(bm);
				
				responseWr.setAccountEnquiryRequest(pacs008);
			}
			
			else if (msgType.equals("CreditTransferRequest")) {
		
				responseWr.setCreditTransferRequest(flattingMessageService.flatteningPacs008(bm));
	
			}
	
			exchange.getMessage().setBody(responseWr);
			
		}
		
		else
			exchange.getMessage().setBody(null);
			
	}
}
