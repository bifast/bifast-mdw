package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class FICreditTransferProcessor implements Processor{


	@Autowired
	private AppHeaderService appHdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
		String bizMsgId = utilService.genRfiBusMsgId("019", "99");
		String msgId = utilService.genMessageId("019");

		// TODO cek account ke core banking

		Pacs002Seed resp = new Pacs002Seed();
		resp.setMsgId(msgId);
		resp.setStatus("ACTC");
		resp.setReason("U0001");

		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		// construct response message
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr(orignBank, "pacs002.001.10", bizMsgId);
		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.fIFICreditTransferRequestResponse(resp, reqBusMesg);
		
		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);

		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		respBusMesg.setDocument(doc);

		exchange.getIn().setBody(respBusMesg);
	}

}
