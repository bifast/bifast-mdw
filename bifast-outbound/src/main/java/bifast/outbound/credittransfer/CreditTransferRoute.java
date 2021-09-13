package bifast.outbound.credittransfer;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferResponsePojo;
import bifast.outbound.history.InitSettlementRequestProcessor;
import bifast.outbound.history.RequestPojo;
import bifast.outbound.history.ResponsePojo;
import bifast.outbound.history.SettlementEnquiryResultProcessor;
import bifast.outbound.pojo.ChannelFaultResponse;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.ValidateInputProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor initSettlementRequest;
	@Autowired
	private InitPaymentStatusRequestCTProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private ValidateInputProcessor validateRequest;
	@Autowired
	private SaveCTTablesProcessor saveCTTableProcessor;
	@Autowired
	private SettlementEnquiryResultProcessor settlementEnquiryResultProcessor;

	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChnlCreditTransferRequestPojo.class);
	JacksonDataFormat jsonChnlCreditTransferResponseFormat = new JacksonDataFormat(ChnlCreditTransferResponsePojo.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChnlFICreditTransferRequestPojo.class);
	JacksonDataFormat jsonChnlFICreditTransferResponseFormat = new JacksonDataFormat(ChnlFICreditTransferResponsePojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat faultJDF = new JacksonDataFormat(ChannelFaultResponse.class);
	JacksonDataFormat SettlementRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat SettlementResponseJDF = new JacksonDataFormat(ResponsePojo.class);

	private void configureJsonDataFormat() {

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlCreditTransferResponseFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferResponseFormat.setInclude("NON_EMPTY");

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		faultJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		faultJDF.setInclude("NON_NULL");
		faultJDF.setInclude("NON_EMPTY");
		faultJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		SettlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		SettlementRequestJDF.setInclude("NON_NULL");
		SettlementRequestJDF.setInclude("NON_EMPTY");
		SettlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		
		SettlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		SettlementResponseJDF.setInclude("NON_NULL");
		SettlementResponseJDF.setInclude("NON_EMPTY");
		SettlementResponseJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();

        onException(Exception.class).routeId("CT Exception Handler")
	    	.log("Fault di CT Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(faultJDF)
			.removeHeaders("ct_*")
			.removeHeaders("req_*")
	    	.handled(true)
	  	;

		// Untuk Proses Credit Transfer Request

		from("direct:ctreq").routeId("crdt_trnsf")

			.log("Credit Transfer")
			.process(validateRequest)

			.process(crdtTransferProcessor)
			.setHeader("ct_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptCTbody")
			.to("seda:saveCTtables")

			// kirim ke CI-HUB
			.setHeader("ct_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit CT no: ${header.ct_objreqbi.appHdr.bizMsgIdr}")
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
//				.log("Selesai submit CT")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ")
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.hdr_idtable}  ")

    			// check settlement
    			.setBody(simple("${header.ct_objreqbi}"))
    			.process(initSettlementRequest)
    			.marshal(SettlementRequestJDF)
    			.log("Settlement: ${body}")
    			
    			.doTry()
					.enrich("http://localhost:9001/services/api/enquiry?"
							+ "bridgeEndpoint=true",
							enrichmentAggregator)
					.convertBodyTo(String.class)
					.unmarshal(SettlementResponseJDF)
					
	    			.process(settlementEnquiryResultProcessor)
	    		.doCatch(Exception.class)
	    			.log("Settlement error")
	    			.setBody(constant(null))
	    		.end()

	    		.log("Selesai panggil settlement")
    			.choice()
    				.when().simple("${body} == null")
    					.log("tidak ada settlement, harus PS")
    	    			.setBody(simple("${header.ct_objreqbi}"))
    	    			.process(initPaymentStatusRequestProcessor)
    	    			.to("direct:paymentstatus")

	    			.otherwise()
						.log("Ada statement")
    					.marshal(businessMessageJDF)
				.endChoice()
				.end()	

					
				.log("selesai catch")
			
			.endDoTry()
			.end()

			.log("selesai dotry")

			.choice()
				.when().simple("${body} != null")
					.setHeader("ct_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.to("seda:encryptCTbody")
					.unmarshal(businessMessageJDF)
					.setHeader("ct_objresponsebi", simple("${body}"))	
				.otherwise()
					.log("Body null akhirnya")
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
					.setHeader("ct_encrMessage", constant(null))	
	
			.end()
			
			// prepare untuk response ke channel
			.process(crdtTransferResponseProcessor)
			.marshal(jsonChnlCreditTransferResponseFormat)

			.setHeader("ct_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.to("seda:saveCTtables?exchangePattern=InOnly")
			.removeHeaders("ct_*")
		;


		from("seda:encryptCTbody")
			.setHeader("ct_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("ct_encrMessage", simple("${body}"))
			.setBody(simple("${header.ct_tmp}"))
		;
		from("seda:saveCTtables")
			.process(saveCTTableProcessor)
		;

	}
}
