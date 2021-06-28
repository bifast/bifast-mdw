package bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelAccountEnquiryReq;
import bifast.outbound.pojo.ChannelAccountEnquiryResp;
import bifast.outbound.pojo.ChannelCreditTransferRequest;
import bifast.outbound.pojo.ChannelCreditTransferResponse;
import bifast.outbound.processor.AccountEnquiryProcessor;
import bifast.outbound.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.processor.CreditTransferRequestProcessor;
import bifast.outbound.processor.CreditTransferResponseProcessor;
import bifast.outbound.processor.SaveAccountEnquiryProcessor;
import bifast.outbound.processor.SaveCstmrCreditTransferProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;



@Component
public class CreditTransferRoute extends RouteBuilder {

	// @Autowired
	// private CreditTransferRequestProcessor creditTransferRequestProcessor;
	@Autowired
	private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiry;
	@Autowired
	private SaveCstmrCreditTransferProcessor saveCstmrCreditTransfer;
	
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(ChannelAccountEnquiryReq.class);
	JacksonDataFormat jsonChnlAccountEnqrRespFormat = new JacksonDataFormat(ChannelAccountEnquiryResp.class);
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlCreditTransferResponseFormat = new JacksonDataFormat(ChannelCreditTransferResponse.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {
		jsonChnlAccountEnqrReqFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrReqFormat.setInclude("NON_EMPTY");
		jsonChnlAccountEnqrReqFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlAccountEnqrRespFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlAccountEnqrRespFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrRespFormat.setInclude("NON_EMPTY");
		jsonChnlAccountEnqrRespFormat.setPrettyPrint(true);
		jsonChnlAccountEnqrRespFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");
		jsonChnlCreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlCreditTransferResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlCreditTransferResponseFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferResponseFormat.setInclude("NON_EMPTY");
		jsonChnlCreditTransferResponseFormat.setPrettyPrint(true);
		jsonChnlCreditTransferResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
		restConfiguration().component("servlet")
        	.apiContextPath("/api-doc")
	            .apiProperty("api.title", "KOMI API Documentation").apiProperty("api.version", "1.0.0")
	            .apiProperty("cors", "true");

        ;
		
		rest("/channel")
			.post("/acctenquiry")
				.description("Pengiriman instruksi Account Enquiry ke bank lain melalui BI-FAST")
				.param()
					.name("IntrnRefId")
					.description("Kode ID Internal dari channel")
					.type(RestParamType.body).dataType("String")
					.endParam()
				.param()
					.name("ChannelType")
					.description("Kode channel")
					.type(RestParamType.body).dataType("String")
					.endParam()
				.param()
					.name("ReceivingParticipant")
					.description("Kode Swift dari bank tujuan")
					.type(RestParamType.body).dataType("String")
					.endParam()
				.param()
					.name("Amount")
					.description("Nilai yang akan ditransfer")
					.type(RestParamType.body).dataType("Decimal")
					.endParam()
				.param()
					.name("categoryPurpose")
					.description("Kode CategoryPurpose sesuai acuan BI")
					.type(RestParamType.body).dataType("String")
					.endParam()
				.param()
					.name("CreditorAccountNumber")
					.description("Nomor Rekening tujuan")
					.type(RestParamType.body).dataType("String")
					.endParam()
					
				.consumes("application/json")
				.to("direct:acctenqr")
				
			.post("/cstmrcrdtrn")
				.description("Pengiriman instruksi Credit Transfer ke bank lain melalui BI-FAST")
				.consumes("application/json")
				.to("direct:ctreq")
		;
		
		from("direct:acctenqr").routeId("direct:acctenqr")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlAccountEnqrReqFormat)
			.setHeader("req_channelReq",simple("${body}"))
			
			// convert channel request jadi pacs008 message
			.process(accountEnquiryProcessor)
			.setHeader("req_objbiacctenqr", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			.setHeader("req_jsonbiacctenqr", simple("${body}"))

			// kirim ke CI-HUB
			.to("rest:post:acctenqP1?host=demo3216691.mockable.io&producerComponentName=http&bridgeEndpoint=true")
			.convertBodyTo(String.class)
			.setHeader("resp_jsonbiacctenqr", simple("${body}"))
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbiacctenqr", simple("${body}"))	

			.process(saveAccountEnquiry)
			
			// prepare untuk response ke channel
			.process(accountEnqrResponseProcessor)
			.marshal(jsonChnlAccountEnqrRespFormat)
			.removeHeaders("req_*")
			.removeHeaders("resp_*")

		;

		from("direct:ctreq").routeId("cstmrcrdttrn")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlCreditTransferRequestFormat)
			.setHeader("req_channelReq",simple("${body}"))
			
			// convert channel request jadi pacs008 message
			.process(crdtTransferProcessor)
			.setHeader("req_objcrdtTrnReq", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			.setHeader("req_jsonbicrdttrn", simple("${body}"))
			
			// kirim ke CI-HUB
			.to("rest:post:crdttrfreqP1?host=demo3216691.mockable.io&producerComponentName=http&bridgeEndpoint=true")
			.convertBodyTo(String.class)
			.setHeader("resp_jsonbicrdttrn", simple("${body}"))
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbicrdttrn", simple("${body}"))	

			.process(saveCstmrCreditTransfer)
			
			// prepare untuk response ke channel
			.process(crdtTransferResponseProcessor)
			.marshal(jsonChnlCreditTransferResponseFormat)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
		;
		
	}
}
