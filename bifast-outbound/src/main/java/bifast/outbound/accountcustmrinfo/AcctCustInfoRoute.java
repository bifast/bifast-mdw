package bifast.outbound.accountcustmrinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.CbAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbAccountCustomerInfoResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.CorebankService;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
	@Autowired
	private CorebankService cbService;
	
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
					CbAccountCustomerInfoRequestPojo aciReq = cbService.initAccountCustInfoRequest(rmw);
					ChnlAccountCustomerInfoRequestPojo chnlReq = (ChnlAccountCustomerInfoRequestPojo) rmw.getChannelRequest();
					aciReq.setAccountNumber(chnlReq.getAccountNumber());
					exchange.getMessage().setBody(aciReq);
				}
			})
			
			// call ke corebank
			.to("seda:callcb")
	
		
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
					channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
					channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
					channelResponseWr.setResponses(new ArrayList<>());

					CbAccountCustomerInfoResponsePojo cbResp = exchange.getMessage().getBody(CbAccountCustomerInfoResponsePojo.class);
					ChnlAccountCustomerInfoResponsePojo chnlResp = new ChnlAccountCustomerInfoResponsePojo();
					
					channelResponseWr.setResponseCode(cbResp.getStatus());
					channelResponseWr.setReasonCode(cbResp.getReason());
					chnlResp.setAccountNumber(cbResp.getAccountNumber());
					chnlResp.setAccountType(cbResp.getAccountType());
					chnlResp.setDebtorId(cbResp.getCustomerId());
					chnlResp.setDebtorIdType(cbResp.getCustomerIdType());
					chnlResp.setDebtorName(cbResp.getCustomerName());
					chnlResp.setDebtorType(cbResp.getCustomerType());
					chnlResp.setEmailAddressList(cbResp.getEmailAddressList());
					chnlResp.setNoRef(cbResp.getNoRef());
					chnlResp.setPhoneNumberList(cbResp.getPhoneNumberList());
					chnlResp.setResidentialStatus(cbResp.getResidentStatus());
					chnlResp.setTownName(cbResp.getTownName());
					
					channelResponseWr.getResponses().add(chnlResp);
					exchange.getMessage().setBody(channelResponseWr);

				}
				
			})
			
			// process resposne

			
		;
	}

}
