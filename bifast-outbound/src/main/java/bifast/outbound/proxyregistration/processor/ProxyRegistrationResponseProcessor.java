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
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPrxy002Pojo;
import bifast.outbound.pojo.flat.FlatPrxy004Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationResponsePojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ProxyRegistrationResponseProcessor implements Processor {
	
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private StatusReasonRepository statusReasonRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelResponseWrapper chnlResponseWr = new ChannelResponseWrapper();
		chnlResponseWr.setResponseCode("U000");
		chnlResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		chnlResponseWr.setTime(LocalDateTime.now().format(timeformatter));
				
		chnlResponseWr.setResponses(new ArrayList<>());

		Object objResponse = exchange.getMessage().getBody(Object.class);

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class);
		ChnlProxyRegistrationRequestPojo chnRequest = rmw.getChnlProxyRegistrationRequest();

		ChnlProxyRegistrationResponsePojo chnResponse = new ChnlProxyRegistrationResponsePojo();
		chnResponse.setNoRef(chnRequest.getChannelRefId());
		chnResponse.setRegistrationType(chnRequest.getRegistrationType());

		if (objResponse.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo)objResponse;
			
			chnlResponseWr.setResponseCode(fault.getResponseCode());
			chnlResponseWr.setReasonCode(fault.getReasonCode());

			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(fault.getReasonCode());
			if (oStatusReason.isPresent())
				chnlResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				chnlResponseWr.setResponseMessage("General Error");
		}
		
		else if (objResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {

			chnlResponseWr.setResponseCode("RJCT");
			chnlResponseWr.setReasonCode("U215");
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById("U215");
			if (oStatusReason.isPresent())
				chnlResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				chnlResponseWr.setResponseMessage("General Error");

		}
		
		else if (objResponse.getClass().getSimpleName().equals("FlatPrxy004Pojo")) {
			FlatPrxy004Pojo resp = (FlatPrxy004Pojo) objResponse;
			chnlResponseWr.setResponseCode(resp.getResponseCode());
			chnlResponseWr.setReasonCode(resp.getReasonCode());
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(resp.getReasonCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				chnlResponseWr.setReasonMessage(desc);;
			}	

		}

		else {
			FlatPrxy002Pojo biResp = (FlatPrxy002Pojo)objResponse;

			chnlResponseWr.setResponseCode(biResp.getResponseCode());
			chnlResponseWr.setReasonCode(biResp.getStatusReason());

			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(biResp.getStatusReason());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				chnlResponseWr.setReasonMessage(desc);;
			}	

			if (null != biResp.getRegistrationId()) 
					chnResponse.setRegistrationId(biResp.getRegistrationId());
			
		}
		
		chnlResponseWr.getResponses().add(chnResponse);

		exchange.getMessage().setBody(chnlResponseWr);

	}
}
