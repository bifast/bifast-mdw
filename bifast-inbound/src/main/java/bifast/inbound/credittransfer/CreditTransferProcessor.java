package bifast.inbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CBCreditInstructionResponsePojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
public class CreditTransferProcessor implements Processor {

	@Autowired
	private AppHeaderService appHdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		CBCreditInstructionResponsePojo cbResponse = exchange.getMessage().getBody(CBCreditInstructionResponsePojo.class);		
		
		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
		CreditTransferTransaction39 biReq =  reqBusMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		Boolean isCopyDupl = false;
		if ( (!(null==reqBusMesg.getAppHdr().getCpyDplct())) &&
				 (reqBusMesg.getAppHdr().getCpyDplct().name().equals("DUPL")) ) {
			isCopyDupl = true;
		}

		String bizMsgId = utilService.genRfiBusMsgId("010", "99");
		String msgId = utilService.genMessageId("010");

		Pacs002Seed resp = new Pacs002Seed();
		resp.setMsgId(msgId);

		if (!(null == biReq.getCdtr().getNm())) 
			resp.setCreditorName(biReq.getCdtr().getNm());

		if ((null==cbResponse) && isCopyDupl ){
			//harus ACTC - plus reversal
			resp.setStatus("ACTC"); 
			resp.setReason("U002");     
//			resp.setAdditionalInfo("Ini informasi tambahan aja.");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
//			resp.setCreditorType("01");
//			resp.setCreditorId("234433");

			exchange.getMessage().setHeader("resp_reversal", "PENDING");

		}
		
		else if ((null==cbResponse) && !isCopyDupl ){
		
			// harus RJCT
			
			resp.setStatus("RJCT"); 
			resp.setReason("U003");     
//			resp.setAdditionalInfo("Ini informasi tambahan aja.");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
//			resp.setCreditorType("01");
//			resp.setCreditorId("234433");

			exchange.getMessage().setHeader("resp_reversal", "");

		}

		else if ((cbResponse.getStatus().equals("FAILED")) && isCopyDupl) {  // perlu reversal
			// harus ACCT plus reversal
			resp.setStatus("ACTC"); 
			resp.setReason("U002");     
//			resp.setAdditionalInfo("Ini informasi tambahan aja.");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
//			resp.setCreditorType("01");
//			resp.setCreditorId("234433");

			exchange.getMessage().setHeader("resp_reversal", "PENDING");

		}

		else if ((cbResponse.getStatus().equals("FAILED")) && !isCopyDupl) {
			// harus RJCT
			resp.setStatus("RJCT"); 
			resp.setReason("U003");     
//			resp.setAdditionalInfo("Ini informasi tambahan aja.");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
//			resp.setCreditorType("01");
//			resp.setCreditorId("234433");

			
			exchange.getMessage().setHeader("resp_reversal", "");

		}

		else if (cbResponse.getStatus().equals("SUCCESS"))  {
			resp.setStatus("ACTC"); 
			resp.setReason("U002");     
//			resp.setAdditionalInfo("Ini informasi tambahan aja.");
//			resp.setCreditorResidentialStatus("01");  // 01 RESIDENT
//			resp.setCreditorTown("0300");  
//			resp.setCreditorType("01");
//			resp.setCreditorId("234433");
			
			exchange.getMessage().setHeader("resp_reversal", "");

		}


		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.creditTransferRequestResponse(resp, reqBusMesg);
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		
		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr(orignBank, "pacs002.001.10", bizMsgId);
		appHdr.setBizSvc("CLEAR");
		
		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);
		respBusMesg.setDocument(doc);
		
		exchange.getIn().setBody(respBusMesg);

	}

}
