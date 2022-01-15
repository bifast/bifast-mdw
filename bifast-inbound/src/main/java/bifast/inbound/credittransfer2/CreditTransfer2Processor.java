package bifast.inbound.credittransfer2;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs002MessageService;
import bifast.library.iso20022.service.Pacs002Seed;

@Component
@ComponentScan(basePackages = {"bifast.library.iso20022.service", "bifast.library.config"} )
public class CreditTransfer2Processor implements Processor {

	@Autowired private AppHeaderService appHdrService;
	@Autowired private Pacs002MessageService pacs002Service;
	@Autowired private UtilService utilService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Override
	public void process(Exchange exchange) throws Exception {
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);

		String saf = exchange.getMessage().getHeader("ct_saf", String.class);
		
		CbAccountEnquiryResponsePojo cbResponse = null;
		FaultPojo fault = null;

		Object oCbResp = processData.getCorebankResponse();
		if (null != oCbResp) {
			if (oCbResp.getClass().getSimpleName().equals("CbAccountEnquiryResponsePojo"))
				cbResponse = (CbAccountEnquiryResponsePojo) oCbResp;
			else if (oCbResp.getClass().getSimpleName().equals("FaultPojo"))
				fault = (FaultPojo) oCbResp;
		}
		
		FlatPacs008Pojo flatRequest = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
		CreditTransferTransaction39 biReq =  reqBusMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		String msgType = processData.getBiRequestMsg().getAppHdr().getBizMsgIdr().substring(16, 19);

		String bizMsgId = utilService.genRfiBusMsgId(msgType, processData.getKomiTrnsId());
		String msgId = utilService.genMsgId(msgType, processData.getKomiTrnsId());

		Pacs002Seed resp = new Pacs002Seed();
		resp.setMsgId(msgId);
		resp.setCreditorAccountIdType(flatRequest.getCreditorAccountType());

		
		//SAF = NO --> reply as-is
		//if SAF = OLD/NEW --> reply ok
		if (saf.equals("NO")) {
			
			if (null != cbResponse) {
				resp.setStatus(cbResponse.getStatus());
				resp.setReason(cbResponse.getReason());				
//				resp.setAdditionalInfo(cbResponse.getgetAdditionalInfo());
			} else {
				resp.setStatus(fault.getResponseCode());
				resp.setReason(fault.getReasonCode());	
			}
			

			if ((resp.getReason().equals("U101") && (resp.getCreditorAccountIdType().equals("SVGS"))))
				resp.setReason("53");
			else if (resp.getReason().equals("U101"))
				resp.setReason("52");
			else if (resp.getReason().equals("U102"))
				resp.setReason("78");
			else if (!(resp.getReason().equals("U000")))
				resp.setReason("62");


		}
		else {
			resp.setStatus("ACTC");
			resp.setReason("U000");
		}
		

		resp.setCreditorResidentialStatus(flatRequest.getCreditorResidentialStatus());  // 01 RESIDENT
		resp.setCreditorTown(flatRequest.getCreditorTownName());  
		resp.setCreditorType(flatRequest.getCreditorType());
		if (null != flatRequest.getCreditorPrvId())
			resp.setCreditorId(flatRequest.getCreditorPrvId());
		else
			resp.setCreditorId(flatRequest.getCreditorOrgId());
			
		if (!(null == biReq.getCdtr().getNm())) 
			resp.setCreditorName(biReq.getCdtr().getNm());
		
		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.creditTransferRequestResponse(resp, reqBusMesg);
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		
		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr(orignBank, "pacs.002.001.10", bizMsgId);
		appHdr.setBizSvc("CLEAR");
		
		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);
		respBusMesg.setDocument(doc);
		
		processData.setBiResponseMsg(respBusMesg);
		exchange.getMessage().setHeader("hdr_process_data", processData);
		exchange.getIn().setBody(respBusMesg);

	}

}
