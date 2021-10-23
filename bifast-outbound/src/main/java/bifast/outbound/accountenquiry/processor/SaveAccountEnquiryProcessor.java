package bifast.outbound.accountenquiry.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.model.AccountEnquiry;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.AccountEnquiryRepository;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		AccountEnquiry ae = new AccountEnquiry();

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		BusinessMessage outRequest = rmw.getAccountEnquiryRequest();
		
		ae.setKomiTrnsId(rmw.getKomiTrxId());
		ae.setChnlRefId(rmw.getRequestId());
		
		ae.setReqBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());

		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		String recptBank = outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		ae.setOriginatingBank(orgnlBank);
		ae.setRecipientBank(recptBank);

		FIToFICustomerCreditTransferV08 accountEnqReq = outRequest.getDocument().getFiToFICstmrCdtTrf();

		ae.setAccountNo(accountEnqReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ae.setAmount(accountEnqReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		ae.setFullRequestMessage(rmw.getCihubEncriptedRequest());

		ae.setSubmitDt(LocalDateTime.now());
		long timeElapsed = Duration.between(rmw.getCihubStart(), Instant.now()).toMillis();
		ae.setElapsedTime(timeElapsed);

		Object oBiResponse = exchange.getMessage().getBody(Object.class);

		if (oBiResponse.getClass().getSimpleName().equals("ChnlFailureResponsePojo")) {
			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)oBiResponse;
			ae.setResponseCode(fault.getResponseCode());
			ae.setErrorMessage(fault.getDescription());
			ae.setReasonCode(fault.getReasonCode());
			ae.setCallStatus(fault.getFaultCategory());
		}
			
		else if (oBiResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {
			ae.setCallStatus("REJECT");
			ae.setResponseCode("RJCT");
			ae.setReasonCode("U215");
			ae.setErrorMessage("Message Rejected with Admi.002");
		}
		
		else {
			ae.setCallStatus("SUCCESS");

			FlatPacs002Pojo aeResponse = (FlatPacs002Pojo) oBiResponse;
			ae.setRespBizMsgIdr(aeResponse.getBizMsgIdr());
		
			ae.setResponseCode(aeResponse.getTransactionStatus());
			ae.setReasonCode(aeResponse.getReasonCode());

			if (!(null==rmw.getCihubEncriptedResponse()))
				ae.setFullResponseMsg(rmw.getCihubEncriptedResponse());

		}
	
		accountEnqrRepo.save(ae);
		
	} 

	
}
