package bifast.outbound.accountcustmrinfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CbAccountCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbAccountCustomerInfoResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlAccountCustomerInfoResponsePojo;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
	@Autowired
	private JacksonDataFormatService jdfService;
	
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciResponseJdf = jdfService.basic(CbAccountCustomerInfoResponsePojo.class);
		JacksonDataFormat chnlAciResponseJdf = jdfService.basic(ChnlAccountCustomerInfoResponsePojo.class);
//		.validate(acctCustInfoValidate)
		
		from("direct:acctcustmrinfo").routeId("komi.acctcustinfo")
			.log("Acctinfo mulai")
			
			// build request msg
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					ChnlAccountCustomerInfoRequestPojo chnReq = (ChnlAccountCustomerInfoRequestPojo) rmw.getChannelRequest();
					
					CbAccountCustomerInfoRequestPojo aciReq = new CbAccountCustomerInfoRequestPojo();
					aciReq.setAccountNumber(chnReq.getAccountNumber());

					DateTimeFormatter fmtMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
					aciReq.setDateTime(fmtMillis.format(LocalDateTime.now()));

					aciReq.setMerchantType(rmw.getMerchantType());
					aciReq.setNoRef(rmw.getRequestId());
					aciReq.setTerminalId("000");
					aciReq.setTransactionId("000000");
					
					rmw.setAccoutCustomerInfoRequest(aciReq);
					exchange.getMessage().setBody(aciReq);
				}
			})
			
			// call ke corebank
			
			.setBody(constant("{\n"
					+ "\"transactionId\" : \"000000\",\n"
					+ "\"dateTime\" : \"2021-10-26T08:45:20.201\",\n"
					+ "\"merchantType\" : \"6000\",\n"
					+ "\"terminalId\" : \"00000\",\n"
					+ "\"noRef\" : \"KOM00000000\",\n"
					+ "\"status\" : \"ACTC\",\n"
					+ "\"reason\" : \"U000\",\n"
					+ "\"emailAddressList\" : [\"ada@glodok.com\",\"yogi@mii.com\"],\n"
					+ "\"phoneNumberList\" : [\"081111\",\"08333333\"],\n"
					+ "\"accountNumber\" : \"522222320\",\n"
					+ "\"accountType\" : \"CACC\",\n"
					+ "\"customerName\" : \"Abang Andre\",\n"
					+ "\"customerType\" : \"01\",\n"
					+ "\"customerId\" : \"222233333\",\n"
					+ "\"customerIdType\" : \"01\",\n"
					+ "\"residentStatus\" : \"01\",\n"
					+ "\"townName\" : \"0040\"\n"
					+ "}\n"))

			.log("${body}")
			
			.unmarshal(aciResponseJdf)
			.log("${body.class}")
			.log("${body.emailAddressList[0]}")
			
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
