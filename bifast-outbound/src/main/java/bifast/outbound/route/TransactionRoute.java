package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.AccountEnquiryProcessor;
import bifast.outbound.accountenquiry.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.credittransfer.CreditTransferRequestProcessor;
import bifast.outbound.credittransfer.CreditTransferResponseProcessor;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.ficredittransfer.FICreditTransferRequestProcessor;
import bifast.outbound.ficredittransfer.FICreditTransferResponseProcessor;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.paymentstatus.PaymentStatusOnTimeoutProcessor;
import bifast.outbound.paymentstatus.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.PaymentStatusResponseProcessor;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.CheckSettlementProcessor;
import bifast.outbound.processor.CombineMessageProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.SaveOutboundMesgProcessor;
import bifast.outbound.processor.SaveTracingTableProcessor;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;
import bifast.outbound.reversect.ReverseCreditTrnRequestProcessor;
import bifast.outbound.reversect.ReverseCreditTrnResponseProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class TransactionRoute extends RouteBuilder {

	@Autowired
	private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private CheckSettlementProcessor checkSettlementProcessor;
	@Autowired
	private CombineMessageProcessor combineMessageProcessor;
	@Autowired
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private FICreditTransferRequestProcessor fiCrdtTransferRequestProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fiCrdtTransferResponseProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private PaymentStatusOnTimeoutProcessor paymentStatusTimeoutProcessor;
	@Autowired
	private ReverseCreditTrnRequestProcessor reverseCTRequestProcessor;
	@Autowired
	private ReverseCreditTrnResponseProcessor reverseCTResponseProcessor;
	@Autowired
	private SaveOutboundMesgProcessor saveOutboundMesg;
	@Autowired
	private SaveTracingTableProcessor saveTracingTable;

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(ChannelAccountEnquiryReq.class);
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChannelFICreditTransferReq.class);
	JacksonDataFormat jsonChnlPaymentStatusRequestFormat = new JacksonDataFormat(ChannelPaymentStatusRequest.class);
	JacksonDataFormat jsonChnlReverseCTRequestFormat = new JacksonDataFormat(ChannelReverseCreditTransferRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {
		jsonChnlAccountEnqrReqFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrReqFormat.setInclude("NON_EMPTY");
//		jsonChnlAccountEnqrReqFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlCreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlFICreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlPaymentStatusRequestFormat.setInclude("NON_NULL");
		jsonChnlPaymentStatusRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlPaymentStatusRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlReverseCTRequestFormat.setInclude("NON_NULL");
		jsonChnlReverseCTRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlReverseCTRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.setPrettyPrint(true);
		jsonChnlResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

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

			.post("/ficrdtrn")
				.description("Pengiriman instruksi Financial Institution Credit Transfer melalui BI-FAST")
				.consumes("application/json")
				.to("direct:fictreq")

			.post("/pymtstatus")
				.description("Check status instruksi Credit Transfer yang pernah dikirim sebelumnya")
				.consumes("application/json")
				.to("direct:pymtstatus")

			.post("/reversect")
				.description("Pengiriman Instruksi Reverse Credit Transfer")
				.consumes("application/json")
				.to("direct:reversect")
		
		;

		// Untuk Proses Account Enquiry Request

		from("direct:acctenqr").routeId("direct:acctenqr")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlAccountEnqrReqFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("AccountEnquiry"))

			// convert channel request jadi pacs008 message
			.process(accountEnquiryProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
			.doTry()

				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.outbound.ciconnector-url}}?"
						+ "socketTimeout=2000&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)

				.unmarshal(jsonBusinessMessageFormat)

				.setHeader("resp_objbi", simple("${body}"))	
				// prepare untuk response ke channel
				.process(accountEnqrResponseProcessor)
				.setHeader("resp_channel", simple("${body}"))
			
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
				.log("Timeout")
				.process(faultProcessor)
				.setHeader("resp_channel", simple("${body}"))

			.end()

			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
			
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("req*")
			.removeHeaders("resp_*")
		;

		// Untuk Proses Credit Transfer Request

		from("direct:ctreq").routeId("cstmrcrdttrn")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlCreditTransferRequestFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("CreditTransfer"))

			// convert channel request jadi pacs008 message
			.process(crdtTransferProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
			.doTry()
				.log("Submit CT no: ${header.req_objbi.appHdr.bizMsgIdr}")
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.outbound.ciconnector-url}}?"
						+ "socketTimeout=2000&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.log("CT Request")

			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
				.log("Timeout")
				// cek settlement dulu, kalo ga ada baru payment status
				.process(checkSettlementProcessor)
				.choice()
					.when().simple("${body} == ''")
						.log("Payment Status")
						.process(paymentStatusTimeoutProcessor)
						.marshal(jsonBusinessMessageFormat)
						.setHeader("HttpMethod", constant("POST"))
						.enrich("http:{{bifast.outbound.ciconnector-url}}?bridgeEndpoint=true", enrichmentAggregator)
						.convertBodyTo(String.class)
					.otherwise()
						.log("Nemu settlement")
				.end()
			.end()
			
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(crdtTransferResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
					
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")

			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
		;

		// Untuk Proses FI Credit Transfer Request
		
		from("direct:fictreq").routeId("fictreq")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlFICreditTransferRequestFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("FICreditTransfer"))

			// convert channel request jadi pacs009 message
			.process(fiCrdtTransferRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
	
			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.outbound.ciconnector-url}}?"
					+ "bridgeEndpoint=true",
					enrichmentAggregator)

			.convertBodyTo(String.class)
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	

			.process(fiCrdtTransferResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))

			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
	
			// prepare untuk response ke channel
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("req*")
			.removeHeaders("resp_*")
		;

		
		// Untuk Proses Payment Status Request

		from("direct:pymtstatus")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlPaymentStatusRequestFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("PaymentStatus"))

			// convert channel request jadi pacs028 message
			.process(paymentStatusRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.outbound.ciconnector-url}}?"
					+ "socketTimeout=2000&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)

			.convertBodyTo(String.class)
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(paymentStatusResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")

			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
		;
		
		// Untuk Proses Reverse Credit Transfer
		from("direct:reversect")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlReverseCTRequestFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("ReverseCreditTransfer"))
			
			// convert channel request jadi pacs008 message
			.process(reverseCTRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.outbound.ciconnector-url}}?"
					+ "socketTimeout=2000&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	

			.process(reverseCTResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))

			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
			
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("resp_*")
			.removeHeaders("req_*")
		;

		from("seda:endlog")
			.log("Save log and trace")
			.process(saveOutboundMesg)
			.process(saveTracingTable)
			.process(combineMessageProcessor)
			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.req_fileName}")
			;

		
	}
}
