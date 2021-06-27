package bifast.inbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV02;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV11;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class FICrdtTrnResponseProcessor implements Processor{


	@Autowired
	private AppHeaderService appHdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("rcv_obj", BusinessMessage.class);
		
		// TODO cek account ke core banking

		Pacs002Seed resp = new Pacs002Seed();
		resp.setStatus("ACTC");
		resp.setReason("U0001");

		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		// construct response message
		BusinessApplicationHeaderV02 appHdr = appHdrService.initAppHdr(orignBank, "pacs002.001.10", "019", "99");
		FIToFIPaymentStatusReportV11 respMsg = pacs002Service.fIFICreditTransferRequestResponse(resp, reqBusMesg);
		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);

		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		respBusMesg.setDocument(doc);

		exchange.getIn().setBody(respBusMesg);
	}

}
