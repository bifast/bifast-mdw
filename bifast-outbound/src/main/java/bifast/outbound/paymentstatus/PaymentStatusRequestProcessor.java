package bifast.outbound.paymentstatus;

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
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.service.UtilService;

@Component
public class PaymentStatusRequestProcessor implements Processor {

	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs028MessageService pacs028MessageService;
	@Autowired
	private UtilService utilService;


	@Override
	public void process(Exchange exchange) throws Exception {
		ChnlPaymentStatusRequestPojo psReq = exchange.getMessage().getBody(ChnlPaymentStatusRequestPojo.class);
			
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		String bizMsgId = utilService.genOfiBusMsgId("000", "99");
		String msgId = utilService.genMessageId("000");
		
		hdr = appHeaderService.getAppHdr(psReq.getRecptBank(), "pacs.028.001.04", bizMsgId);

		Pacs028Seed seed = new Pacs028Seed();	
		seed.setMsgId(msgId);
		seed.setOrgnlEndToEnd(psReq.getOrgnlEndToEndId());
	
		Document doc = new Document();
		doc.setFIToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);

	}

}
