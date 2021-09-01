package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.mock.iso20022.custom.BusinessMessage;
import bifast.mock.iso20022.custom.Document;
import bifast.mock.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.mock.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.mock.iso20022.service.AppHeaderService;
import bifast.mock.iso20022.service.Pacs002MessageService;
import bifast.mock.iso20022.service.Pacs002Seed;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class AccountEnquiryResponseProcessor implements Processor {

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	
	@Override
	public void process(Exchange exchange) throws Exception {

        Random rand = new Random();
        int posbl4 = rand.nextInt(4);

		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
		
		Pacs002Seed seed = new Pacs002Seed();

		if (posbl4 == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");
			
		}
		seed.setCreditorName("Wawan Setiawan");
		seed.setCreditorAccountNo("977004883004");
		seed.setCreditorAccountIdType("CACC");
		seed.setCreditorType("01");
		seed.setCreditorId(String.format("KTP-2%08d", rand.nextInt(9999999)));
		seed.setCreditorTown("0300");
		seed.setCreditorResidentialStatus("01");
		
		FIToFIPaymentStatusReportV10 response = pacs002Service.accountEnquiryResponse(seed, msg);
		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.initAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", "510", "02");
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);
		
		exchange.getMessage().setBody(busMesg);
		
	}

}
