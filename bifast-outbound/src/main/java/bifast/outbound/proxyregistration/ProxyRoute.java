package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.ValidateAEInputProcessor;
import bifast.outbound.pojo.ChannelFaultResponse;
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
	private ValidatePrxyInputProcessor validatePrxyRegsProcessor;
	@Autowired
	private FaultProcessor faultProcessor;


	JacksonDataFormat chnlProxyRegistrationJDF = new JacksonDataFormat(ChannelProxyRegistrationReq.class);
	JacksonDataFormat jsonChnlProxyRegistrationResponseFormat = new JacksonDataFormat(ChnlProxyRegistrationResponse.class);
	JacksonDataFormat jsonChnlProxyResolutionResponseFormat = new JacksonDataFormat(ChnlProxyResolutionResponse.class);
	JacksonDataFormat jsonChnlProxyResolutionFormat = new JacksonDataFormat(ChannelProxyResolutionReq.class);
	JacksonDataFormat faultJDF = new JacksonDataFormat(ChannelFaultResponse.class);

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {

		chnlProxyRegistrationJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		chnlProxyRegistrationJDF.setInclude("NON_NULL");
		chnlProxyRegistrationJDF.setInclude("NON_EMPTY");
		chnlProxyRegistrationJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlProxyRegistrationResponseFormat.setInclude("NON_NULL");
		jsonChnlProxyRegistrationResponseFormat.setInclude("NON_EMPTY");
		jsonChnlProxyResolutionResponseFormat.setInclude("NON_NULL");
		jsonChnlProxyResolutionResponseFormat.setInclude("NON_EMPTY");

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		faultJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		faultJDF.setInclude("NON_NULL");
		faultJDF.setInclude("NON_EMPTY");
		faultJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

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
					+ " error_msg= :#${body.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(faultJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
	    	.handled(true)
    	;

		// Untuk Proses Proxy Registration Request

		from("direct:proxyregistration").routeId("proxyregistration")

//			.marshal(chnlProxyRegistrationJDF)
//			.log("${body}")
//			.unmarshal(chnlProxyRegistrationJDF)
//			.log("${body.orignReffId}")
			
			.setHeader("hdr_errorlocation", constant("PrxyRoute/InputValidation"))
			.log("Channelnya ${body.channel}")
			.process(validatePrxyRegsProcessor)

			.setHeader("hdr_errorlocation", constant("PrxyRoute/BuildRegistratinMsg"))
			.process(proxyRegistrationRequestProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			.log("${body}")

			.setHeader("hdr_errorlocation", constant("PrxyRoute/saveTabelInit"))
			.to("seda:savePrxytables")


			// kirim ke CI-HUB
			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_errorlocation", constant("PrxyRoute/sendCIHub"))
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:{{bifast.ciconnector-url}}?"
					+ "socketTimeout={{bifast.timeout}}&" 
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
			.marshal(jsonChnlProxyRegistrationResponseFormat)
			
			// save audit tables
			.setHeader("hdr_errorlocation", constant("PrxyRoute/updateTable"))
			.to("seda:savePrxytables?exchangePattern=InOnly")

			
		;	

		
//		from("direct:proxyresolution").routeId("proxyresolution")
//			
//			.process(proxyResolutionRequestProcessor)
//			.setHeader("req_objbi", simple("${body}"))
//			.marshal(jsonBusinessMessageFormat)
//
//			.setHeader("hdr_fullrequestmessage", simple("${body}"))
//			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
//
//			// kirim ke CI-HUB
//			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{bifast.ciconnector-url}}?"
//					+ "socketTimeout={{bifast.timeout}}&" 
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
//			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
//			.setHeader("hdr_fullresponsemessage", simple("${body}"))
//
//			.unmarshal(jsonBusinessMessageFormat)
//			.setHeader("resp_objbi", simple("${body}"))	
//					
//			// prepare untuk response ke channel
//			.process(proxyResolutionResponseProcessor)
//			.setHeader("resp_channel", simple("${body}"))
//			.marshal(jsonChnlProxyResolutionResponseFormat)
//
//		;


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

