package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
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
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.CheckSettlementProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

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
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private FICreditTransferRequestProcessor fiCrdtTransferRequestProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fiCrdtTransferResponseProcessor;
//	@Autowired
//	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
//	@Autowired
//	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private PaymentStatusOnTimeoutProcessor paymentStatusTimeoutProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(ChannelAccountEnquiryReq.class);
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChannelFICreditTransferReq.class);
	JacksonDataFormat jsonChnlPaymentStatusRequestFormat = new JacksonDataFormat(ChannelPaymentStatusRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {
		jsonChnlAccountEnqrReqFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrReqFormat.setInclude("NON_EMPTY");

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlPaymentStatusRequestFormat.setInclude("NON_NULL");
		jsonChnlPaymentStatusRequestFormat.setInclude("NON_EMPTY");

		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
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
		
		// Untuk Proses Account Enquiry Request

		from("direct:acctenqr").routeId("direct:acctenqr")
			.setHeader("log_filename", simple("acctenqr.${header.rcv_channel.intrnRefId}.arch"))

			//log channel request message
			.marshal(jsonChnlAccountEnqrReqFormat)
			.setHeader("log_label", constant("Channel Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// convert channel request jadi pacs008 message
			.unmarshal(jsonChnlAccountEnqrReqFormat)
			
			.process(accountEnquiryProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			//log message ke ci-hub
			.setHeader("log_label", constant("CI-Hub Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)

				// log message dari ci-hub
				.setHeader("log_label", constant("CI-Hub Response"))
				.to("seda:savelogfiles?exchangePattern=InOnly")

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
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.marshal(jsonChnlResponseFormat)
			
			// log message reponse ke channel
			.setHeader("log_label", constant("Channel Response"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

		;

		// Untuk Proses Credit Transfer Request

		from("direct:ctreq").routeId("crdt_trnsf")
			.setHeader("log_filename", simple("crdttrns.${header.rcv_channel.intrnRefId}.arch"))

			//log channel request message
			.marshal(jsonChnlCreditTransferRequestFormat)
			.setHeader("log_label", constant("Channel Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// convert channel request jadi pacs008 message
			.unmarshal(jsonChnlCreditTransferRequestFormat)
			.process(crdtTransferProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			//log message ke ci-hub
			.setHeader("log_label", constant("CI-Hub Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit CT no: ${header.req_objbi.appHdr.bizMsgIdr}")
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
				// log message dari ci-hub
				.setHeader("log_label", constant("CI-Hub Response"))
				.to("seda:savelogfiles?exchangePattern=InOnly")

			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
				.log("Timeout")
				.setHeader("log_label", constant("TIMEOUT"))
				.to("seda:savelogfiles?exchangePattern=InOnly")

				// cek settlement dulu, kalo ga ada baru payment status
				.process(checkSettlementProcessor)
				.choice()
					.when().simple("${body} == ''")
						.log("Payment Status")
						.process(paymentStatusTimeoutProcessor)
						.marshal(jsonBusinessMessageFormat)

						// log message dari ci-hub
						.setHeader("log_label", constant("Payment Status Request"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

						.setHeader("HttpMethod", constant("POST"))
						.enrich("http:{{bifast.ciconnector-url}}?bridgeEndpoint=true", enrichmentAggregator)
						.convertBodyTo(String.class)

						// log message dari ci-hub
						.setHeader("log_label", constant("CI-Hub Response"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

					.otherwise()
						.log("Nemu settlement")
						// log message dari ci-hub
						.setHeader("log_label", constant("Settlement"))
						.to("seda:savelogfiles?exchangePattern=InOnly")

				.end()
			.endDoTry()
			.end()
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(crdtTransferResponseProcessor)
			.marshal(jsonChnlResponseFormat)
			
			// log message reponse ke channel
			.setHeader("log_label", constant("Channel Response"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

		;

		// Untuk Proses FI Credit Transfer Request
		
		from("direct:fictreq").routeId("fictreq")
			.setHeader("log_filename", simple("ficrdttrns.${header.rcv_channel.intrnRefId}.arch"))
			
			//log channel request message
			.marshal(jsonChnlFICreditTransferRequestFormat)
			.setHeader("log_label", constant("Channel Request Message"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// convert channel request jadi pacs009 message
			.unmarshal(jsonChnlFICreditTransferRequestFormat)
			.process(fiCrdtTransferRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
	
			//log message ke ci-hub
			.setHeader("log_label", constant("CI-Hub Request"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// log message dari ci-hub
			.setHeader("log_label", constant("CI-Hub Response"))
			.to("seda:savelogfiles?exchangePattern=InOnly")

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	

			// prepare untuk response ke channel
			.process(fiCrdtTransferResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			.marshal(jsonChnlResponseFormat)

			// log message reponse ke channel
			.setHeader("log_label", constant("Channel Response"))
			.to("seda:savelogfiles?exchangePattern=InOnly")


		;

		
		// Untuk Proses Payment Status Request

//		from("direct:pymtstatus")
//			.convertBodyTo(String.class)
//			.unmarshal(jsonChnlPaymentStatusRequestFormat)
//			.setHeader("req_channelReq",simple("${body}"))
//
//			// convert channel request jadi pacs028 message
//			.process(paymentStatusRequestProcessor)
//			.setHeader("req_objbi", simple("${body}"))
//			.marshal(jsonBusinessMessageFormat)
//			
//			// kirim ke CI-HUB
//			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{bifast.ciconnector-url}}?"
//					+ "socketTimeout={{bifast.timeout}}&" 
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//
//			.convertBodyTo(String.class)
//			.unmarshal(jsonBusinessMessageFormat)
//			.setHeader("resp_objbi", simple("${body}"))	
//			
//			// prepare untuk response ke channel
//			.process(paymentStatusResponseProcessor)
//			.setHeader("resp_channel", simple("${body}"))
//			
//			.setExchangePattern(ExchangePattern.InOnly)
//			.to("seda:endlog")
//
//			.setBody(simple("${header.resp_channel}"))
//			.marshal(jsonChnlResponseFormat)
//			.removeHeaders("resp_*")
//			.removeHeaders("req_*")
//		;
				
	}
}
