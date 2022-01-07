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

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.CbCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbCustomerInfoResponsePojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
    @Autowired private StatusReasonRepository statusReasonRepo;

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
//		.validate(acctCustInfoValidate)
		
		from("direct:acctcustmrinfo").routeId("komi.acctcustinfo")
			
			// build request msg
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					ChnlAccountCustomerInfoRequestPojo chnlReq = (ChnlAccountCustomerInfoRequestPojo) rmw.getChannelRequest();
					CbCustomerInfoRequestPojo aciReq = new CbCustomerInfoRequestPojo();
					aciReq.setAccountNumber(chnlReq.getAccountNumber());
					aciReq.setKomiTrnsId(rmw.getKomiTrxId());
					aciReq.setMerchantType(rmw.getMerchantType());
					aciReq.setNoRef(chnlReq.getNoRef());
					aciReq.setTerminalId(chnlReq.getTerminalId());
					exchange.getMessage().setBody(aciReq);
				}
			})
			
			// call ke corebank
			.to("seda:callcb")
	
			.log(LoggingLevel.DEBUG, "komi.acctcustinfo", "After callcb: ${body}")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);

					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setResponses(new ArrayList<>());

					Object oCbResponse = exchange.getMessage().getBody(Object.class);
					ChnlAccountCustomerInfoResponsePojo chnlResp = new ChnlAccountCustomerInfoResponsePojo();

					chnlResp.setNoRef(rmw.getRequestId());
					
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
						CbCustomerInfoResponsePojo cbResp = (CbCustomerInfoResponsePojo) oCbResponse;
						
						channelResponseWr.setResponseCode(cbResp.getStatus());
						channelResponseWr.setReasonCode(cbResp.getReason());
						Optional<StatusReason> optStatusReason = statusReasonRepo.findById(cbResp.getReason());
						if (optStatusReason.isPresent()) {
							String desc = optStatusReason.get().getDescription();
							channelResponseWr.setReasonMessage(desc);
						}	

						chnlResp.setAccountNumber(cbResp.getAccountNumber());
						chnlResp.setAccountType(cbResp.getAccountType());
						chnlResp.setDebtorId(cbResp.getCustomerId());
						chnlResp.setDebtorIdType(cbResp.getCustomerIdType());
						chnlResp.setDebtorName(cbResp.getCustomerName());
						chnlResp.setDebtorType(cbResp.getCustomerType());
						chnlResp.setEmailAddressList(cbResp.getEmailAddressList());
						chnlResp.setPhoneNumberList(cbResp.getPhoneNumberList());
						chnlResp.setResidentialStatus(cbResp.getResidentStatus());
						chnlResp.setTownName(cbResp.getTownName());
						
					}
					
					
					channelResponseWr.getResponses().add(chnlResp);
					exchange.getMessage().setBody(channelResponseWr);

				}
				
			})
			
			// process resposne

			
		;
	}

}
