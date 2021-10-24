package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class FICreditTransferResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage req = exchange.getMessage().getBody(BusinessMessage.class);
		String rcptBank = req.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		exchange.getMessage().setHeader("hdr_rcptBank", rcptBank);

		String bizMsgId = utilService.genRfiBusMsgId("019", "99", req.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		String msgId = utilService.genMessageId("019", req.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		Random rand = new Random();
		Pacs002Seed seed = new Pacs002Seed();
		
		seed.setMsgId(msgId);
		
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");
			
		}

		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");

		if (seed.getStatus().equals("ACTC"))	
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "ACTC");
		else
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "RJCT");

		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);

		FIToFIPaymentStatusReportV10 response = pacs002Service.fIFICreditTransferRequestResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);
		hdr.setBizSvc("CTRESPONSE");
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);

	}


}
