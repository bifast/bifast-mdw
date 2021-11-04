package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
public class AccountEnquiryResponseProcessor implements Processor {
	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		CbAccountEnquiryResponsePojo aeResp = exchange.getMessage().getBody(CbAccountEnquiryResponsePojo.class);
//		BusinessMessage msg = exchange.getMessage().getHeader("ae_obj_birequest", BusinessMessage.class);

		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);

		BusinessMessage msg = processData.getBiRequestMsg();

//		String komiTrnsId = utilService.genKomiTrnsId();
//		exchange.getMessage().setHeader("hdr_komiId", komiTrnsId);
//		System.out.println("generate komiId : " + komiTrnsId);
		
		String bizMsgId = utilService.genRfiBusMsgId("510", processData.getKomiTrnsId());
		String msgId = utilService.genMsgId("510", processData.getKomiTrnsId());

		Pacs002Seed seed = new Pacs002Seed();
		
		seed.setStatus(aeResp.getStatus());
		seed.setReason(aeResp.getReason());

		seed.setMsgId(msgId);
		
		seed.setCreditorName(aeResp.getCreditorName());
		seed.setCreditorAccountNo(aeResp.getAccountNumber());
		seed.setCreditorAccountIdType(aeResp.getAccountType());
		seed.setCreditorType(aeResp.getCreditorType());
		seed.setCreditorId(aeResp.getCreditorId());
		seed.setCreditorTown(aeResp.getTownName());
		seed.setCreditorResidentialStatus(aeResp.getResidentStatus());

		
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(msg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId(), 
									"pacs.002.001.10", bizMsgId);

		FIToFIPaymentStatusReportV10 response = pacs002Service.accountEnquiryResponse(seed, msg);
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);
		
		processData.setBiResponseMsg(busMesg);
		exchange.getMessage().setHeader("hdr_process_data", processData);
		
		exchange.getMessage().setBody(busMesg);

	}

}
