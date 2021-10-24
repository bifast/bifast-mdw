package bifast.outbound.credittransfer.processor;

import java.math.BigDecimal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs008MessageService;
import bifast.library.iso20022.service.Pacs008Seed;
import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.service.UtilService;

@Component
public class BuildCTRequestProcessor implements Processor {

	@Autowired
	private Config config;
	@Autowired
	private AppHeaderService appHeaderService;
	@Autowired
	private Pacs008MessageService pacs008MessageService;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();

		Pacs008Seed seedCreditTrn = new Pacs008Seed();

		String msgType = "";
		if (null == chnReq.getCrdtProxyIdValue()) 
			msgType = "010";	
		else	
			msgType = "110";	
			
		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();

		String bizMsgId = utilService.genOfiBusMsgId(msgType, rmw.getChannelType());
		
		String msgId = utilService.genMessageId(msgType);

		seedCreditTrn.setMsgId(msgId);
		seedCreditTrn.setAmount(new BigDecimal(chnReq.getAmount()));
		seedCreditTrn.setBizMsgId(bizMsgId);
		
		seedCreditTrn.setCategoryPurpose(chnReq.getCategoryPurpose());
		
		seedCreditTrn.setChannel(rmw.getChannelType());
		
		seedCreditTrn.setCrdtAccountNo(chnReq.getCrdtAccountNo());		
		seedCreditTrn.setCrdtAccountType(chnReq.getCrdtAccountType());
		seedCreditTrn.setCrdtId(chnReq.getCrdtId());
		seedCreditTrn.setCrdtType(chnReq.getCrdtType());
		seedCreditTrn.setCrdtName(chnReq.getCrdtName());
		seedCreditTrn.setCrdtResidentStatus(chnReq.getCrdtResidentialStatus());
		seedCreditTrn.setCrdtTownName(chnReq.getCrdtTownName());
		
		
		seedCreditTrn.setDbtrAccountNo(chnReq.getDbtrAccountNo());
		seedCreditTrn.setDbtrAccountType(chnReq.getDbtrAccountType());
		seedCreditTrn.setDbtrName(chnReq.getDbtrName());
		seedCreditTrn.setDbtrId(chnReq.getDbtrId());
		seedCreditTrn.setDbtrType(chnReq.getDbtrType());
		seedCreditTrn.setDbtrResidentStatus(chnReq.getDbtrResidentialStatus());
		seedCreditTrn.setDbtrTownName(chnReq.getDbtrTownName());

		seedCreditTrn.setOrignBank(config.getBankcode());
		seedCreditTrn.setPaymentInfo(chnReq.getPaymentInfo());
		seedCreditTrn.setRecptBank(chnReq.getRecptBank());
		
		if (!(null == chnReq.getCrdtProxyIdValue())) {
			seedCreditTrn.setCrdtProxyIdType(msgId);
			seedCreditTrn.setCrdtProxyIdValue(msgId);
		}
			
		seedCreditTrn.setTrnType(msgType);
		
		if (!(null == chnReq.getCrdtProxyIdType())) {
			seedCreditTrn.setCrdtProxyIdType(chnReq.getCrdtProxyIdType());
			seedCreditTrn.setCrdtProxyIdValue(chnReq.getCrdtProxyIdValue());
		}
		
		BusinessMessage busMsg = new BusinessMessage();

		hdr = appHeaderService.getAppHdr(chnReq.getRecptBank(), "pacs.008.001.08", bizMsgId);
		busMsg.setAppHdr(hdr);

		Document doc = new Document();
		if (msgType.equals("011"))
			doc.setFiToFICstmrCdtTrf(pacs008MessageService.reverseCreditTransferRequest(seedCreditTrn));

		else 
			doc.setFiToFICstmrCdtTrf(pacs008MessageService.creditTransferRequest(seedCreditTrn));
		
		busMsg.setDocument(doc);

		rmw.setCreditTransferRequest(busMsg);
		exchange.getMessage().setHeader("hdr_request_list", rmw);

		exchange.getIn().setBody(busMsg);
		
	}

}
