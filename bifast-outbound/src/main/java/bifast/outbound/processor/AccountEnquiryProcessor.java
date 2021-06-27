package fransmz.bifast.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fransmz.bifast.pojo.BusinessMessage;
import fransmz.bifast.pojo.Document;
import fransmz.bifast.service.AppHeaderService;
import iso20022.head001.BusinessApplicationHeaderV02;

@Component
public class AccountEnquiryProcessor implements Processor {

	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs008MessageService pacs008MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		CreditTransferRequestInput req = exchange.getIn().getHeader("req_orgnlReq",CreditTransferRequestInput.class);

		BusinessApplicationHeaderV02 hdr = new BusinessApplicationHeaderV02();
		hdr = appHeaderService.initAppHdr(req.getReceivingParticipant(), "pacs.008.01.08", "510", req.getChannelType());
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.accountEnquiryRequest(req, hdr.getBizMsgIdr(), "510"));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getMessage().setHeader("req_aeBizMsgId", hdr.getBizMsgIdr());
		exchange.getMessage().setHeader("req_aeMsgId", doc.getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());
		exchange.getIn().setBody(busMsg);
	}

}
