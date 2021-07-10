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
		String sttlMsgName = sttlHeader.getMsgDefIdr();
	
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setSettlConfMesgName(sttlMsgName);
		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		sttl.setFullMessage(strMesg);
		// check apakah harus reversal ?
//		Optional<CreditTransfer> optCreditTrn = creditTrnRepo.findByCrdtTrnRequestBizMsgIdr(orglBizMsgId);
//
//		if (optCreditTrn.isPresent()) {
//			System.out.print("Present, dan ");
//			if (optCreditTrn.get().getCrdtTrnResponseStatus().equals("ACTC")) {
//				sttl.setForReversal("N");
//			}
//			else {
//				sttl.setForReversal("Y");
//			}
//		}
//		
//		else {
//			sttl.setForReversal("Y");
//		}
		
		settlementRepo.save(sttl);

	}
	
	public void saveCreditTransfer (BusinessMessage busMesg, BusinessMessage respBi) {
		
		CreditTransferTransaction39 inbCdtTrn = busMesg.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		BusinessApplicationHeaderV01 inbMsgHeader = busMesg.getAppHdr();

		CreditTransfer cdtTrn = new CreditTransfer();
		
		cdtTrn.setMsgType("CreditTransfer");

		cdtTrn.setAmount(inbCdtTrn.getIntrBkSttlmAmt().getValue());		
		
		cdtTrn.setCrdtTrnRequestBizMsgIdr(busMesg.getAppHdr().getBizMsgIdr());
		cdtTrn.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		cdtTrn.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		if (!(null == inbCdtTrn.getDbtr().getNm())) 
			cdtTrn.setCreditorAccount(inbCdtTrn.getDbtr().getNm() );
		
		cdtTrn.setCreDt(LocalDateTime.now());
		
		cdtTrn.setDebtorAccount(inbCdtTrn.getDbtrAcct().getId().getOthr().getId());	
		cdtTrn.setCreditorAccount(inbCdtTrn.getCdtrAcct().getId().getOthr().getId());
				
		cdtTrn.setOriginatingBank(inbMsgHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		cdtTrn.setRecipientBank(inbMsgHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
	
		creditTrnRepo.save(cdtTrn);
	}
	
	public void saveFICreditTransfer (BusinessMessage busMesg, BusinessMessage respBi) {
		
		CreditTransferTransaction44 inbMsgRequest = busMesg.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0);
		BusinessApplicationHeaderV01 inbMsgHeader = busMesg.getAppHdr();

		CreditTransfer fiCreditTransfer = new CreditTransfer();

		fiCreditTransfer.setAmount(inbMsgRequest.getIntrBkSttlmAmt().getValue());		
		fiCreditTransfer.setCrdtTrnRequestBizMsgIdr(inbMsgHeader.getBizMsgIdr());

		fiCreditTransfer.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		
		fiCreditTransfer.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		
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

		revCreditTransfer.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		
		revCreditTransfer.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		
		revCreditTransfer.setCreDt(LocalDateTime.now());
		
		revCreditTransfer.setDebtorAccount(inbMsgRequest.getDbtrAcct().getId().getOthr().getId());
		revCreditTransfer.setCreditorAccount(inbMsgRequest.getCdtrAcct().getId().getOthr().getId());

		revCreditTransfer.setMsgType("ReverseCreditTransfer");
		
		revCreditTransfer.setOriginatingBank(inbMsgHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		revCreditTransfer.setRecipientBank(inbMsgHeader.getTo().getFIId().getFinInstnId().getOthr().getId());

		creditTrnRepo.save(revCreditTransfer);

	}

}
