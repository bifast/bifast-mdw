package bifast.mock.processor;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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

		String norekDebitur = msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId();
		String norek = msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();
		exchange.getMessage().setHeader("hdr_account_no", norek);

		Pacs002Seed seed = new Pacs002Seed();
		
		seed.setMsgId(msgId);
		
		if (null == msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getNm())
			seed.setCreditorName(utilService.getFullName());
		else
			seed.setCreditorName(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getNm());

		seed.setCreditorType("01");
		seed.setCreditorId(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		seed.setCreditorResidentialStatus("01");
		seed.setCreditorTown(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTwnNm());

        // int posbl4 = rand.nextInt(4);
		if (norekDebitur.startsWith("5")) {
			seed.setStatus("RJCT");
			seed.setReason("U110");
			seed.setAdditionalInfo("Additional Info abbc lsdjf 46");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");
		}
		

		if (seed.getStatus().equals("ACTC"))	
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "ACTC");
		else
			exchange.getMessage().setHeader("hdr_ctRespondStatus", "RJCT");
			

		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);
		hdr.setBizSvc("CLEAR");
		hdr.getFr().getFIId().getFinInstnId().getOthr().setId(msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		
		FIToFIPaymentStatusReportV10 response = pacs002Service.creditTransferRequestResponse(seed, msg);
		
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		response.getTxInfAndSts().get(0).getOrgnlTxRef().setIntrBkSttlmDt(xcal);
			
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);

	}


}
