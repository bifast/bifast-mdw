package bifast.outbound.processor;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.pacs009.CreditTransferTransaction44;
import bifast.library.model.CreditTransfer;
import bifast.library.repository.CreditTransferRepository;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;

@Component
public class SaveTracingTableProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTrnRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage reqBi = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
		String msgType = exchange.getMessage().getHeader("req_msgType", String.class);
		
		if (msgType.equals("CreditTransfer")) {
			ChannelCreditTransferRequest chnReq  = exchange.getMessage().getHeader("req_channelReq", ChannelCreditTransferRequest.class);
			String refId = chnReq.getIntrnRefId();
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
			saveCreditTransfer(refId, reqBi, respBi);
		}

		else if (msgType.equals("FICreditTransfer")) {
			ChannelFICreditTransferReq chnReq  = exchange.getMessage().getHeader("req_channelReq", ChannelFICreditTransferReq.class);
			String intrnRefId = chnReq.getIntrnRefId();
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
			saveFICreditTransfer(intrnRefId, reqBi, respBi);	
		}

		else if (msgType.equals("ReverseCreditTransfer")) {
			ChannelReverseCreditTransferRequest chnReq  = exchange.getMessage().getHeader("req_channelReq", ChannelReverseCreditTransferRequest.class);
			String intrnRefId = chnReq.getIntrnRefId();
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
			saveReversalCreditTransfer(intrnRefId, reqBi, respBi);	
		}
		

	}


	public void saveCreditTransfer (String intrnRefId, BusinessMessage reqBi, BusinessMessage respBi) {
		
		CreditTransferTransaction39 reqCdtTrn = reqBi.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		CreditTransfer cdtTrn = new CreditTransfer();
		
		cdtTrn.setMsgType("CreditTransfer");
		cdtTrn.setIntrRefId(intrnRefId);
		cdtTrn.setAmount(reqCdtTrn.getIntrBkSttlmAmt().getValue());	
		
		cdtTrn.setCrdtTrnRequestBizMsgIdr(reqBi.getAppHdr().getBizMsgIdr());
		
		if (respBi.getAppHdr().getBizSvc().equals("SETTLEMENTCONFIRMATION"))
			cdtTrn.setSettlementBizMsgId(respBi.getAppHdr().getBizMsgIdr());
		else
			cdtTrn.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		
		cdtTrn.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		cdtTrn.setCreDt(LocalDateTime.now());
		
		cdtTrn.setDebtorAccount(reqCdtTrn.getDbtrAcct().getId().getOthr().getId());
		cdtTrn.setCreditorAccount(reqCdtTrn.getCdtrAcct().getId().getOthr().getId());
		
		cdtTrn.setOriginatingBank(reqBi.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		cdtTrn.setRecipientBank(reqBi.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
	
		creditTrnRepo.save(cdtTrn);
	}
	
	public void saveFICreditTransfer (String intrnRefId, BusinessMessage reqBi, BusinessMessage respBi) {
		
		CreditTransferTransaction44 reqFICdtTrn = reqBi.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0);

		CreditTransfer fiCreditTransfer = new CreditTransfer();

		fiCreditTransfer.setMsgType("FICreditTransfer");
		fiCreditTransfer.setIntrRefId(intrnRefId);
		fiCreditTransfer.setAmount(reqFICdtTrn.getIntrBkSttlmAmt().getValue());		

		fiCreditTransfer.setCrdtTrnRequestBizMsgIdr(reqBi.getAppHdr().getBizMsgIdr());
		fiCreditTransfer.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		fiCreditTransfer.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());		
		
		fiCreditTransfer.setCreDt(LocalDateTime.now());
		
		fiCreditTransfer.setOriginatingBank(reqBi.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		fiCreditTransfer.setRecipientBank(reqBi.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		creditTrnRepo.save(fiCreditTransfer);
	}

	public void saveReversalCreditTransfer (String intrnRefId, BusinessMessage reqBi, BusinessMessage respBi) {
		
		CreditTransferTransaction39 inbMsgRequest = reqBi.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);

		CreditTransfer revCreditTransfer = new CreditTransfer();

		revCreditTransfer.setMsgType("ReverseCreditTransfer");
		revCreditTransfer.setIntrRefId(intrnRefId);
		revCreditTransfer.setAmount(inbMsgRequest.getIntrBkSttlmAmt().getValue());		

		revCreditTransfer.setCrdtTrnRequestBizMsgIdr(reqBi.getAppHdr().getBizMsgIdr());
		revCreditTransfer.setCrdtTrnResponseBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
		revCreditTransfer.setCrdtTrnResponseStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		
		revCreditTransfer.setCreDt(LocalDateTime.now());

		if (!(null == inbMsgRequest.getDbtrAcct()))  
			revCreditTransfer.setDebtorAccount(inbMsgRequest.getDbtrAcct().getId().getOthr().getId());
		
		if (!(null == inbMsgRequest.getCdtrAcct()))  
			revCreditTransfer.setCreditorAccount(inbMsgRequest.getCdtrAcct().getId().getOthr().getId());

		
		revCreditTransfer.setOriginatingBank(reqBi.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		revCreditTransfer.setRecipientBank(reqBi.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		creditTrnRepo.save(revCreditTransfer);
	}

	

}
