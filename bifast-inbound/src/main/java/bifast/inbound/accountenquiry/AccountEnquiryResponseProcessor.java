package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.pojo.FaultPojo;
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

		Object oResp = exchange.getMessage().getBody(Object.class);
		
		CbAccountEnquiryResponsePojo aeResp = new CbAccountEnquiryResponsePojo();
		FaultPojo fault = new FaultPojo();
		
		if (oResp.getClass().getSimpleName().equals("CbAccountEnquiryResponsePojo"))
			aeResp = (CbAccountEnquiryResponsePojo) oResp;
		else
			fault = (FaultPojo) oResp;
		
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);

		String msgId = utilService.genMsgId("510", processData.getKomiTrnsId());
		BusinessMessage msg = processData.getBiRequestMsg();

		Pacs002Seed seed = new Pacs002Seed();
		seed.setMsgId(msgId);
		seed.setCreditorAccountNo(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());

		if (oResp.getClass().getSimpleName().equals("CbAccountEnquiryResponsePojo")) {
			seed.setStatus(aeResp.getStatus());
			seed.setReason(aeResp.getReason());
			
			seed.setCreditorName(aeResp.getCreditorName());
			seed.setCreditorAccountIdType(aeResp.getAccountType());
			seed.setCreditorType(aeResp.getCreditorType());
			seed.setCreditorId(aeResp.getCreditorId());
			seed.setCreditorTown(aeResp.getTownName());
			seed.setCreditorResidentialStatus(aeResp.getResidentStatus());
		}
		else {
			seed.setStatus(fault.getResponseCode());
			seed.setReason(fault.getReasonCode());	
		}
			
		String bizMsgId = utilService.genRfiBusMsgId("510", processData.getKomiTrnsId());
		

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
