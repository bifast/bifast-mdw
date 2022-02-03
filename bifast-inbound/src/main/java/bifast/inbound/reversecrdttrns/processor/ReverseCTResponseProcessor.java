package bifast.inbound.reversecrdttrns.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.DebitReversalResponse;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.Pacs002Seed;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.AppHeaderService;
import bifast.inbound.service.Pacs002MessageService;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;

@Component
public class ReverseCTResponseProcessor implements Processor {
	@Autowired private AppHeaderService appHdrService;
	@Autowired private Pacs002MessageService pacs002Service;
	@Autowired private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object oResp = exchange.getMessage().getBody(Object.class);
		
		Pacs002Seed resp = new Pacs002Seed();
		
		ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flatRequest = (FlatPacs008Pojo) processData.getBiRequestFlat();

		String msgId = utilService.genMsgId("011", processData.getKomiTrnsId());
		resp.setMsgId(msgId);
		resp.setCreditorAccountIdType(flatRequest.getCreditorAccountType());
//		BusinessMessage msg = processData.getBiRequestMsg();


		FaultPojo fault = new FaultPojo();
		DebitReversalResponse cbResponse = new DebitReversalResponse();
		if (oResp.getClass().getSimpleName().equals("DebitReversalResponsePojo")) {
			cbResponse = (DebitReversalResponse) oResp;
			resp.setStatus(cbResponse.getStatus());
			resp.setReason(cbResponse.getReason());				
			resp.setAdditionalInfo(cbResponse.getAdditionalInfo());
		}
		else {
			fault = (FaultPojo) oResp;
			System.out.println("Pojo response "  + fault.getReasonCode());
			resp.setStatus(fault.getResponseCode());
			resp.setReason(fault.getReasonCode());				
//			resp.setAdditionalInfo(fault.getErrorMessage());
			
		}
		if ((resp.getReason().equals("U101") && (resp.getCreditorAccountIdType().equals("SVGS"))))
			resp.setReason("53");
		else if (resp.getReason().equals("U101"))
			resp.setReason("52");
		else if (resp.getReason().equals("U102"))
			resp.setReason("78");
		else if (!(resp.getReason().equals("U000")))
			resp.setReason("62");

		resp.setCreditorResidentialStatus(flatRequest.getCreditorResidentialStatus());  // 01 RESIDENT
		resp.setCreditorTown(flatRequest.getCreditorTownName());  
		resp.setCreditorType(flatRequest.getCreditorType());
		if (null != flatRequest.getCreditorId())
			resp.setCreditorId(flatRequest.getCreditorId());
		if (!(null == flatRequest.getCreditorName())) 
			resp.setCreditorName(flatRequest.getCreditorName());


		//////////
		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
//		CreditTransferTransaction39 biReq =  reqBusMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);


		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.creditTransferRequestResponse(resp, reqBusMesg);
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);

		String bizMsgId = utilService.genRfiBusMsgId("011", processData.getKomiTrnsId());
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr("pacs.002.001.10", bizMsgId);
		appHdr.setBizSvc("CLEAR");

		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);
		respBusMesg.setDocument(doc);

		processData.setBiResponseMsg(respBusMesg);
		exchange.setProperty("prop_process_data", processData);
		exchange.getIn().setBody(respBusMesg);

	}

}
