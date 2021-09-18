package bifast.mock.processor;

import java.util.List;
import java.util.Optional;

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
import bifast.mock.persist.MockPacs002;
import bifast.mock.persist.MockPacs002Repository;
import bifast.mock.persist.OutboundMessage;
import bifast.mock.persist.OutboundMessageRepository;

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
		// List<MockPacs002> listPacs002 = mockPacs002Repo.findByOrgnlEndToEndId(reqEndToEndId);
		Optional<MockPacs002> optPacs002 = mockPacs002Repo.findByTrxTypeAndOrgnlEndToEndId("CreditConfirmation", reqEndToEndId);
		if (optPacs002.isPresent()) {
			MockPacs002 pacs002 = optPacs002.get();
			exchange.getMessage().setBody(pacs002.getFullMessage());
		}

		else  {
			optPacs002 = mockPacs002Repo.findByTrxTypeAndOrgnlEndToEndId("SettlementConfirmation", reqEndToEndId);
			if (optPacs002.isPresent()) {
				MockPacs002 pacs002 = optPacs002.get();
				exchange.getMessage().setBody(pacs002.getFullMessage());
			}	
			else
				exchange.getMessage().setBody(null);
//				notFoundCTResponse(psRequest);
		}
		
	}
	
//	BusinessMessage notFoundCTResponse (BusinessMessage psRequest) {
//		String bizMsgId = utilService.genRfiBusMsgId("010", "02");
//		String msgId = utilService.genMessageId("010");
//		
//		Pacs002Seed seed = new Pacs002Seed();
//		seed.setStatus("RJCT");
//		seed.setReason("U106");
//		seed.setAdditionalInfo("Credit Transfer Request Not Found");
//
//		FIToFIPaymentStatusReportV10 response = pacs002Service.creditTransferRequestResponse(seed, msg);
//
//		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
//		hdr = hdrService.getAppHdr(psRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
//									"pacs.002.001.10", bizMsgId);
//		hdr.setBizSvc("CTRESPONSE");
//		
//		Document doc = new Document();
//		doc.setFiToFIPmtStsRpt(response);
//		BusinessMessage busMesg = new BusinessMessage();
//		busMesg.setAppHdr(hdr);
//		busMesg.setDocument(doc);
//
//		exchange.getMessage().setBody(busMesg);
//
//	}

}
