package bifast.outbound.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.model.ProxyMessage;
import bifast.outbound.repository.AccountEnquiryRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.OutboundMessageRepository;
import bifast.outbound.repository.ProxyMessageRepository;

@Component
public class SaveTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private ProxyMessageRepository proxyMessageRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
		Long tableId = exchange.getMessage().getHeader("hdr_idtable", Long.class); 
		
		OutboundMessage outboundMessage = outboundMsgRepo.findById(tableId).orElse(new OutboundMessage());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		String strChnlResponseTime = exchange.getMessage().getHeader("req_channelResponseTime", String.class);
		LocalDateTime ChnlResponseTime = LocalDateTime.parse(strChnlResponseTime, dtf);
		outboundMessage.setChannelResponseDT(ChnlResponseTime);

		String strCiHubRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
		outboundMessage.setCihubRequestDT(cihubRequestTime);
		String strCiHubResponseTime = exchange.getMessage().getHeader("req_cihubResponseTime", String.class);
		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
		outboundMessage.setCihubResponseDT(cihubResponseTime);
		
		String fullResponseMsg = exchange.getMessage().getHeader("hdr_fullresponsemessage", String.class);
		outboundMessage.setFullResponseMsg(fullResponseMsg);

		BusinessMessage outResponse = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);

		if (null == outResponse) {  // tidak terima response dari BI berarti timeout
			outboundMessage.setRespStatus("TIMEOUT");
			outboundMessage.setErrorMessage("Timeout terima message dari CI-HUB");
		}
		
		else if (!(null == outResponse.getDocument().getFiToFIPmtStsRpt())) {  // response ct pacs002
			outboundMessage.setRespBizMsgId(outResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(outResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			
		}			

		else if (!(null == outResponse.getDocument().getPrxyRegnRspn())) {    // Response dari Proxy Registration
			outboundMessage.setRespBizMsgId(outResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(outResponse.getDocument().getPrxyRegnRspn().getRegnRspn().getPrxRspnSts().name());
		}
		
		else if (!(null == outResponse.getDocument().getPrxyLookUpRspn())) {    // Response dari Proxy Resolution
			outboundMessage.setRespBizMsgId(outResponse.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(outResponse.getDocument().getPrxyLookUpRspn().getLkUpRspn().getRegnRspn().getPrxRspnSts().name());
		}
		
		else {  // msg Reject is not nusaveAccountEnquiryMsgl
					
			String rjctMesg = outResponse.getDocument().getMessageReject().getRsn().getRsnDesc();
			if (rjctMesg.length() > 400)
				rjctMesg = rjctMesg.substring(0, 400);
			
			outboundMessage.setRespStatus("FAILURE");
			outboundMessage.setErrorMessage(rjctMesg);
		}
		
		outboundMsgRepo.save(outboundMessage);

		// save table tracing
		Object channelRequest = exchange.getMessage().getHeader("hdr_channelRequest");
		String requestClass = channelRequest.getClass().getName();
		
		if (requestClass.endsWith("ChannelAccountEnquiryReq")) {
			saveAccountEnquiryMsg(outboundMessage, outRequest);
		} 
		else if (requestClass.endsWith("ChannelCreditTransferRequest")) {
			saveCreditTransferMsg(outboundMessage, outRequest);
		}
//		else if (msgType.equals("reversal")) 
//			saveReversalCreditTransferMsg(outboundMessage, outRequest, (String) channelRequest.get("crdttrn_req_bizmsgid"));
			
		else if (requestClass.endsWith("ChannelFICreditTransferReq")) {
			saveFICreditTransferMsg(outboundMessage, outRequest);
		}
		else if (requestClass.endsWith("ChannelProxyRegistrationReq")) {
			saveProxyMessage(outboundMessage, outRequest);
		}

		
	}

	private void saveAccountEnquiryMsg (OutboundMessage auditTab,
										BusinessMessage outRequest) 
	{
		FIToFICustomerCreditTransferV08 accountEnqReq = outRequest.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		AccountEnquiry accountEnquiry = new AccountEnquiry();
		
		accountEnquiry.setAccountNo(accountEnqReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		accountEnquiry.setAmount(accountEnqReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		// dari XMLGregorianCalender ubah ke LocalDateTime
		accountEnquiry.setCreDt(accountEnqReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		accountEnquiry.setIntrRefId(auditTab.getInternalReffId());
//		accountEnquiry.setLogMessageId(auditTab.getId());
		accountEnquiry.setOriginatingBank(orgnlBank);
		accountEnquiry.setRecipientBank(auditTab.getRecipientBank());
		
		accountEnqrRepo.save(accountEnquiry);
		
	} 

	private CreditTransfer saveCreditTransferMsg (OutboundMessage auditTab,
										BusinessMessage outRequest) 
	{
		FIToFICustomerCreditTransferV08 creditTransferReq = outRequest.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();

		CreditTransfer ct = new CreditTransfer();

		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(auditTab.getBizMsgIdr());
		ct.setResponseStatus(auditTab.getRespStatus());
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());

		ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		
		if (ct.getCreditorType().equals("01"))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());
			
		// dari XMLGregorianCalender ubah ke LocalDateTime
		ct.setCreDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());

		if (ct.getDebtorType().endsWith("01"))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(auditTab.getRecipientBank());
//		ct.setLogMessageId(auditTab.getId());
		
		creditTransferRepo.save(ct);
		
		return ct;
	}

	private void saveReversalCreditTransferMsg (OutboundMessage auditTab,
				BusinessMessage outRequest,
				String endToEndId) 
	{
		CreditTransfer ct = saveCreditTransferMsg(auditTab, outRequest);
		ct.setMsgType("Reversal CT");
		ct.setReversal(endToEndId);
		creditTransferRepo.save(ct);
	}

	private void saveFICreditTransferMsg (OutboundMessage auditTab,
										  BusinessMessage outRequest) 
	{
		FinancialInstitutionCreditTransferV09 fiCreditTransferReq = outRequest.getDocument().getFiCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();

		CreditTransfer ct = new CreditTransfer();

		ct.setCrdtTrnRequestBizMsgIdr(auditTab.getBizMsgIdr());

		ct.setAmount(fiCreditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCreDt(fiCreditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		ct.setCallStatus(auditTab.getRespStatus());
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("FI to FI Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(auditTab.getRecipientBank());
//		ct.setLogMessageId(auditTab.getId());
		
		creditTransferRepo.save(ct);
	}

	public void saveProxyMessage (OutboundMessage auditTab, BusinessMessage reqBi) {
		ProxyMessage proxyMsg = new ProxyMessage(); 
		
		ProxyRegistrationV01 proxyData = reqBi.getDocument().getPrxyRegn();
		
		proxyMsg.setLogMessageId(auditTab.getId());
		proxyMsg.setOperationType(proxyData.getRegn().getRegnTp().value());
		proxyMsg.setAccountName(proxyData.getRegn().getPrxyRegn().getAcct().getNm());
		proxyMsg.setAccountNumber(proxyData.getRegn().getPrxyRegn().getAcct().getId().getOthr().getId());
		proxyMsg.setAccountType(proxyData.getRegn().getPrxyRegn().getAcct().getTp().getPrtry());
		proxyMsg.setCustomerId(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
		proxyMsg.setCustomerType(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
		
		proxyMsg.setDisplayName(proxyData.getRegn().getPrxyRegn().getDsplNm());
		proxyMsg.setProxyType(proxyData.getRegn().getPrxy().getTp());
		proxyMsg.setProxyValue(proxyData.getRegn().getPrxy().getVal());
		proxyMsg.setResidentStatus(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());
		proxyMsg.setScndIdType(proxyData.getRegn().getPrxyRegn().getScndId().getTp());
		proxyMsg.setScndValue(proxyData.getRegn().getPrxyRegn().getScndId().getVal());
		proxyMsg.setTownName(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());

		proxyMessageRepo.save(proxyMsg);
	}
	
}
