package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.credittransfer.ChnlCreditTransferResponse;
import bifast.outbound.credittransfer.CreditTransferRequestProcessor;
import bifast.outbound.credittransfer.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.InitSettlementRetrievalProcessor;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.ficredittransfer.ChnlFiCreditTransferResponse;
import bifast.outbound.ficredittransfer.FICreditTransferRequestProcessor;
import bifast.outbound.ficredittransfer.FICreditTransferResponseProcessor;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequest;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class TransactionRoute extends RouteBuilder {

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
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRetrievalProcessor initSettlementRequest;
	
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlCreditTransferResponseFormat = new JacksonDataFormat(ChnlCreditTransferResponse.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChannelFICreditTransferReq.class);
	JacksonDataFormat jsonChnlFICreditTransferResponseFormat = new JacksonDataFormat(ChnlFiCreditTransferResponse.class);
	JacksonDataFormat jsonChnlPaymentStatusRequestFormat = new JacksonDataFormat(ChnlPaymentStatusRequest.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlCreditTransferResponseFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferResponseFormat.setInclude("NON_EMPTY");

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlPaymentStatusRequestFormat.setInclude("NON_NULL");
		jsonChnlPaymentStatusRequestFormat.setInclude("NON_EMPTY");

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

		// Untuk Proses Credit Transfer Request

		from("direct:ctreq").routeId("crdt_trnsf")

			.process(crdtTransferProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			.setHeader("hdr_fullrequestmessage", simple("${body}"))

			.to("seda:savetableawal")

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
				
				.setHeader("hdr_fullresponsemessage", simple("${body}"))
				.log("Selesai submit CT")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ref:${header.rcv_channel.orignReffId}")

    			// check settlement
    			.setBody(simple("${header.req_objbi}"))
    			.process(initSettlementRequest)
    			.to("direct:history")
    			
    			.choice()
    				.when().simple("${body} == null")
    					.log("tidak nemu settment")
    					// prepare data unt payment status
    					.to("direct:paymentstatus")
				.end()
    					
				.setHeader("hdr_fullresponsemessage", simple("${body}"))

			.end()


			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
	
			// prepare untuk response ke channel
			.process(crdtTransferResponseProcessor)
			.marshal(jsonChnlCreditTransferResponseFormat)
			
		;

		// Untuk Proses FI Credit Transfer Request
		
		from("direct:fictreq").routeId("fictreq")
			
			.process(fiCrdtTransferRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
	
			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_fullrequestmessage", simple("${body}"))
			.log("Cekpoint 5885")

			.to("seda:savetableawal")

			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_fullresponsemessage", simple("${body}"))

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	

			// prepare untuk response ke channel
			.process(fiCrdtTransferResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			.marshal(jsonChnlFICreditTransferResponseFormat)

		;
		

		
	}
}
