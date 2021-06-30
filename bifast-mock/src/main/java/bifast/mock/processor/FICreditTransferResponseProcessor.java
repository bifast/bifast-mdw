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
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Random rand = new Random();
		Pacs002Seed seed = new Pacs002Seed();
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {
			seed.setStatus("AJCT");
			seed.setReason("U000");
			
		}

		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");

		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);

		FIToFIPaymentStatusReportV10 response = pacs002Service.fIFICreditTransferRequestResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.initAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", "019", "99");
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);

	}


}
