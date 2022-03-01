package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.AccountEnquiryInboundResponse;
import bifast.inbound.iso20022.AppHeaderService;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.Pacs002Seed;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.service.Pacs002MessageService;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;

@Component
public class IsoAEResponsePrc implements Processor {
	@Autowired
	private AppHeaderService hdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;
	@Autowired
	private UtilService utilService;
	
	private static Logger logger = LoggerFactory.getLogger(IsoAEResponsePrc.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		Object oResp = exchange.getMessage().getBody(Object.class);
		
		AccountEnquiryInboundResponse aeResp = new AccountEnquiryInboundResponse();
		FaultPojo fault = new FaultPojo();
		
		if (oResp.getClass().getSimpleName().equals("AccountEnquiryInboundResponse"))
			aeResp = (AccountEnquiryInboundResponse) oResp;
		else
			fault = (FaultPojo) oResp;
		
		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);

		String msgId = utilService.genMsgId("510", processData.getKomiTrnsId());
		BusinessMessage msg = processData.getBiRequestMsg();

		Pacs002Seed seed = new Pacs002Seed();
		seed.setMsgId(msgId);
		seed.setCreditorAccountNo(msg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());

		logger.debug("response class: " + oResp.getClass().getSimpleName());
		
		if (oResp.getClass().getSimpleName().equals("AccountEnquiryInboundResponse")) {
			seed.setStatus(aeResp.getStatus());
			if (aeResp.getReason().equals("U101")) {
				if (null == aeResp.getAccountType())
					seed.setReason("52");
				else if (aeResp.getAccountType().equals("SVGS"))
					seed.setReason("53");
				else
					seed.setReason("52");
			}
			else if (aeResp.getReason().equals("U102"))
				seed.setReason("78");
			else if (!(aeResp.getReason().equals("U000")))
				seed.setReason("62");
			else 
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
			
			if (fault.getReasonCode().equals("U101")) 
				seed.setReason("52");
			else if (fault.getReasonCode().equals("U102"))
				seed.setReason("78");
			else 
				seed.setReason("62");
		}
			
		seed.setCreditorAccountIdType("CACC");

		String bizMsgId = utilService.genRfiBusMsgId("510", processData.getKomiTrnsId());

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = hdrService.getAppHdr(	"pacs.002.001.10", bizMsgId);

		FIToFIPaymentStatusReportV10 response = pacs002Service.accountEnquiryResponse(seed, msg);
		
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(response);
		
		BusinessMessage busMesg = new BusinessMessage();
		busMesg.setAppHdr(hdr);
		busMesg.setDocument(doc);
		
		processData.setBiResponseMsg(busMesg);
		exchange.setProperty("prop_process_data", processData);
		
		exchange.getMessage().setBody(busMesg);

	}

}
