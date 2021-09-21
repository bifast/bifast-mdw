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
import bifast.outbound.model.BankCode;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.DomainCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.model.ProxyMessage;
import bifast.outbound.repository.AccountEnquiryRepository;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;
import bifast.outbound.repository.ProxyMessageRepository;

@Component
public class SaveOutboundTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private ProxyMessageRepository proxyMessageRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

//		OutboundMessage outboundMessage = new OutboundMessage();
//
//		String chnlRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
//		outboundMessage.setInternalReffId(chnlRefId);
//
//		outboundMessage.setBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
//		
//		Object channelRequest = exchange.getMessage().getHeader("hdr_channelRequest");
//		String requestClassName = channelRequest.getClass().getSimpleName();
//		String msgName = domainCodeRepo.findByGrpAndKey("REQUEST.CLASS", requestClassName).orElse(new DomainCode()).getValue();
//		outboundMessage.setMessageName(msgName);
//		
//		String rcptBank = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
//		outboundMessage.setRecipientBank( rcptBank );
//
//		String strCiHubRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
//		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
//		outboundMessage.setCihubRequestDT(cihubRequestTime);
//
//		String strCiHubResponseTime = exchange.getMessage().getHeader("req_cihubResponseTime", String.class);
//		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
//		outboundMessage.setCihubResponseDT(cihubResponseTime);
//		
//		String encrRequestMsg = exchange.getMessage().getHeader("ae_encrypt_request", String.class);
//		String encrResponseMsg = exchange.getMessage().getHeader("ae_encrypt_response", String.class);
//		outboundMessage.setFullRequestMessage(encrRequestMsg);
//		
//		if (!(null== encrResponseMsg))
//			outboundMessage.setFullResponseMsg(encrResponseMsg);
//
//		BusinessMessage outResponse = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);
//
//		if (null == outResponse) {  // tidak terima response dari BI berarti timeout
//			outboundMessage.setRespStatus("TIMEOUT");
//			outboundMessage.setErrorMessage("Timeout terima message dari CI-HUB");
//		}
//
//		else if (!(null == outResponse.getDocument().getMessageReject())) {  // reject message
//		
//			String rjctMesg = outResponse.getDocument().getMessageReject().getRsn().getRsnDesc();
//			if (rjctMesg.length() > 400)
//				rjctMesg = rjctMesg.substring(0, 400);
//			
//			outboundMessage.setRespStatus("ERROR-CIHUB");
//			outboundMessage.setErrorMessage(rjctMesg);
//		}
//		
//		else {  // response ct pacs002
//			outboundMessage.setRespBizMsgId(outResponse.getAppHdr().getBizMsgIdr());
//			outboundMessage.setRespStatus(outResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());			
//		}			
//		
//		
//		outboundMsgRepo.save(outboundMessage);
//
//		// save table tracing
////		Object channelRequest = exchange.getMessage().getHeader("hdr_channelRequest");
////		String requestClassName = channelRequest.getClass().getSimpleName();
//		
//		if (requestClassName.equals("ChnlAccountEnquiryRequestPojo"))
//			
//			
//			saveAccountEnquiryMsg(outboundMessage, outRequest);
//			
//		
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

	
}
