package bifast.outbound.paymentstatus.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs028MessageService;
import bifast.library.iso20022.service.Pacs028Seed;
import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.service.UtilService;

@Component
public class BuildPaymentStatusSAFRequestProcessor implements Processor{
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs028MessageService pacs028MessageService;

	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		UndefinedCTPojo req = exchange.getMessage().getHeader("ps_request", UndefinedCTPojo.class);
			
		
		String msgType = "010";
		String bizMsgId = utilService.genOfiBusMsgId(msgType, req.getChannelType());
		String msgId = utilService.genMsgId(msgType, req.getKomiTrnsId());

		Pacs028Seed seed = new Pacs028Seed();
		seed.setMsgId(msgId);
		
		seed.setOrgnlEndToEnd(req.getReqBizmsgid());
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		hdr = appHeaderService.getAppHdr(req.getRecipientBank(), "pacs.028.001.04", bizMsgId);

		Document doc = new Document();
		doc.setFiToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);
	}

}
