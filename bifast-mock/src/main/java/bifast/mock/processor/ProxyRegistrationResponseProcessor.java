package bifast.mock.processor;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Proxy002MessageService;
import bifast.library.iso20022.service.Proxy002Seed;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class ProxyRegistrationResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Proxy002MessageService proxy002MessageService;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String bizMsgId = utilService.genRfiBusMsgId("710", "01", "INDOIDJA");
		String msgId = utilService.genMessageId("710", "INDOIDJA");

		Random rand = new Random();
		Proxy002Seed seed = new Proxy002Seed();
        int posbl = rand.nextInt(10);
		if (posbl == 0) {
			seed.setStatus("RJCT");
			seed.setReason("U001");
		}
		else {
			seed.setStatus("ACTC");
			seed.setReason("U000");
			
		}

		seed.setAdditionalInfo("Terimakasih atas perhaitiannya");
		seed.setCstmrId("5302022290990001");
		seed.setCstmrTp("01");
		seed.setCstmrTwnNm("0300");
		seed.setCstmrRsdntSts("01");
		
		BusinessMessage msg = exchange.getIn().getBody(BusinessMessage.class);

		ProxyRegistrationResponseV01 response = proxy002MessageService.proxyRegistrationResponse(seed, msg);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"prxy.002.001.01", bizMsgId);
		Document doc = new Document();
		doc.setPrxyRegnRspn(response);
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		exchange.getMessage().setBody(busMesg);
	}


}
