package bifast.outbound.accountenquiry.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.DomainCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.repository.AccountEnquiryRepository;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelResponseWrapper channelResponse = exchange.getMessage().getBody(ChannelResponseWrapper.class);
		BusinessMessage outRequest = exchange.getMessage().getHeader("ae_objreq_bi", BusinessMessage.class);
		BusinessMessage outResponse = exchange.getMessage().getHeader("ae_objresp_bi", BusinessMessage.class);
		
		
		ChnlAccountEnquiryRequestPojo chnlRequest = exchange.getMessage().getHeader("ae_channelRequest", ChnlAccountEnquiryRequestPojo.class);				
		String encrRequestMsg = exchange.getMessage().getHeader("ae_encrypt_request", String.class);
		String encrResponseMsg = exchange.getMessage().getHeader("ae_encrypt_response", String.class);

		FIToFICustomerCreditTransferV08 accountEnqReq = outRequest.getDocument().getFiToFICstmrCdtTrf();

		AccountEnquiry ae = new AccountEnquiry();
		
		ae.setAccountNo(accountEnqReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ae.setAmount(accountEnqReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		ae.setBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
		
		if (!(null==outResponse)) {
			ae.setRespBizMsgIdr(outResponse.getAppHdr().getBizMsgIdr());
			ae.setResponseStatus(outResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		}

		String strCiHubRequestTime = exchange.getMessage().getHeader("ae_cihubRequestTime", String.class);
		String strCiHubResponseTime = exchange.getMessage().getHeader("ae_cihubResponseTime", String.class);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		LocalDateTime ciHubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
		LocalDateTime ciHubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);

		ae.setCihubRequestDT(ciHubRequestTime);
		ae.setCihubResponseDT(ciHubResponseTime);

		ae.setCreDt(accountEnqReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		ae.setFullRequestMessage(encrRequestMsg);
		ae.setFullResponseMsg(encrResponseMsg);
		ae.setIntrRefId(chnlRequest.getChannelRefId());
		
		
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		String recptBank = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		ae.setOriginatingBank(orgnlBank);
		ae.setRecipientBank(recptBank);
		
		
		String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
    	String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);

		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		if (!(null==e)) {

			ae.setCallStatus(errorStatus);
			if (!(null==e.getCause()))
				ae.setErrorMessage(e.getCause().getMessage());
			else if (!(null==errorMesg))
				ae.setErrorMessage(errorMesg);
			
		}
		
		else if (!(null==errorStatus)) {
			ae.setCallStatus(errorStatus);
//			ae.setErrorMessage();
		}
		
		accountEnqrRepo.save(ae);
		
	} 

	
}
