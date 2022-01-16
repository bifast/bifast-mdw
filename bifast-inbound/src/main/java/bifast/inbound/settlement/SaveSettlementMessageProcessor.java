package bifast.inbound.settlement;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.model.Settlement;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.repository.SettlementRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;

@Component
public class SaveSettlementMessageProcessor implements Processor {
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private SettlementRepository settlementRepo;

	public void process(Exchange exchange) throws Exception {
		 
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		FlatPacs002Pojo flatSttl = (FlatPacs002Pojo) processData.getBiRequestFlat();

		String fullReqMsg = exchange.getMessage().getHeader("hdr_frBI_jsonzip",String.class);

//		BusinessMessage settlRequest = exchange.getMessage().getHeader("hdr_frBIobj",BusinessMessage.class);
		
//		BusinessApplicationHeaderV01 sttlHeader = settlRequest.getAppHdr();
//		FIToFIPaymentStatusReportV10 settlBody = settlRequest.getDocument().getFiToFIPmtStsRpt();
			
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(flatSttl.getOrgnlEndToEndId());
		sttl.setSettlConfBizMsgId(flatSttl.getBizMsgIdr());
		
		sttl.setOrignBank(flatSttl.getDbtrAgtFinInstnId());
		sttl.setRecptBank(flatSttl.getCdtrAgtFinInstnId());
		
		if (!(null == flatSttl.getCdtrAcctId()))
			sttl.setCrdtAccountNo(flatSttl.getCdtrAcctId());
		
//		sttl.setCrdtAccountType(orglBizMsgId);
//		sttl.setCrdtId(orglBizMsgId);
//		sttl.setCrdtIdType(orglBizMsgId);
//		sttl.setCrdtName(orglBizMsgId);
		
		if (!(null == flatSttl.getDbtrAcctId()))
			sttl.setDbtrAccountNo(flatSttl.getDbtrAcctId());
//		sttl.setDbtrAccountType(orglBizMsgId);
//		sttl.setDbtrId(orglBizMsgId);
//		sttl.setDbtrIdType(orglBizMsgId);
//		sttl.setDbtrName(orglBizMsgId);
		
//		sttl.setCrdtBankAccountNo(
//				settlBody.getTxInfAndSts().get(0).getSplmtryData().get(0).getEnvlp().getCdtrAgtAcct().getId().getOthr().getId());
//		
//		sttl.setDbtrBankAccountNo(
//				settlBody.getTxInfAndSts().get(0).getSplmtryData().get(0).getEnvlp().getDbtrAgtAcct().getId().getOthr().getId());

		sttl.setReceiveDate(LocalDateTime.now());
		sttl.setFullMessage(fullReqMsg);
		
		settlementRepo.save(sttl);
		
///////////////////////		
		
		List<CreditTransfer> lCrdtTrns = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flatSttl.getOrgnlEndToEndId());
		CreditTransfer ct = null;
		for (CreditTransfer runningCT : lCrdtTrns) {
			if ((runningCT.getResponseCode().equals("ACTC")) ||
				(runningCT.getResponseCode().equals("ACSC"))) {
				ct = runningCT;
				break;
			}
		}

		if (null != ct) {
			ct.setSettlementConfBizMsgIdr(flatSttl.getBizMsgIdr());
			ct.setCbStatus("READY");
			ctRepo.save(ct);
//			sttlRequest.setOrgnlKomiTrnsId(ct.getKomiTrnsId());
		}


	}
}
