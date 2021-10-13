package bifast.inbound.accountenquiry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.pojo.CBAccountEnquiryResponsePojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
@ComponentScan({"bifast.library.iso20022", "bifast.library.config"}) 
public class AECorebankReponseProcessor implements Processor {
	
	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Override
	public void process(Exchange exchange) throws Exception {

		CBAccountEnquiryResponsePojo cbResponse = exchange.getMessage().getBody(CBAccountEnquiryResponsePojo.class);		
		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);

		String strToday = LocalDate.now().format(formatter);

		String inboundResponseId = utilService.genKomiTrnsId();
		
		String bizMsgId = strToday + config.getBankcode() + "510R99" + inboundResponseId;

		String msgId = strToday + config.getBankcode() + "510" + inboundResponseId;
		
		Pacs002Seed resp = new Pacs002Seed();
		
		if (null==cbResponse) {
			resp.setMsgId(msgId);
//			resp.setCreditorName("UJANG");
			resp.setCreditorAccountNo(reqBusMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
			resp.setCreditorAccountIdType("CACC");
//			resp.setCreditorType("01");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
			resp.setStatus("RJCT");
			resp.setReason("U002"); 
		}
		
		else {
			resp.setMsgId(msgId);
			resp.setCreditorName(cbResponse.getCreditorName());
			resp.setCreditorAccountNo(cbResponse.getAccountNumber());  
			resp.setCreditorAccountIdType(cbResponse.getAccountType());
		
			// TODO apakah creditorType perlu conversi ?
			resp.setCreditorType(cbResponse.getCreditorType());
	
			resp.setCreditorResidentialStatus(cbResponse.getResidentStatus());  // 01 RESIDENT
			resp.setCreditorTown(cbResponse.getTownName());
		
			if (cbResponse.getCreditorStatus().equals("ACCEPTED")) {
				resp.setStatus("ACTC");
				resp.setReason("U000");     
			}
			else {
				resp.setStatus("RJCT");
				resp.setReason("U000");     
			}
		}
			
		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.accountEnquiryResponse(resp, reqBusMesg);
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		
		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr(orignBank, "pacs002.001.10", bizMsgId);

		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);
		respBusMesg.setDocument(doc);
				
		exchange.getIn().setBody(respBusMesg);
	}

}
