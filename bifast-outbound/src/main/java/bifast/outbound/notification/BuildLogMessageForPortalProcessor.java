package bifast.outbound.notification;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.config.Config;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.notification.pojo.LogDataPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

@Component
public class BuildLogMessageForPortalProcessor implements Processor {
	@Autowired private Config config;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);
		ResponseMessageCollection respColl = exchange.getMessage().getHeader("hdr_response_list",ResponseMessageCollection.class );

		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		FaultPojo fault = respColl.getFault();
		if (null == fault)
			data.setStatus_code("SUCCESS");
		else if (fault.getCallStatus().equals("REJECT-CB"))
			data.setStatus_code("SUCCESS");
		else {
			data.setStatus_code(fault.getCallStatus());
			data.setError_msg(fault.getErrorMessage());
		}		
		
		data.setResponse_code(responseWr.getResponseCode());
		data.setReason_code(responseWr.getReasonCode());
		data.setReason_message(responseWr.getReasonMessage());

		data.setKomi_trx_no(rmw.getKomiTrxId());
		data.setKomi_unique_id(rmw.getRequestId());
		data.setChannel_type(rmw.getChannelType());
		data.setSender_bank(config.getBankcode());
		data.setTrx_type("O");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
		data.setTrx_initiation_date(formatter.format(rmw.getKomiStart()));

		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
		data.setTrx_duration(String.valueOf(timeElapsed));

		if (rmw.getMsgName().equals("CTReq")) {
			logMsg.setCodelog("CT");
			ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
			
			data.setRecipient_bank(chnReq.getRecptBank());		
		
			if (null != rmw.getCreditTransferRequest())
				data.setBifast_trx_no(rmw.getCreditTransferRequest().getAppHdr().getBizMsgIdr());
			
			data.setProxy_alias(chnReq.getCrdtProxyIdValue());
			data.setProxy_type(chnReq.getCrdtProxyIdType());
			data.setCharge_type("D");
			data.setTrx_amount(chnReq.getAmount());
			data.setRecipient_account_no(chnReq.getCrdtAccountNo());
			data.setRecipient_account_name(chnReq.getCrdtName());

			data.setSender_account_name(chnReq.getDbtrName());
			data.setSender_account_no(chnReq.getDbtrAccountNo());

			if ((null == chnReq.getCrdtProxyIdValue() ||
				(chnReq.getCrdtProxyIdValue().isBlank()))) {
				data.setProxyFlag("T");
			}
			else
				data.setProxyFlag("Y");
		}
		
		else if (rmw.getMsgName().equals("AEReq")) {
			logMsg.setCodelog("AE");
			ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
			
			if ((null != aeReq.getCreditorAccountNumber()) || !(aeReq.getCreditorAccountNumber().isBlank())) 
				data.setBifast_trx_no(rmw.getAccountEnquiryRequest().getAppHdr().getBizMsgIdr());
			else
				data.setBifast_trx_no(rmw.getProxyResolutionRequest().getAppHdr().getBizMsgIdr());
			
			data.setProxy_alias(aeReq.getProxyId());
			data.setProxy_type(aeReq.getProxyType());
			if (null != aeReq.getProxyId())
				data.setProxyFlag("Y");
			else
				data.setProxyFlag("T");
			data.setRecipient_bank(aeReq.getRecptBank());
			data.setRecipient_account_no(aeReq.getCreditorAccountNumber());
			data.setSender_account_no(aeReq.getSenderAccountNumber());
			data.setTrx_amount(aeReq.getAmount());
			
		}
		
		else if (rmw.getMsgName().equals("PrxRegn")) {
			logMsg.setCodelog("PR");
			ChnlProxyRegistrationRequestPojo regnReq = rmw.getChnlProxyRegistrationRequest();

			data.setProxy_regn_opr(regnReq.getRegistrationType());

			if (null != rmw.getProxyRegistrationRequest())
				data.setBifast_trx_no(rmw.getProxyRegistrationRequest().getAppHdr().getBizMsgIdr());
			else
				data.setBifast_trx_no(rmw.getProxyResolutionRequest().getAppHdr().getBizMsgIdr());

			data.setProxy_alias(regnReq.getProxyValue());
			data.setProxy_type(regnReq.getProxyType());
			
			if (null != regnReq.getAccountName())
				data.setSender_account_name(regnReq.getAccountName());

			if (null != regnReq.getAccountName())
				data.setSender_account_no(regnReq.getAccountNumber());
			
			if (null != regnReq.getSecondIdType())
				data.setScnd_id_type(regnReq.getSecondIdType());

			if (null != regnReq.getSecondIdValue())
				data.setScnd_id_value(regnReq.getSecondIdValue());
		}

		
		else if (rmw.getMsgName().equals("PaymentStsSAF")) {
			logMsg.setCodelog("PS");
			ChnlCreditTransferRequestPojo chn = rmw.getChnlCreditTransferRequest();
			
			data.setKomi_unique_id(chn.getChannelRefId());
			data.setBifast_trx_no(rmw.getCreditTransferRequest().getAppHdr().getBizMsgIdr());
		}
		
		logMsg.setData(data);
		exchange.getMessage().setBody(logMsg);
		
	}

}
