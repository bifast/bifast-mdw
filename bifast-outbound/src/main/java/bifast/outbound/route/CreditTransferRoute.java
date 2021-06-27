package bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import fransmz.bifast.credittransfer.AccountEnquiryProcessor;
import fransmz.bifast.credittransfer.CTMonitorStoreDbProcessor;
import fransmz.bifast.credittransfer.CreditTransferRequestInput;
import fransmz.bifast.credittransfer.CreditTransferRequestProcessor;
import fransmz.bifast.credittransfer.ResponseMsg;
import fransmz.bifast.credittransfer.ResponseMsgProcessor;
import fransmz.bifast.pojo.BusinessMessage;
import fransmz.bifast.service.ExtractStatusResponseProcessor;




@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private CreditTransferRequestProcessor creditTransferRequestProcessor;
	@Autowired
	private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired
	private ExtractStatusResponseProcessor extractResponseProcessor;
	@Autowired
	private ResponseMsgProcessor responseMsgProcessor;
	@Autowired
	private CTMonitorStoreDbProcessor saveMonitorDb;
	
	JacksonDataFormat jsonCreditTransferRequestInputDataFormat = new JacksonDataFormat(CreditTransferRequestInput.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat jsonResponseMsgFormat = new JacksonDataFormat(ResponseMsg.class);

	private void configureJsonDataFormat() {
		jsonCreditTransferRequestInputDataFormat.setInclude("NON_NULL");
		jsonCreditTransferRequestInputDataFormat.setInclude("NON_EMPTY");
		jsonCreditTransferRequestInputDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		// jsonBusinessMessageFormat.setPrettyPrint(true);
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonResponseMsgFormat.setInclude("NON_NULL");
		jsonResponseMsgFormat.setInclude("NON_EMPTY");
		jsonResponseMsgFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
		restConfiguration().component("servlet");
		rest("/case")
			.post("/cstmrct")
				.consumes("application/json")
				.to("direct:ctreq")
		;
		

		from("direct:ctreq").routeId("cstmrcrdttrn")
			.convertBodyTo(String.class)
			.unmarshal(jsonCreditTransferRequestInputDataFormat)
			.setHeader("req_orgnlReq",simple("${body}"))
			
			.process(accountEnquiryProcessor)
			.setHeader("req_acctEnqReq", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			.to("rest:post:acctenqP1?host=demo3216691.mockable.io&producerComponentName=http&bridgeEndpoint=true")
			.convertBodyTo(String.class)
			.to("file:/home/fransdm/workspace/sink?fileName=accountenqr.txt&fileExist=Append")
			// .log("Account Enquiry HttpResponse: ${header.CamelHttpResponseCode}")
			.log("Content-Length: ${header.Content-Length} vs. ${body.length}")

			// proses Account Enquiry Confirmation
			.unmarshal(jsonBusinessMessageFormat)
			// .log("selesai unmarshal(jsonBusinessMessageFormat)")
			.process(extractResponseProcessor)
			.setHeader("req_step", constant("acctEnqr"))
			.to("seda:storedb")
			.log("Status AcountEnquiry Response: ${header.resp_status}")
			
			.choice()
				.when().simple("${header.resp_status} == 'ACTC'")
					.process(creditTransferRequestProcessor)
					.setHeader("req_crdtTrnReq", simple("${body}"))
					.marshal(jsonBusinessMessageFormat)
					
					// .to("rest:post:crdttrfreqN1?host=demo3216691.mockable.io&producerComponentName=http&bridgeEndpoint=true")
					.to("rest:post:castlemock/mock/rest/project/pta9nB/application/TuxUb1/acctenq" +
					"?host=localhost:7010&producerComponentName=http&bridgeEndpoint=true")
					.convertBodyTo(String.class)
					.to("file:/home/fransdm/workspace/sink?fileName=crdttrn.txt&fileExist=Append")
					// .log("Credit Request HttpResponse: ${header.CamelHttpResponseCode}")
					.log("Content-Length: ${header.Content-Length} vs. ${body.length}")
					
					.unmarshal(jsonBusinessMessageFormat)
					.process(extractResponseProcessor)
					.choice()
						.when().simple("${header.resp_status} == 'ACTC'")
							.log("CT Accepted")
						.otherwise()
							.log("Credit Transfer REJECTED")
					.endChoice()
				.otherwise()
					.log("Account Enquery Rejected")
			.end()

			.setHeader("req_step", constant("crdtTrn"))
			.to("seda:storedb")
			.process(responseMsgProcessor)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
			.marshal(jsonResponseMsgFormat)
		;
		
		
		from("seda:storedb")
			.process(saveMonitorDb)
		;

	}
}
