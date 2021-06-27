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
public class CreditTransferRequestProcessor implements Processor {

	@Autowired
	private AppHeaderService appHeaderService;

	@Autowired
	private Pacs008MessageService pacs008MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		CreditTransferRequestInput req = exchange.getIn().getHeader("req_orgnlReq", CreditTransferRequestInput.class);

		BusinessApplicationHeaderV02 hdr = new BusinessApplicationHeaderV02();
		hdr = appHeaderService.initAppHdr(req.getReceivingParticipant(), "pacs.008.00.08", "010", req.getChannelType());

		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.creditTransferRequest(req, hdr.getBizMsgIdr(), "010"));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
		
		exchange.getMessage().setHeader("req_ctBizMsgId", hdr.getBizMsgIdr());
		exchange.getMessage().setHeader("req_ctMsgId", doc.getFiToFICstmrCdtTrf().getGrpHdr().getMsgId());

		exchange.getIn().setBody(busMsg);

	}

}
