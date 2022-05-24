package bifast.outbound.accountenquiry.processor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.repository.AccountEnquiryRepository;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		AccountEnquiry ae = new AccountEnquiry();
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		
//		ChnlAccountEnquiryRequestPojo chnlRequest = rmw.getChnlAccountEnquiryRequest();
		ChnlAccountEnquiryRequestPojo chnlRequest = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();

		ae.setKomiTrnsId(rmw.getKomiTrxId());
		ae.setChnlRefId(rmw.getRequestId());
		ae.setAccountNo(chnlRequest.getCreditorAccountNumber());
		ae.setAmount(new BigDecimal(chnlRequest.getAmount()));

		ae.setSubmitDt(LocalDateTime.now());
		long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
		ae.setElapsedTime(timeElapsed);

		ae.setFullRequestMessage(rmw.getCihubEncriptedRequest());

		Object oBody = exchange.getMessage().getBody(Object.class);
		
		BusinessMessage outRequest = null;
		
		if (oBody.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo)oBody;
			ae.setCallStatus(fault.getCallStatus());
			ae.setResponseCode(fault.getResponseCode());
			ae.setErrorMessage(fault.getErrorMessage());
			ae.setReasonCode(fault.getReasonCode());

		}

		else if (oBody.getClass().getSimpleName().equals("FlatPrxy004Pojo")) {
			outRequest = rmw.getProxyResolutionRequest();
			
			ae.setCallStatus("SUCCESS");

			ae.setReqBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());

			String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
			String recptBank = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
			ae.setOriginatingBank(orgnlBank);
			ae.setRecipientBank(recptBank);
						
			FlatPrxy004Pojo response = (FlatPrxy004Pojo) oBody;
			ae.setRespBizMsgIdr(response.getBizMsgIdr());
			ae.setResponseCode(response.getResponseCode());
			ae.setReasonCode(response.getReasonCode());
			ae.setProxyId(chnlRequest.getProxyId());
			
			if (!(null==rmw.getCihubEncriptedResponse()))
				ae.setFullResponseMsg(rmw.getCihubEncriptedResponse());
		}
		
		else {
			ae.setCallStatus("SUCCESS");

			outRequest = rmw.getAccountEnquiryRequest();			
			ae.setReqBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
	
			String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
			String recptBank = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
			ae.setOriginatingBank(orgnlBank);
			ae.setRecipientBank(recptBank);

			FlatPacs002Pojo aeResponse = (FlatPacs002Pojo) oBody;
			ae.setRespBizMsgIdr(aeResponse.getBizMsgIdr());
		
			ae.setResponseCode(aeResponse.getTransactionStatus());
			ae.setReasonCode(aeResponse.getReasonCode());

			if (!(null==rmw.getCihubEncriptedResponse()))
				ae.setFullResponseMsg(rmw.getCihubEncriptedResponse());

		}
	
		accountEnqrRepo.save(ae);
		
	} 

	
}
