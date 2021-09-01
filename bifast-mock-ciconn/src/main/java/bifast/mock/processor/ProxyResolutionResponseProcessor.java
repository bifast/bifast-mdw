package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.mock.iso20022.custom.BusinessMessage;
import bifast.mock.iso20022.custom.Document;
import bifast.mock.iso20022.service.AppHeaderService;
import bifast.mock.iso20022.service.Proxy002MessageService;
import bifast.mock.prxy004.Proxy004Seed;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyResolutionResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy002MessageService proxy002MessageService;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Random rand = new Random();
		Proxy004Seed seed = new Proxy004Seed();
		
        int posbl = rand.nextInt(10);
//		if (posbl == 0) {
//			seed.setStatus("RJCT");
//			seed.setReason("U001");
//		}
//		else {
//			seed.setStatus("ACTC");
//			seed.setReason("U000");
//			
//		}

//		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");
//		seed.setCstmrId("5302022290990001");
//		seed.setCstmrTp("01");
//		seed.setCstmrTwnNm("0300");
//		seed.setCstmrRsdntSts("01");
//		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);
//
//		ProxyRegistrationResponseV01 response = proxy002MessageService.proxyRegistrationResponse(seed, msg);
//
//		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
//		hdr = hdrService.initAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
//									"pacs.002.001.10", "710", "01");
		Document doc = new Document();
//		doc.setPrxyRegnRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
//		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
	}


}
