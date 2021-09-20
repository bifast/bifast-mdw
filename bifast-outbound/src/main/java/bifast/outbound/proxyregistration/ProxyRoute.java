package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private SavePrxyTablesProcessor savePrxyTablesProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private ValidatePrxyRegistrationProcessor validatePrxyRegsProcessor;
	@Autowired
	private FaultProcessor faultProcessor;


	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
        onException(Exception.class).routeId("Proxy Exception Handler")
	    	.log("Fault di Prxy Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
    	;

		// Untuk Proses Proxy Registration Request

		from("direct:proxyregistration").routeId("proxyregistration")

			.setHeader("hdr_errorlocation", constant("PrxyRoute/InputValidation"))
			.process(validatePrxyRegsProcessor)

			.setHeader("hdr_errorlocation", constant("PrxyRoute/BuildRegistratinMsg"))
			.process(proxyRegistrationRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)

			.setHeader("hdr_errorlocation", constant("PrxyRoute/saveTabelInit"))
			.to("seda:savePrxytables")


			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_errorlocation", constant("PrxyRoute/sendCIHub"))
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{komi.ciconnector-url}}?"
					+ "socketTimeout={{komi.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_fullresponsemessage", simple("${body}"))

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.setHeader("hdr_errorlocation", constant("PrxyRoute/ProcessResponse"))
			.process(proxyRegistrationResponseProcessor)
			
			.log("proxyRegistrationResponseProcessor selesai")
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.setHeader("resp_channel", simple("${body}"))			
			.marshal(chnlResponseJDF)
			
			// save audit tables
			.setHeader("hdr_errorlocation", constant("PrxyRoute/updateTable"))
			.to("seda:savePrxytables?exchangePattern=InOnly")

			
		;	

		
		from("direct:proxyresolution").routeId("proxyresolution")
		
//			.setHeader("hdr_errorlocation", constant("proxyresolution/InputValidation"))
//			.process(validatePrxyRegsProcessor)

			.setHeader("hdr_errorlocation", constant("PrxyRoute/BuildRegistratinMsg"))
			.process(proxyResolutionRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
	
			.setHeader("hdr_errorlocation", constant("PrxyRoute/saveTabelInit"))
			.to("seda:savePrxytables")
	
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

//			// kirim ke CI-HUB
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{komi.ciconnector-url}}?"
					+ "socketTimeout={{komi.timeout}}&" 
					+ "bridgeEndpoint=true",
					enrichmentAggregator)
			.convertBodyTo(String.class)
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_fullresponsemessage", simple("${body}"))

			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
					
			// prepare untuk response ke channel
			.process(proxyResolutionResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			.marshal(chnlResponseJDF)
			.setHeader("req_channelResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			// save audit tables
			.setHeader("hdr_errorlocation", constant("PrxyRoute/updateTable"))
			.to("seda:savePrxytables?exchangePattern=InOnly")
		;

		from("seda:savePrxytables")
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_encrMessage", simple("${body}"))
			.process(savePrxyTablesProcessor)
			.setBody(simple("${header.hdr_tmp}"))
			.removeHeader("hdr_tmp")
		;

	}
}

