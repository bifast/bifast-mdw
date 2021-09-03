package bifast.outbound.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.model.AccountEnquiry;
import bifast.library.model.CreditTransfer;
import bifast.library.model.DomainCode;
import bifast.library.model.OutboundMessage;
import bifast.library.model.ProxyMessage;
import bifast.library.repository.AccountEnquiryRepository;
import bifast.library.repository.CreditTransferRepository;
import bifast.library.repository.DomainCodeRepository;
import bifast.library.repository.OutboundMessageRepository;
import bifast.library.repository.ProxyMessageRepository;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.proxyregistration.ChannelProxyResolutionReq;

@Component
public class SaveTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundRepo;
	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private ProxyMessageRepository proxyMessageRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;


	@Override
	public void process(Exchange exchange) throws Exception {

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
		
		String filename = exchange.getIn().getHeader("log_filename", String.class);
		String msgType = exchange.getIn().getHeader("rcv_msgType", String.class);
		Object channelRequest = exchange.getMessage().getHeader("rcv_channel");

		OutboundMessage outboundMessage = new OutboundMessage();

		outboundMessage.setBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
		outboundMessage.setMsgDefIdr(outRequest.getAppHdr().getMsgDefIdr());

		outboundMessage.setToFinId(outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		
		String trxType = outRequest.getAppHdr().getBizMsgIdr().substring(16, 19);
		Optional<DomainCode> trxName = domainCodeRepo.findByGrpAndKey("TRANSACTION.TYPE", trxType);
		if (trxName.isPresent())
			outboundMessage.setTransactionType(trxName.get().getValue());
		else 
			outboundMessage.setTransactionType(trxType);
			
		outboundMessage.setLogfileName(filename);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		String strChnlRequestTime = exchange.getMessage().getHeader("req_channelRequestTime", String.class);
		String strChnlResponseTime = exchange.getMessage().getHeader("req_channelResponseTime", String.class);
		LocalDateTime chnlRequestTime = LocalDateTime.parse(strChnlRequestTime, dtf);
		LocalDateTime ChnlResponseTime = LocalDateTime.parse(strChnlResponseTime, dtf);
		outboundMessage.setChannelRequestDT(chnlRequestTime);
		outboundMessage.setChannelResponseDT(ChnlResponseTime);

		String strCiHubRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
		String strCiHubResponseTime = exchange.getMessage().getHeader("req_cihubResponseTime", String.class);
		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
		outboundMessage.setCihubRequestDT(cihubRequestTime);
		outboundMessage.setCihubResponseDT(cihubResponseTime);
		
		
		String intrRefId = "";
		String channel = "";
		if (msgType.equals("acctenqr")) {
			intrRefId = ((ChannelAccountEnquiryReq)channelRequest).getIntrnRefId();
			channel = ((ChannelAccountEnquiryReq)channelRequest).getChannelName();
		}
		else if (msgType.equals("crdttrns")) {
			intrRefId = ((ChannelCreditTransferRequest)channelRequest).getIntrnRefId();
			channel = ((ChannelCreditTransferRequest)channelRequest).getChannel();
		}
		else if (msgType.equals("ficrdttrns")) {
			intrRefId = ((ChannelFICreditTransferReq)channelRequest).getIntrnRefId();
			channel = ((ChannelFICreditTransferReq)channelRequest).getChannel();
		}
		else if (msgType.equals("prxyrgst")) {
			intrRefId = ((ChannelProxyRegistrationReq)channelRequest).getIntrnRefId();
			channel = ((ChannelProxyRegistrationReq)channelRequest).getChannel();
		}
		else if (msgType.equals("prxyrslt")) {
			intrRefId = ((ChannelProxyResolutionReq)channelRequest).getIntrnRefId();
			channel = ((ChannelProxyResolutionReq)channelRequest).getChannel();
		}
		
		if (!(null==intrRefId))
			outboundMessage.setInternalReffId(intrRefId);

//		Optional<DomainCode> channelName = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channel);
//		if (channelName.isPresent()) 
//			outboundMessage.setChannel(channelName.get().getValue());
//		else
			outboundMessage.setChannel(channel);

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
		
		outboundRepo.save(outboundMessage);
		
		// save table tracing
		
		if (msgType.equals("acctenqr")) {
			saveAccountEnquiryMsg(outboundMessage, outRequest);
		} 
		else if (msgType.equals("crdttrns")) {
			saveCreditTransferMsg(outboundMessage, outRequest);
		}
		else if (msgType.equals("ficrdttrns")) {
			saveFICreditTransferMsg(outboundMessage, outRequest);
		}
		else if (msgType.equals("prxyrgst")) {
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
		accountEnquiry.setCreDt(LocalDateTime.now());
		accountEnquiry.setIntrRefId(auditTab.getInternalReffId());
		accountEnquiry.setLogMessageId(auditTab.getId());
		accountEnquiry.setOriginatingBank(orgnlBank);
		accountEnquiry.setRecipientBank(auditTab.getToFinId());
		
		accountEnqrRepo.save(accountEnquiry);
		
	} 

	private void saveCreditTransferMsg (OutboundMessage auditTab,
										BusinessMessage outRequest) 
	{
		FIToFICustomerCreditTransferV08 creditTransferReq = outRequest.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();

		CreditTransfer ct = new CreditTransfer();

		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(auditTab.getBizMsgIdr());
		ct.setStatus(auditTab.getRespStatus());
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());

		ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		
		if (ct.getCreditorType().equals("01"))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());
			
		ct.setCreDt(LocalDateTime.now());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());

		if (ct.getDebtorType().endsWith("01"))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(auditTab.getToFinId());
		ct.setLogMessageId(auditTab.getId());
		
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
		ct.setCreDt(LocalDateTime.now());
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("FI Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(auditTab.getToFinId());
		ct.setLogMessageId(auditTab.getId());
		
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
