package bifast.outbound.accountcustmrinfo;

import java.time.Duration;
import java.time.Instant;
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
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
	@Autowired private ChannelTransactionRepository channelTransactionRepo;
    @Autowired private PrepACIRequestProcessor prepACIPrc;
    @Autowired private StatusReasonRepository statusReasonRepo;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
//		.validate(acctCustInfoValidate)
		
		from("direct:acctcustmrinfo").routeId("komi.acctcustinfo")
			
			// build request msg
			.process(prepACIPrc)
			// call ke corebank
			.to("direct:accinfo")
	
			.log(LoggingLevel.DEBUG, "komi.acctcustinfo", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] prepare response ${exchangeProperty.pr_response}")
			
			// prepare output for client
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
			
					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setResponses(new ArrayList<>());

					ChnlAccountCustomerInfoResponsePojo chnlResp = new ChnlAccountCustomerInfoResponsePojo();
					chnlResp.setNoRef(rmw.getRequestId());

					String response = exchange.getProperty("pr_response", String.class);
										
					if (!(response.equals("ACTC"))) {
						channelResponseWr.setResponseCode(exchange.getProperty("pr_response", String.class));
						channelResponseWr.setReasonCode(exchange.getProperty("pr_reason", String.class));

						Optional<StatusReason> oStatusReason = statusReasonRepo.findById(channelResponseWr.getReasonCode());
						if (oStatusReason.isPresent())
							channelResponseWr.setReasonMessage(oStatusReason.get().getDescription());
						else
							channelResponseWr.setReasonMessage("General Error");
					}
					
					else {
						AccountCustInfoResponseDTO cbResponse = exchange.getMessage().getBody(AccountCustInfoResponseDTO.class);
						
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
			
			// save channel transaction
//			.log(LoggingLevel.DEBUG, "komi.acctcustinfo", 
//					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Save table channel_transaction.")
//			.process(new Processor() {
//				public void process(Exchange exchange) throws Exception {
//					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
//					ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
//					ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);
//
//					Optional<ChannelTransaction> optChannel = channelTransactionRepo.findById(rmw.getKomiTrxId());
//					ChannelTransaction chnlTrns = optChannel.get();
//					
//					long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
//					chnlTrns.setElapsedTime(timeElapsed);
//
//					if (respColl.getCallStatus().equals("SUCCESS")) {
//						chnlTrns.setCallStatus("SUCCESS");
//						chnlTrns.setResponseCode(responseWr.getResponseCode());
//					}
//					else {
//						chnlTrns.setCallStatus(respColl.getCallStatus());
//						chnlTrns.setErrorMsg("(" + responseWr.getReasonCode() + ") " + responseWr.getReasonMessage());
//						chnlTrns.setResponseCode(responseWr.getResponseCode());
//					}
//
//					channelTransactionRepo.save(chnlTrns);
//				}
//			})
		;
	}

}
