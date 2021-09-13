package bifast.inbound.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.model.DomainCode;
import bifast.inbound.model.InboundMessage;
import bifast.inbound.model.Settlement;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.repository.DomainCodeRepository;
import bifast.inbound.repository.InboundMessageRepository;
import bifast.inbound.repository.SettlementRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;

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
		 
		BusinessMessage rcvBi = exchange.getMessage().getHeader("hdr_frBIobj",BusinessMessage.class);
		BusinessApplicationHeaderV01 hdr = rcvBi.getAppHdr();

		InboundMessage inboundMsg = new InboundMessage();
		
		inboundMsg.setBizMsgIdr(hdr.getBizMsgIdr());
		inboundMsg.setFrFinId(hdr.getFr().getFIId().getFinInstnId().getOthr().getId());
		
		String fullReqMsg = exchange.getMessage().getHeader("hdr_frBI_jsonzip",String.class);
		String fullRespMsg = exchange.getMessage().getHeader("hdr_toBI_jsonzip",String.class);
		inboundMsg.setFullRequestMessage(fullReqMsg);
		inboundMsg.setFullResponseMsg(fullRespMsg);
		
		if (!(null==hdr.getCpyDplct()))
				inboundMsg.setCopyDupl(hdr.getCpyDplct().name());
		

		if (!(null == exchange.getMessage().getHeader("hdr_toBIobj"))) {
			BusinessMessage respBi = exchange.getMessage().getHeader("hdr_toBIobj",BusinessMessage.class);

			inboundMsg.setRespStatus(respBi.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			inboundMsg.setRespBizMsgIdr(respBi.getAppHdr().getBizMsgIdr());
			
//			inboundMsg.setRespRejectMsg(null);
		}
//		inboundMsg.setRespErrorMsg(null);
		
		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);
		String msgName = "";
		System.out.println("msgName: " + msgType);
		if (msgType.equals("SETTLEMENT")) 
			msgName = "Settlement Confirmation";
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

		else if (msgType.equals("010")) {  // Credit Transfer
			String reversal = exchange.getMessage().getHeader("resp_reversal",String.class);

			BusinessMessage respBi = exchange.getMessage().getHeader("hdr_toBIobj", BusinessMessage.class);
			saveCreditTransfer(inboundMsg, rcvBi, reversal);
		}

		else if (msgType.equals("019")) {   // FI Credit Tranfsre
			BusinessMessage respBi = exchange.getMessage().getHeader("hdr_toBIobj", BusinessMessage.class);
//			saveFICreditTransfer(rcvMessage, respBi);	
		}

		else if (msgType.equals("011")) {  // Reversal Credit Transfer
			BusinessMessage respBi = exchange.getMessage().getHeader("hdr_toBIobj", BusinessMessage.class);
//			saveReversalCreditTransfer(rcvMessage, respBi);	
		}


	}

	public void saveSettlement (BusinessMessage settlRequest, InboundMessage inbMsg) {
		BusinessApplicationHeaderV01 sttlHeader = settlRequest.getAppHdr();
		FIToFIPaymentStatusReportV10 settlBody = settlRequest.getDocument().getFiToFIPmtStsRpt();
		
		String sttlBizMsgId = sttlHeader.getBizMsgIdr();
		
	
		Settlement sttl = new Settlement();
		sttl.setOrgnlCrdtTrnReqBizMsgId(settlBody.getTxInfAndSts().get(0).getOrgnlEndToEndId());
		sttl.setSettlConfBizMsgId(sttlBizMsgId);
		sttl.setOrignBank(sttlHeader.getFr().getFIId().getFinInstnId().getOthr().getId());
		sttl.setRecptBank(sttlHeader.getTo().getFIId().getFinInstnId().getOthr().getId());
		
		sttl.setLogMessageId(inbMsg.getId());
		sttl.setCrdtAccountNo(settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
//		sttl.setCrdtAccountType(orglBizMsgId);
//		sttl.setCrdtId(orglBizMsgId);
//		sttl.setCrdtIdType(orglBizMsgId);
//		sttl.setCrdtName(orglBizMsgId);
		sttl.setDbtrAccountNo(settlBody.getTxInfAndSts().get(0).getOrgnlTxRef().getDbtrAcct().getId().getOthr().getId());
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
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());
		
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
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		
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
