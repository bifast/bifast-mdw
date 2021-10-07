package bifast.mock.processor;

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
public class CreditTransferResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);

		String bizMsgId = utilService.genRfiBusMsgId("010", "02", msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		String msgId = utilService.genMessageId("010", msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());


		String norek = msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();
		exchange.getMessage().setHeader("hdr_account_no", norek);

		Pacs002Seed seed = new Pacs002Seed();
		
		seed.setMsgId(msgId);
		
        // int posbl4 = rand.nextInt(4);
		if (norek.startsWith("5")) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
			seed.setAdditionalInfo("Additional Info abbc lsdjf 46");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");

			seed.setCreditorName(utilService.getFullName());
			seed.setCreditorType("01");
			seed.setCreditorId("KTP-2004384");
			seed.setCreditorResidentialStatus("01");
			seed.setCreditorTown("0300");
		}

		if (seed.getStatus().equals("ACTC"))	
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "ACTC");
		else
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "RJCT");
			

		FIToFIPaymentStatusReportV10 response = pacs002Service.creditTransferRequestResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		System.out.println("from Bank: " + msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());

		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);
		hdr.setBizSvc("CLEAR");
		hdr.getFr().getFIId().getFinInstnId().getOthr().setId(msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);

	}


}
