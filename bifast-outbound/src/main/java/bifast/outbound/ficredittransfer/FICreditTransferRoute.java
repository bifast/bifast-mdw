package bifast.outbound.ficredittransfer;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
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
public class FICreditTransferRoute extends RouteBuilder {

	@Autowired
	private FICreditTransferRequestProcessor fiCrdtTransferRequestProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fiCrdtTransferResponseProcessor;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private InitSettlementRequestProcessor initSettlementRequest;
	@Autowired
	private InitPaymentStatusRequestFICTProcessor initPaymentStatusRequestProcessor;
	@Autowired
	private ValidateInputProcessor validateRequest;
	@Autowired
	private SaveFICTTablesProcessor saveFICTTableProcessor;
	@Autowired
	private SettlementEnquiryResultProcessor settlementEnquiryResultProcessor;

	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChnlFICreditTransferRequestPojo.class);
	JacksonDataFormat jsonChnlFICreditTransferResponseFormat = new JacksonDataFormat(ChnlFICreditTransferResponsePojo.class);
	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat faultJDF = new JacksonDataFormat(ChannelFaultResponse.class);
	JacksonDataFormat SettlementRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat SettlementResponseJDF = new JacksonDataFormat(ResponsePojo.class);

	private void configureJsonDataFormat() {

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");

		jsonChnlFICreditTransferResponseFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferResponseFormat.setInclude("NON_EMPTY");

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

        onException(Exception.class).routeId("FICT Exception Handler")
	    	.log("Fault di FICT Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(faultJDF)
			.removeHeaders("fict_*")
			.removeHeaders("req_*")
	    	.handled(true)
	  	;

		// Untuk Proses Credit Transfer Request

		from("direct:fictreq").routeId("fictreq")

			.log("FI Credit Transfer")
			.process(validateRequest)

			.process(fiCrdtTransferRequestProcessor)
			.setHeader("fict_objreqbi", simple("${body}"))
			.marshal(businessMessageJDF)

			.to("seda:encryptFICTbody")
			.to("seda:saveFICTtables")

			// kirim ke CI-HUB
			.setHeader("fict_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.doTry()
				.log("Submit CT no: ${header.fict_objreqbi.appHdr.bizMsgIdr}")
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{bifast.ciconnector-url}}?"
						+ "socketTimeout={{bifast.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.log("Selesai submit FICT")
				
			.doCatch(SocketTimeoutException.class)     // klo timeout maka kirim payment status
    			.log(LoggingLevel.ERROR, "Timeout process Credit Transfer ref:${header.hdr_channelRequest.orignReffId}")
    			.to("sql:update outbound_message set resp_status = 'TIMEOUT', "
    					+ " error_msg= 'Timeout' "
    					+ "where id= :#${header.hdr_idtable}  ")

    			// check settlement
    			.log("Cari settlement untuk : ${header.fict_objreqbi.appHdr.bizMsgIdr}")
    			.setBody(simple("${header.fict_objreqbi}"))
    			.process(initSettlementRequest)
    			.marshal(SettlementRequestJDF)

				.enrich("http://localhost:9001/services/api/enquiry?"
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.unmarshal(SettlementResponseJDF)
				
    			.process(settlementEnquiryResultProcessor)

    			.choice()
    				.when().simple("${body} == null")
    					.log("tidak ada settlement, harus PS")
    	    			.setBody(simple("${header.fict_objreqbi}"))
    	    			.process(initPaymentStatusRequestProcessor)
    	    			.to("direct:paymentstatus")

	    			.otherwise()
						.log("Ada statement")
    					.marshal(businessMessageJDF)
				.end()
				
				.log("selesai catch")
			
			.endDoTry()
			.end()

			.log("selesai dotry")
//			.setHeader("fict_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.choice()
				.when().simple("${body} != null")
					.setHeader("fict_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
					.to("seda:encryptFICTbody")
					.unmarshal(businessMessageJDF)
					.setHeader("fict_objresponsebi", simple("${body}"))	
				.otherwise()
					.setHeader("fict_encrMessage", constant(null))	

			.end()
				
	
			// prepare untuk response ke channel
			.process(fiCrdtTransferResponseProcessor)
			.marshal(jsonChnlFICreditTransferResponseFormat)

			.setHeader("fict_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.to("seda:saveFICTtables?exchangePattern=InOnly")
			
			.removeHeaders("fict_*")
		;


		from("seda:encryptFICTbody")
			.setHeader("fict_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("fict_encrMessage", simple("${body}"))
			.setBody(simple("${header.fict_tmp}"))
		;
		from("seda:saveFICTtables")
			.process(saveFICTTableProcessor)
		;

	}
}
