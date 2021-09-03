package bifast.inbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.pacs009.CreditTransferTransaction44;
import bifast.library.model.CreditTransfer;
import bifast.library.model.Settlement;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.SettlementRepository;

@Component
public class SaveTracingTableProcessor implements Processor {

	@Autowired
	private SettlementRepository settlementRepo;
	@Autowired
	private CreditTransferRepository creditTrnRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage rcvMessage = exchange.getMessage().getHeader("rcv_bi", BusinessMessage.class);
		String strMessage = exchange.getMessage().getHeader("rcv_jsonbi", String.class);
		
		String msgType = exchange.getMessage().getHeader("rcv_msgType", String.class);
		
		if (msgType.equals("SETTLEMENT")) {
			saveSettlement(rcvMessage, strMessage);
		}

		if (msgType.equals("CRDTTRN")) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
			saveCreditTransfer(rcvMessage, respBi);
		}

		else if (msgType.equals("FICDTTRN")) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
			saveFICreditTransfer(rcvMessage, respBi);	
		}

		else if (msgType.equals("REVCRDTTRN")) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
			saveReversalCreditTransfer(rcvMessage, respBi);	
		}
		
	}

	public void saveSettlement (BusinessMessage busMesg, String strMesg) {
		BusinessApplicationHeaderV01 sttlHeader = busMesg.getAppHdr();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		String orglBizMsgId = busMesg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlEndToEndId();
	
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		
//		sttl.setAmount(null);
//		sttl.setCrdtAccountNo(orglBizMsgId);
//		sttl.setCrdtAccountType(orglBizMsgId);
//		sttl.setCrdtId(orglBizMsgId);
//		sttl.setCrdtIdType(orglBizMsgId);
//		sttl.setCrdtName(orglBizMsgId);
//		sttl.setDbtrAccountNo(orglBizMsgId);
//		sttl.setDbtrAccountType(orglBizMsgId);
//		sttl.setDbtrId(orglBizMsgId);
//		sttl.setDbtrIdType(orglBizMsgId);
//		sttl.setDbtrName(orglBizMsgId);

		
		sttl.setFullMessage(strMesg);
		
		settlementRepo.save(sttl);

	}
	
	public void saveCreditTransfer (BusinessMessage busMesg, BusinessMessage respBi) {
		
		CreditTransferTransaction39 inbCdtTrn = busMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		BusinessApplicationHeaderV01 inbMsgHeader = busMesg.getAppHdr();

		CreditTransfer creditTransfer = new CreditTransfer();
		
		creditTransfer.setAmount(inbCdtTrn.getIntrBkSttlmAmt().getValue());

		creditTransfer.setCrdtTrnRequestBizMsgIdr(busMesg.getAppHdr().getBizMsgIdr());
		creditTransfer.setCreditorAccountNumber(inbCdtTrn.getCdtrAcct().getId().getOthr().getId());
		creditTransfer.setCreditorId(null);
		creditTransfer.setCreditorType(null);
		creditTransfer.setCreDt(LocalDateTime.now());

		creditTransfer.setDebtorAccountNumber(inbCdtTrn.getDbtrAcct().getId().getOthr().getId());
		creditTransfer.setDebtorId(null);
		creditTransfer.setDebtorType(null);
//		creditTransfer.setIntrRefId(null);
		creditTransfer.setLogMessageId(null);
		creditTransfer.setMsgType(null);
		creditTransfer.setOriginatingBank(inbMsgHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		creditTransfer.setRecipientBank(inbMsgHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		creditTransfer.setReversal(null);
		creditTransfer.setSettlementBizMsgId(null);
		creditTransfer.setStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());

		
//		cdtTrn.setMsgType("CreditTransfer");
//		cdtTrn.setCreditorType(null);
//		
//		
//		if (!(null==inbCdtTrn.getCdtr().getId().getPrvtId()))
//			cdtTrn.setCreditorId(inbCdtTrn.getCdtr().getId().getPrvtId().getOthr().get(0).getId());
//		else if (!(null==inbCdtTrn.getCdtr().getId().getOrgId()))
//			cdtTrn.setCreditorId(inbCdtTrn.getCdtr().getId().getOrgId().getOthr().get(0).getId());
//		
	
		creditTrnRepo.save(creditTransfer);
	}
	
	public void saveFICreditTransfer (BusinessMessage busMesg, BusinessMessage respBi) {
		
		CreditTransferTransaction44 inbMsgRequest = busMesg.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0);
		BusinessApplicationHeaderV01 inbMsgHeader = busMesg.getAppHdr();

		CreditTransfer fiCreditTransfer = new CreditTransfer();

		fiCreditTransfer.setAmount(inbMsgRequest.getIntrBkSttlmAmt().getValue());		
		fiCreditTransfer.setCrdtTrnRequestBizMsgIdr(inbMsgHeader.getBizMsgIdr());

		fiCreditTransfer.setStatus(respBi.getAppHdr().getBizMsgIdr());
		
		fiCreditTransfer.setCreDt(LocalDateTime.now());
		
		fiCreditTransfer.setMsgType("FICreditTransfer");
		
		fiCreditTransfer.setOriginatingBank(inbMsgHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		fiCreditTransfer.setRecipientBank(inbMsgHeader.getTo().getFIId().getFinInstnId().getOthr().getId());

		creditTrnRepo.save(fiCreditTransfer);
	}

	public void saveReversalCreditTransfer (BusinessMessage busMesg, BusinessMessage respBi) {
		
		CreditTransferTransaction39 inbMsgRequest = busMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		BusinessApplicationHeaderV01 inbMsgHeader = busMesg.getAppHdr();

		CreditTransfer revCreditTransfer = new CreditTransfer();

		revCreditTransfer.setAmount(inbMsgRequest.getIntrBkSttlmAmt().getValue());		
		revCreditTransfer.setCrdtTrnRequestBizMsgIdr(inbMsgHeader.getBizMsgIdr());

		revCreditTransfer.setStatus(respBi.getAppHdr().getBizMsgIdr());
		
		revCreditTransfer.setCreDt(LocalDateTime.now());
		
		revCreditTransfer.setDebtorAccountNumber(inbMsgRequest.getDbtrAcct().getId().getOthr().getId());
		revCreditTransfer.setCreditorAccountNumber(inbMsgRequest.getCdtrAcct().getId().getOthr().getId());

		revCreditTransfer.setMsgType("ReverseCreditTransfer");
		
		revCreditTransfer.setOriginatingBank(inbMsgHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		revCreditTransfer.setRecipientBank(inbMsgHeader.getTo().getFIId().getFinInstnId().getOthr().getId());

		creditTrnRepo.save(revCreditTransfer);

	}

}
