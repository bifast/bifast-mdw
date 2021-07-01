package bifast.mock.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.OutboundMessageRepository;

@Component
//@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class PaymentStatusResponseProcessor implements Processor{

	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private OutboundMessageRepository outboundMessageRepo;
	@Autowired
	private Pacs002MessageService pacs002Service;

	@Override
	public void process(Exchange exchange) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JaxbAnnotationModule());
		mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		Document doc = new Document();

		BusinessMessage req = exchange.getIn().getBody(BusinessMessage.class);
		String reqMsgId = req.getDocument().getFIToFIPmtStsReq().getTxInf().get(0).getOrgnlEndToEndId();
		BusinessMessage responseMessageRequested = new BusinessMessage();
		
		String outboundMsgString = "";
		Pacs002Seed seed = new Pacs002Seed();

	
		Optional<OutboundMessage> optOutbMsg = outboundMessageRepo.findByBizMsgIdr(reqMsgId);
		if (optOutbMsg.isPresent()) {
			outboundMsgString = optOutbMsg.get().getResponseMessage();
			responseMessageRequested = mapper.readValue(outboundMsgString, BusinessMessage.class);
		}
		
		else {
			outboundMsgString = outboundMessageRepo.findAll().get(0).getResponseMessage();
			BusinessMessage msgTmp = mapper.readValue(outboundMsgString, BusinessMessage.class);
		
			seed.setStatus("RJCT");
			seed.setReason("U106");
			seed.setAdditionalInfo("Tidak ketemu");
			FIToFIPaymentStatusReportV10 response = pacs002Service.creditTransferRequestResponse(seed, msgTmp);
					
			BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
			hdr = hdrService.initAppHdr("INDOIDJA", 
										"pacs.002.001.10", "019", "99");

			doc.setFiToFIPmtStsRpt(response);

			responseMessageRequested.setAppHdr(hdr);
			responseMessageRequested.setDocument(doc);

		}

		exchange.getMessage().setBody(responseMessageRequested);

	}


}
