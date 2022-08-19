package bifast.outbound.reversect;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.BISupplementaryDataEnvelope1;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.outbound.config.Config;
import bifast.outbound.iso20022.ApplHeaderService;
import bifast.outbound.iso20022.Pacs008MsgService;
import bifast.outbound.iso20022.Pacs008Seed;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.CallRouteService;
import bifast.outbound.service.UtilService;

@Component
public class BuildRevCTRequestProcessor implements Processor {

	@Autowired	private ApplHeaderService appHeaderService;
	@Autowired	private Config config;
	@Autowired	private Pacs008MsgService pacs008MessageService;
	@Autowired	private UtilService utilService;
	@Autowired	private CallRouteService callRouteService;
	
	@Override
	public void process(Exchange exchange) throws Exception {

//		@SuppressWarnings("unchecked")
//		HashMap<String, Object> arr = exchange.getMessage().getBody(HashMap.class);
		RCTQueryResultDTO qry = exchange.getProperty("pr_rctrequest", RCTQueryResultDTO.class);
		
//		String encrMsg = (String) arr.get("txt_req_msg");
		exchange.getMessage().setBody(qry.getFullTextMsg());
		BusinessMessage bmReq = callRouteService.decrypt_unmarshal(exchange);
		
		CreditTransferTransaction39 ctReq = bmReq.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		
		Pacs008Seed seedCreditTrn = new Pacs008Seed();

		String komiTrnsId = utilService.genKomiTrnsId();		

		String bizMsgId = utilService.genBusMsgId("110", komiTrnsId, "99");
		String msgId = utilService.genMessageId("110", komiTrnsId);

		seedCreditTrn.setTrnType("110");

		seedCreditTrn.setBizMsgId(bizMsgId);
		seedCreditTrn.setMsgId(msgId);
		seedCreditTrn.setAmount(ctReq.getIntrBkSttlmAmt().getValue());
		
		seedCreditTrn.setCategoryPurpose(ctReq.getPmtTpInf().getCtgyPurp().getPrtry().substring(3, 5));

		seedCreditTrn.setChannel("99");

		seedCreditTrn.setCrdtAccountNo(ctReq.getDbtrAcct().getId().getOthr().getId());		
		seedCreditTrn.setCrdtAccountType(ctReq.getDbtrAcct().getTp().getPrtry());
		
		if (null != ctReq.getDbtr().getId()) {
			if (null != ctReq.getDbtr().getId().getPrvtId())
				seedCreditTrn.setCrdtId(ctReq.getDbtr().getId().getPrvtId().getOthr().get(0).getId());
			else if (null != ctReq.getDbtr().getId().getOrgId())
				seedCreditTrn.setCrdtId(ctReq.getDbtr().getId().getOrgId().getOthr().get(0).getId());
		}
		
		BISupplementaryDataEnvelope1 suppl = null;
		if (null != ctReq.getSplmtryData())
			if (ctReq.getSplmtryData().size()>0) 
				if (null != ctReq.getSplmtryData().get(0).getEnvlp()) 
					if (null != ctReq.getSplmtryData().get(0).getEnvlp().getDtl()) 
						suppl = ctReq.getSplmtryData().get(0).getEnvlp().getDtl();

		if ((null != suppl) && (null != suppl.getDbtr())) {
			seedCreditTrn.setCrdtType(suppl.getDbtr().getTp());
			seedCreditTrn.setCrdtName(ctReq.getDbtr().getNm());
			seedCreditTrn.setCrdtResidentStatus(suppl.getDbtr().getRsdntSts());
			seedCreditTrn.setCrdtTownName(suppl.getDbtr().getTwnNm());
		}	
		
		seedCreditTrn.setDbtrAccountNo(ctReq.getCdtrAcct().getId().getOthr().getId());
		seedCreditTrn.setDbtrAccountType(ctReq.getCdtrAcct().getTp().getPrtry());
		seedCreditTrn.setDbtrName(ctReq.getCdtr().getNm());
		
		if (null != ctReq.getCdtr().getId()) {
			if (null != ctReq.getCdtr().getId().getPrvtId())
				seedCreditTrn.setDbtrId(ctReq.getCdtr().getId().getPrvtId().getOthr().get(0).getId());
			else if (null != ctReq.getDbtr().getId().getOrgId())
				seedCreditTrn.setDbtrId(ctReq.getCdtr().getId().getOrgId().getOthr().get(0).getId());
		}
		
		if (null != suppl) {
			if (null != suppl.getCdtr()) {
				seedCreditTrn.setDbtrType(suppl.getCdtr().getTp()); 
				seedCreditTrn.setDbtrResidentStatus(suppl.getCdtr().getRsdntSts());
				seedCreditTrn.setDbtrTownName(suppl.getCdtr().getTwnNm());
			}
		}
	
		seedCreditTrn.setPaymentInfo("O");

		seedCreditTrn.setOrignBank(config.getBankcode());
		seedCreditTrn.setRecptBank(ctReq.getDbtrAgt().getFinInstnId().getOthr().getId());

//		if (!(null == chnReq.getCrdtProxyIdValue())) {
//			seedCreditTrn.setCrdtProxyIdType(msgId);
//			seedCreditTrn.setCrdtProxyIdValue(msgId);
//		}
		
		
//		if (!(null == chnReq.getCrdtProxyIdType())) {
//			seedCreditTrn.setCrdtProxyIdType(chnReq.getCrdtProxyIdType());
//			seedCreditTrn.setCrdtProxyIdValue(chnReq.getCrdtProxyIdValue());
//		}
	
		seedCreditTrn.setEndToEndId(ctReq.getPmtId().getEndToEndId());
		
		BusinessMessage busMsg = new BusinessMessage();
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		hdr = appHeaderService.getAppHdr(ctReq.getDbtrAgt().getFinInstnId().getOthr().getId(), "pacs.008.001.08", bizMsgId);
		busMsg.setAppHdr(hdr);
		
		Document doc = new Document();
		doc.setFiToFICstmrCdtTrf(pacs008MessageService.reverseCreditTransferRequest(seedCreditTrn));
		
		busMsg.setDocument(doc);

		exchange.getIn().setBody(busMsg);
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		rmw.setCreditTransferRequest(busMsg);
		exchange.setProperty("prop_request_list", rmw);
				
	}

}
