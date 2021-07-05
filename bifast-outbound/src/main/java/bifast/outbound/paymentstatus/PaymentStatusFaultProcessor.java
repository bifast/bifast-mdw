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
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelReject;
import bifast.outbound.pojo.ChannelResponseMessage;

@Component
public class PaymentStatusFaultProcessor implements Processor {

	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs028MessageService pacs028MessageService;
	

	@Override
	public void process(Exchange exchange) throws Exception {
		BusinessMessage orgnMsg = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
			
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.initAppHdr(orgnMsg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId(), 
										"pacs.028.001.04", "000", "99");

		Pacs028Seed seed = new Pacs028Seed();	
		seed.setOrgnlEndToEnd(orgnMsg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getEndToEndId());

		Document doc = new Document();
		doc.setFIToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));

		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);
	
		exchange.getIn().setBody(busMsg);

	}

}
