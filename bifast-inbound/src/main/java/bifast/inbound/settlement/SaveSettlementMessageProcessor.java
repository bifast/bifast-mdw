package bifast.inbound.settlement;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.Settlement;
import bifast.inbound.repository.SettlementRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;

@Component
public class SaveSettlementMessageProcessor implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;

	public void process(Exchange exchange) throws Exception {
		 
		String fullReqMsg = exchange.getMessage().getHeader("hdr_frBI_jsonzip",String.class);

		BusinessMessage settlRequest = exchange.getMessage().getHeader("hdr_frBIobj",BusinessMessage.class);
		
		BusinessApplicationHeaderV01 sttlHeader = settlRequest.getAppHdr();
		FIToFIPaymentStatusReportV10 settlBody = settlRequest.getDocument().getFiToFIPmtStsRpt();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(settlBody.getTxInfAndSts().get(0).getOrgnlEndToEndId());
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		
		String orgnBank = sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId();
		String recptBank = sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId();

		sttl.setOrignBank(orgnBank);
		sttl.setRecptBank(recptBank);

		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		
		if (!(null == settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct()))
			sttl.setCrdtAccountNo(settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
		
//		sttl.setCrdtAccountType(orglBizMsgId);
//		sttl.setCrdtId(orglBizMsgId);
//		sttl.setCrdtIdType(orglBizMsgId);
//		sttl.setCrdtName(orglBizMsgId);
		
		if (!(null == settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct()))
			sttl.setDbtrAccountNo(settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().getId().getOthr().getId());
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

	}
}
