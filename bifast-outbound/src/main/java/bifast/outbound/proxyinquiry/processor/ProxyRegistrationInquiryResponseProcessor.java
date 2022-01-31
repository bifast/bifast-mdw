package bifast.outbound.proxyinquiry.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPrxy006AliasPojo;
import bifast.outbound.pojo.flat.FlatPrxy006Pojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationInquiryRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationInquiryResponsePojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class ProxyRegistrationInquiryResponseProcessor implements Processor {

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired private StatusReasonRepository statusReasonRepo;
    @Autowired private Config config;

	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
		channelResponseWr.setResponseCode("U000");
		channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		channelResponseWr.setResponses(new ArrayList<>());

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		ChnlProxyRegistrationInquiryRequestPojo chnRequest = rmw.getChnlProxyRegistrationInquiryRequest();

		Object objBody = exchange.getMessage().getBody(Object.class);
		if (objBody.getClass().getSimpleName().equals("FaultPojo")) {

			FaultPojo fault = (FaultPojo)objBody;

			channelResponseWr.setResponseCode(fault.getResponseCode());
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(fault.getReasonCode());
			if (oStatusReason.isPresent())
				channelResponseWr.setResponseMessage(oStatusReason.get().getDescription());
			else
				channelResponseWr.setResponseMessage("General Error");

			ChnlProxyRegistrationInquiryResponsePojo chnResponse = new ChnlProxyRegistrationInquiryResponsePojo();
			chnResponse.setNoRef(chnRequest.getChannelRefId());
			channelResponseWr.getResponses().add(chnResponse);

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
			
			FlatPrxy006Pojo resp = (FlatPrxy006Pojo)objBody;
			
			if (resp.getResponseCode().equals("ACTC")) {

				List<FlatPrxy006AliasPojo> aliasListFiltered = new ArrayList<>();
				// filter hanya proxy yng terdaftar di bank asal yg ditampilkan
				if(resp.getAlias() !=  null) {	
					for(FlatPrxy006AliasPojo data:resp.getAlias()) {
						if (data.getRegisterBank().equals(config.getBankcode())) 
							aliasListFiltered.add(data);
					}
				}
				
				if (aliasListFiltered.size() == 0) {
					channelResponseWr.setResponseCode("RJCT");
					channelResponseWr.setReasonCode("U804");
					channelResponseWr.setReasonMessage("Alias Not Found Or Inactive");
					ChnlProxyRegistrationInquiryResponsePojo chnResponse = new ChnlProxyRegistrationInquiryResponsePojo();
					chnResponse.setNoRef(chnRequest.getChannelRefId());
					channelResponseWr.getResponses().add(chnResponse);

				}
				
				else {
					channelResponseWr.setResponseCode(resp.getResponseCode());
					channelResponseWr.setReasonCode(resp.getReasonCode());
					Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
					if (optStatusReason.isPresent()) {
						String desc = optStatusReason.get().getDescription();
						channelResponseWr.setReasonMessage(desc);
					}	
					
					for (FlatPrxy006AliasPojo data : aliasListFiltered) {
						ChnlProxyRegistrationInquiryResponsePojo chnResponse = new ChnlProxyRegistrationInquiryResponsePojo();
						chnResponse.setNoRef(chnRequest.getChannelRefId());
		
						chnResponse.setProxyType(data.getProxyType());
						chnResponse.setProxyValue(data.getProxyValue());
						chnResponse.setRegistrationId(data.getRegistrationId());
						chnResponse.setDisplayName(data.getDisplayName());
						chnResponse.setRegisterBank(data.getRegisterBank());
						chnResponse.setAccountType(data.getAccountType());
						chnResponse.setAccountNumber(data.getAccountNumber());
						chnResponse.setProxyStatus(data.getProxySatus());
							
						if (null !=data.getAccountName())
							chnResponse.setAccountName(data.getAccountName());
		
						if (null != data.getCustomerType())
							chnResponse.setCustomerType(data.getCustomerType());
										
						if (null != data.getResidentialStatus());
							chnResponse.setResidentStatus(data.getResidentialStatus());
		
						if (null != data.getTownName());
							chnResponse.setTownName(data.getTownName());
								
						chnResponse.setScndIdType(chnRequest.getScndIdType());
						chnResponse.setScndIdValue(chnRequest.getScndIdValue());
							
						channelResponseWr.getResponses().add(chnResponse);
					}
				}
			}
			
			else {
				channelResponseWr.setResponseCode(resp.getResponseCode());
				channelResponseWr.setReasonCode(resp.getReasonCode());
				Optional<StatusReason> optStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
				if (optStatusReason.isPresent()) {
					String desc = optStatusReason.get().getDescription();
					channelResponseWr.setReasonMessage(desc);
				}	
				ChnlProxyRegistrationInquiryResponsePojo chnResponse = new ChnlProxyRegistrationInquiryResponsePojo();
				chnResponse.setNoRef(chnRequest.getChannelRefId());
				channelResponseWr.getResponses().add(chnResponse);
			}
		}
		
		exchange.getIn().setBody(channelResponseWr);
	}
}
