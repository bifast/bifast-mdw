package bifast.outbound.proxyregistration.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyResolutionRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlProxyResolutionResponsePojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ProxyResolutionResponseProcessor implements Processor {

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private StatusReasonRepository statusReasonRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setResponses(new ArrayList<>());

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlProxyResolutionRequestPojo chnRequest = rmw.getChnlProxyResolutionRequest();

		ChnlProxyResolutionResponsePojo chnResponse = new ChnlProxyResolutionResponsePojo();
		chnResponse.setNoRef(chnRequest.getChannelRefId());

		Object objBody = exchange.getMessage().getBody(Object.class);
		if (objBody.getClass().getSimpleName().equals("ChnlFailureResponsePojo")) {

			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objBody;

			channelResponseWr.setResponseCode(fault.getResponseCode());
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(fault.getReasonCode());
			if (oStatusReason.isPresent())
				channelResponseWr.setResponseMessage(oStatusReason.get().getDescription());
			else
				channelResponseWr.setResponseMessage("General Error");

		}
		
		else if (objBody.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {

			channelResponseWr.setResponseCode("RJCT");
			channelResponseWr.setReasonCode("U215");
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById("U215");
			if (oStatusReason.isPresent())
				channelResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				channelResponseWr.setResponseMessage("General Error");

		}

		else { 
			FlatPrxy004Pojo resp = (FlatPrxy004Pojo)objBody;
			
			channelResponseWr.setResponseCode(resp.getResponseCode());
			channelResponseWr.setReasonCode(resp.getReasonCode());
			
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				channelResponseWr.setReasonMessage(desc);
			}	

			chnResponse.setNoRef(chnRequest.getChannelRefId());
			chnResponse.setProxyType(resp.getProxyType());
			chnResponse.setProxyValue(resp.getProxyValue());
			chnResponse.setRegistrationId(resp.getRegistrationId());
			chnResponse.setDisplayName(resp.getDisplayName());
			chnResponse.setRegisterBank(resp.getRegisterBank());
			chnResponse.setAccountNumber(resp.getAccountNumber());
			chnResponse.setAccountType(resp.getAccountType());
			
			if (null !=resp.getAccountName())
				chnResponse.setAccountName(resp.getAccountName());

			if (null != resp.getCustomerType())
				chnResponse.setCustomerType(resp.getCustomerType());
			
			if (null != resp.getCustomerId())
				chnResponse.setCustomerId(resp.getCustomerId());
			
			if (null != resp.getResidentialStatus());
				chnResponse.setResidentStatus(resp.getResidentialStatus());

			if (null != resp.getTownName());
				chnResponse.setTownName(resp.getTownName());
		}

		channelResponseWr.getResponses().add(chnResponse);
		exchange.getIn().setBody(channelResponseWr);
	
	}

}
