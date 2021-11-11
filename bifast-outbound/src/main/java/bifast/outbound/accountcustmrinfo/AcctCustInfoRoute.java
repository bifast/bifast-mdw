package bifast.outbound.accountcustmrinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.CbCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbCustomerInfoResponsePojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
	
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
					aciReq.setTerminalId("000000");
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

					CbCustomerInfoResponsePojo cbResp = exchange.getMessage().getBody(CbCustomerInfoResponsePojo.class);
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
