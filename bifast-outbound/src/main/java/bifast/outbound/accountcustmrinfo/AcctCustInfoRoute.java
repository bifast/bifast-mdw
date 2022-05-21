package bifast.outbound.accountcustmrinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.AccountCustInfoResponseDTO;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
//import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
    @Autowired private StatusReasonRepository statusReasonRepo;
    @Autowired private PrepACIRequestProcessor prepACIPrc;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
//		.validate(acctCustInfoValidate)
		
		from("direct:acctcustmrinfo").routeId("komi.acctcustinfo")
			
			// build request msg
			.process(prepACIPrc)
			// call ke corebank
//			.to("direct:isoadpt")
			.to("direct:accinfo")
	
			.log(LoggingLevel.DEBUG, "komi.acctcustinfo", "After callcb: ${body}")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);

					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setResponses(new ArrayList<>());

					ChnlAccountCustomerInfoResponsePojo chnlResp = new ChnlAccountCustomerInfoResponsePojo();
					chnlResp.setNoRef(rmw.getRequestId());

					Object oCbResponse = exchange.getMessage().getBody(Object.class);
//					AccountCustInfoResponseDTO cbResponse = exchange.getMessage().getBody(AccountCustInfoResponseDTO.class);

//					if (!cbResponse.getStatus().equals("ACTC")) {
					if (oCbResponse.getClass().getSimpleName().equals("FaultPojo")) {
						FaultPojo fault = (FaultPojo) oCbResponse;
						channelResponseWr.setResponseCode(fault.getResponseCode());
						channelResponseWr.setReasonCode(fault.getReasonCode());

						Optional<StatusReason> oStatusReason = statusReasonRepo.findById(fault.getReasonCode());
						if (oStatusReason.isPresent())
							channelResponseWr.setReasonMessage(oStatusReason.get().getDescription());
						else
							channelResponseWr.setReasonMessage("General Error");
					}
					
					else {
						AccountCustInfoResponseDTO cbResponse = (AccountCustInfoResponseDTO) oCbResponse;
						
						channelResponseWr.setResponseCode(cbResponse.getStatus());
						channelResponseWr.setReasonCode(cbResponse.getReason());
						Optional<StatusReason> optStatusReason = statusReasonRepo.findById(cbResponse.getReason());
						if (optStatusReason.isPresent()) {
							String desc = optStatusReason.get().getDescription();
							channelResponseWr.setReasonMessage(desc);
						}	

						chnlResp.setAccountNumber(cbResponse.getAccountNumber());
						chnlResp.setAccountType(cbResponse.getAccountType());
						chnlResp.setDebtorId(cbResponse.getCustomerId());
						chnlResp.setDebtorIdType(cbResponse.getCustomerIdType());
						chnlResp.setDebtorName(cbResponse.getCustomerName());
						chnlResp.setDebtorType(cbResponse.getCustomerType());
						chnlResp.setEmailAddressList(cbResponse.getEmailAddressList());
						chnlResp.setPhoneNumberList(cbResponse.getPhoneNumberList());
						chnlResp.setResidentialStatus(cbResponse.getResidentStatus());
						chnlResp.setTownName(cbResponse.getTownName());
						
					}
					
					
					channelResponseWr.getResponses().add(chnlResp);
					exchange.getMessage().setBody(channelResponseWr);

				}
				
			})
			
			// process resposne

			
		;
	}

}
