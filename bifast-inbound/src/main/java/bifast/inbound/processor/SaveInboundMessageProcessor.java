package bifast.inbound.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.model.CreditTransfer;
import bifast.library.model.DomainCode;
import bifast.library.model.InboundMessage;
import bifast.library.model.Settlement;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.DomainCodeRepository;
import bifast.library.repository.InboundMessageRepository;
import bifast.library.repository.SettlementRepository;

@Component
public class SaveInboundMessageProcessor implements Processor {

	@Autowired
	private InboundMessageRepository inboundMessageRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private SettlementRepository settlementRepo;
	@Autowired
	private CreditTransferRepository creditTrnRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		 
		BusinessMessage rcvBi = exchange.getMessage().getHeader("rcv_bi",BusinessMessage.class);
		BusinessApplicationHeaderV01 hdr = rcvBi.getAppHdr();

		InboundMessage inboundMsg = new InboundMessage();
		
		inboundMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		inboundMsg.setBizSvc(hdr.getBizSvc());
		inboundMsg.setFrFinId(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		inboundMsg.setReceiveDt(LocalDateTime.now());
		
		if (!(null==hdr.getCpyDplct()))
				inboundMsg.setCopyDupl(hdr.getCpyDplct().name());
		
		inboundMsg.setReceiveDt(LocalDateTime.now());

		if (!(null == exchange.getMessage().getHeader("resp_bi"))) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi",BusinessMessage.class);

			inboundMsg.setRespStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			inboundMsg.setRespBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
			
//			inboundMsg.setRespRejectMsg(null);
		}
//		inboundMsg.setRespErrorMsg(null);
		
		String msgType = exchange.getMessage().getHeader("rcv_msgType", String.class);
		String msgName = "";
		if (msgType.equals("Settlement")) 
			msgName = "Settlement";
		else {
			String trxCode = hdr.getBizMsgIdr().substring(16,19);
			msgName = domainCodeRepo.findByGrpAndKey("TRANSACTION.TYPE", trxCode).orElse(new DomainCode()).getValue();
		}
		inboundMsg.setMessageName(msgName);

		// simpan data waktu 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		String strCihubRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCihubRequestTime, dtf);
		inboundMsg.setCihubRequestTime(cihubRequestTime);

		String strCihubResponseTime = exchange.getMessage().getHeader("req_cihubResponseTime", String.class);
		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCihubResponseTime, dtf);
		inboundMsg.setCihubResponseTime(cihubResponseTime);

		inboundMessageRepo.save(inboundMsg);
		
		
		if (msgType.equals("SETTLEMENT")) {
			saveSettlement(rcvBi, inboundMsg);
		}

		else if (msgType.equals("CRDTTRN")) {
			String reversal = exchange.getMessage().getHeader("resp_reversal",String.class);

			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
			saveCreditTransfer(inboundMsg, rcvBi, reversal);
		}

		else if (msgType.equals("FICDTTRN")) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
//			saveFICreditTransfer(rcvMessage, respBi);	
		}

		else if (msgType.equals("REVCRDTTRN")) {
			BusinessMessage respBi = exchange.getMessage().getHeader("resp_bi", BusinessMessage.class);
//			saveReversalCreditTransfer(rcvMessage, respBi);	
		}


	}

	public void saveSettlement (BusinessMessage settlRequest, InboundMessage inbMsg) {
		BusinessApplicationHeaderV01 sttlHeader = settlRequest.getAppHdr();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		
		String orglBizMsgId = settlRequest.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getOrgnlEndToEndId();
	
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(orglBizMsgId);
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		
		sttl.setLogMessageId(inbMsg.getId());
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
		
//		sttl.setFullMessage(strMesg);
		
		settlementRepo.save(sttl);

	}

	private void saveCreditTransfer (InboundMessage logTable, BusinessMessage inboundMsg, String reversal) 
	{
		FIToFICustomerCreditTransferV08 creditTransferReq = inboundMsg.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = inboundMsg.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		CreditTransfer ct = new CreditTransfer();
		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(inboundMsg.getAppHdr().getBizMsgIdr());
		
		ct.setStatus(logTable.getRespStatus());
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		
		// jika node splmtryData ada, ambil data custType dari sini; jika tidak maka cek apakah ada di prvtId atau orgId
		
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr()))
				ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		}
		
		else if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId())) {
				ct.setCreditorType("01");
			}

		else 
			ct.setCreditorType("02");
	
		if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId()))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());
			
		ct.setCreDt(LocalDateTime.now());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());

		// tentukan debtorType: Personal atau bukan
		if (creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().size()>0) {	
			if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr()))
				ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
		}
		else if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId())) 
				ct.setDebtorType("01");
		else 
			ct.setDebtorType("02");

		if (!(null==creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId()))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());
		
//		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(inboundMsg.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		
		ct.setLogMessageId(logTable.getId());
		
		ct.setReversal(reversal);
		
		creditTrnRepo.save(ct);
	}
	
}
