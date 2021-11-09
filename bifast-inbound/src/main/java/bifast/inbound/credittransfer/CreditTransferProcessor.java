package bifast.inbound.credittransfer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import bifast.inbound.config.Config;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
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
public class CreditTransferProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHdrService;
	@Autowired
	private Pacs002MessageService pacs002Service;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Override
	public void process(Exchange exchange) throws Exception {

		CbCreditResponsePojo cbResponse = exchange.getMessage().getBody(CbCreditResponsePojo.class);
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs008Pojo flatRequest = (FlatPacs008Pojo) processData.getBiRequestFlat();
		
		BusinessMessage reqBusMesg = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
		CreditTransferTransaction39 biReq =  reqBusMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		String strToday = LocalDate.now().format(formatter);
		String bizMsgId = strToday + config.getBankcode() + "510R99" + processData.getKomiTrnsId();
		String msgId = strToday + config.getBankcode() + "510" + processData.getKomiTrnsId();
		
		Pacs002Seed resp = new Pacs002Seed();
		resp.setMsgId(msgId);

		resp.setAdditionalInfo(cbResponse.getAdditionalInfo());
		resp.setCreditorResidentialStatus(flatRequest.getCreditorResidentialStatus());  // 01 RESIDENT
		resp.setCreditorTown(flatRequest.getCreditorTownName());  
		resp.setCreditorType(flatRequest.getCreditorType());
		if (null != flatRequest.getCreditorPrvId())
			resp.setCreditorId(flatRequest.getCreditorPrvId());
		else
			resp.setCreditorId(flatRequest.getCreditorOrgId());
			
		if (!(null == biReq.getCdtr().getNm())) 
			resp.setCreditorName(biReq.getCdtr().getNm());

		//SAF = NO --> reply as-is
		//if SAF = OLD/NEW --> reply ok
		String saf = exchange.getMessage().getHeader("ct_saf", String.class);
		
		if (saf.equals("NO")) {
			resp.setStatus(cbResponse.getStatus());
			resp.setReason(cbResponse.getReason());
		}
		
		else {
			resp.setStatus("ACTC");
			resp.setReason("U000");
		}
			

		FIToFIPaymentStatusReportV10 respMsg = pacs002Service.creditTransferRequestResponse(resp, reqBusMesg);
		Document doc = new Document();
		doc.setFiToFIPmtStsRpt(respMsg);
		
		String orignBank = reqBusMesg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		BusinessApplicationHeaderV01 appHdr = appHdrService.getAppHdr(orignBank, "pacs002.001.10", bizMsgId);
		appHdr.setBizSvc("CLEAR");
		
		BusinessMessage respBusMesg = new BusinessMessage();
		respBusMesg.setAppHdr(appHdr);
		respBusMesg.setDocument(doc);
		
		exchange.getIn().setBody(respBusMesg);

	}

}
