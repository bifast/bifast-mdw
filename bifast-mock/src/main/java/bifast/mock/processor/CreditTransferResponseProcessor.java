package bifast.mock.processor;

import java.util.GregorianCalendar;
import java.util.Optional;

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
import bifast.mock.persist.AccountProxy;
import bifast.mock.persist.AccountProxyRepository;

@Component
public class CreditTransferResponseProcessor implements Processor{
	@Autowired AccountProxyRepository accountRepo;
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
		
		String norekCdtr = msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId();
		String bank = msg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		exchange.getMessage().setHeader("hdr_account_no", norekCdtr);

		Optional<AccountProxy> oAcct = accountRepo.findByAccountNumberAndRegisterBank(norekCdtr, bank);

		Pacs002Seed seed = new Pacs002Seed();
		seed.setMsgId(msgId);
		
		AccountProxy account = null;
		if (oAcct.isPresent()) {
			account = oAcct.get();

			if (account.getAccountStatus().equals("ACTV")) {
				seed.setStatus("ACTC");
				seed.setReason("U000");				
				seed.setCreditorName(account.getAccountName());
			}
			else {
				seed.setStatus("RJCT");
				seed.setReason("U102");				
			}
		}
		else {
			seed.setStatus("RJCT");
			seed.setReason("U101");
			seed.setCreditorName(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getNm());
		}
		

		seed.setCreditorType("01");
		seed.setCreditorId(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		seed.setCreditorResidentialStatus("01");
		seed.setCreditorTown(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDtl().getCdtr().getTwnNm());


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
