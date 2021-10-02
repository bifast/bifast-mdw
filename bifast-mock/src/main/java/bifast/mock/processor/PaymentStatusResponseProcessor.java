package bifast.mock.processor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;

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
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;

@Component
public class PaymentStatusResponseProcessor implements Processor{

	@Autowired
	private MockPacs002Repository mockPacs002Repo;
	@Autowired
	private UtilService utilService;
	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;


	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage psRequest = exchange.getMessage().getHeader("objRequest", BusinessMessage.class);
		String reqEndToEndId = psRequest.getDocument().getFIToFIPmtStsReq().getTxInf().get(0).getOrgnlEndToEndId();

		System.out.println("Cari history dengan no OrgnlEndtoend: " + reqEndToEndId);

		ObjectMapper map = new ObjectMapper();
		map.registerModule(new JaxbAnnotationModule());
		map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// List<MockPacs002> listPacs002 = mockPacs002Repo.findByOrgnlEndToEndId(reqEndToEndId);
		Optional<MockPacs002> optPacs002 = mockPacs002Repo.findByTrxTypeAndOrgnlEndToEndId("CreditConfirmation", reqEndToEndId);
		if (optPacs002.isPresent()) {
			MockPacs002 pacs002 = optPacs002.get();

			BusinessMessage bm = map.readValue(pacs002.getFullMessage(), BusinessMessage.class);

			exchange.getMessage().setBody(bm);
		}

		else  {
			optPacs002 = mockPacs002Repo.findByTrxTypeAndOrgnlEndToEndId("SettlementConfirmation", reqEndToEndId);
			if (optPacs002.isPresent()) {
				MockPacs002 pacs002 = optPacs002.get();
				String str = pacs002.getFullMessage();
				
				BusinessMessage bm = map.readValue(str, BusinessMessage.class);

				exchange.getMessage().setBody(bm);
			
			}	
			else
				// exchange.getMessage().setBody(null);
				System.out.println("ga nemu ah");
				exchange.getMessage().setBody(null);
		}
		
		
		
	}
	
	BusinessMessage notFoundCTResponse (BusinessMessage psRequest) {
		String bizMsgId = utilService.genRfiBusMsgId("010", "02");
		String msgId = utilService.genMessageId("010");
		BusinessMessage busMesg = new BusinessMessage();
		
		Pacs002Seed seed = new Pacs002Seed();
		seed.setStatus("RJCT");
		seed.setReason("U106");
		seed.setAdditionalInfo("Credit Transfer Request Not Found");

		FIToFIPaymentStatusReportV10 response = new FIToFIPaymentStatusReportV10();
		try {
			response = pacs002Service.creditTransferRequestResponse(seed, psRequest);

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(psRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);
		hdr.setBizSvc("CTRESPONSE");
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return busMesg;
//		exchange.getMessage().setBody(busMesg);

	}

}
