package bifast.inbound.ficredittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CBFICreditInstructionResponsePojo;
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

		CBFICreditInstructionResponsePojo cbResponse = exchange.getMessage().getBody(CBFICreditInstructionResponsePojo.class);		

		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);

		Boolean isCopyDupl = false;
		if ( (!(null==reqBusMesg.getAppHdr().getCpyDplct())) &&
				 (reqBusMesg.getAppHdr().getCpyDplct().name().equals("DUPL")) ) {
			isCopyDupl = true;
			System.out.println("Ini COPY DUPL");
		}
		
		String bizMsgId = utilService.genRfiBusMsgId("019", "99");
		String msgId = utilService.genMessageId("019");


		Pacs002Seed resp = new Pacs002Seed();
		resp.setMsgId(msgId);

		if ((null==cbResponse) && isCopyDupl ){
			resp.setStatus("ACTC"); 
			resp.setReason("U002");     
			
			exchange.getMessage().setHeader("resp_reversal", "PENDING");

		}
		
		else if ((null==cbResponse) && !isCopyDupl ){
			resp.setStatus("RJCT"); 
			resp.setReason("U003");     

			exchange.getMessage().setHeader("resp_reversal", "");
		}
		
		else if ((cbResponse.getTransactionStatus().equals("FAILED")) && isCopyDupl) {  // perlu reversal
			resp.setStatus("ACTC"); 
			resp.setReason("U002");     

			exchange.getMessage().setHeader("resp_reversal", "PENDING");

		}
		
		else if ((cbResponse.getTransactionStatus().equals("FAILED")) && !isCopyDupl) {
			resp.setStatus("RJCT"); 
			resp.setReason("U003");     

			exchange.getMessage().setHeader("resp_reversal", "");
		}
		
		else if (cbResponse.getTransactionStatus().equals("SUCCESS"))  {

			resp.setStatus("ACTC");
			resp.setReason("U0001");

			exchange.getMessage().setHeader("resp_reversal", "");
		}


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
