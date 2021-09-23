package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelRequestWrapper;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FlatMessageWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultResponseProcessor;
import bifast.outbound.processor.SaveTableChannelProcessor;
import bifast.outbound.processor.ValidateAndTransformProcessor;
import bifast.outbound.processor.ValidateProcessor;

@Component
public class RouterEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private FaultResponseProcessor faultProcessor;
	@Autowired
	private ValidateProcessor validateInputProcessor;
	@Autowired
	private SaveTableChannelProcessor saveChannelRequestProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;


	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);
	JacksonDataFormat chnlRequestJDF = new JacksonDataFormat(ChannelRequestWrapper.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);
	JacksonDataFormat flatResponseJDF = new JacksonDataFormat(FlatMessageWrapper.class);

	@Override
	public void configure() throws Exception {

		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		chnlRequestJDF.setInclude("NON_NULL");
		chnlRequestJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

		flatResponseJDF.setInclude("NON_NULL");
		flatResponseJDF.setInclude("NON_EMPTY");
		flatResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

        onException(Exception.class).routeId("Generic Exception Handler")
        	.log(LoggingLevel.ERROR, "Fault di EndpointRoute, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.log("chl_tab_id : ${header.hdr_chnlRefId}")
			.to("sql:update channel_transaction set status = 'ERROR-KM'"
					+ ", error_msg= :#${body.faultResponse.reason} " 
					+ "  where id= :#${header.hdr_chnlTable_id}  ")
			.marshal(chnlResponseJDF)
			.removeHeaders("req_*")
			.removeHeaders("hdr_*")
			.removeHeaders("fict_*")
			.removeHeaders("ps_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
    	;

		restConfiguration().component("servlet");
		
		rest("/")

			.post("/routing")
				.consumes("application/json")
				.to("direct:router")

		;

		
		from("direct:router").routeId("komi.direct:router")
			.convertBodyTo(String.class)

			.setHeader("hdr_errorlocation", constant("EndpointRoute"))
					
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.unmarshal(chnlRequestJDF)


			.setHeader("hdr_errorlocation", constant("EndpointRoute/checkChannelRequest"))
			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			.setHeader("hdr_channelRequest", simple("${body}"))
			
			.process(validateInputProcessor)
			
			.process(saveChannelRequestProcessor)

			.choice()
				.when().simple("${header.hdr_msgType} == 'acctenqr'")
					.log("[ChRefId:${header.hdr_chnlRefId}][AE] start.")
					.to("direct:acctenqrflt")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][CT] start.")
					.to("direct:flatctreq")

				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][FICT] start.")
					.to("direct:fictreq")

				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PS] start.")
					.to("direct:ps4chnl")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PREG] start.")
					.to("direct:prxyrgst")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PRES] start.")
					.to("direct:proxyresolution")

			.end()

			.log("${body}")
			.marshal(flatResponseJDF)
			
			
			.choice()
					
				.when().simple("${header.hdr_msgType} == 'acctenqr'")
					.log("[ChRefId:${header.hdr_chnlRefId}][AE] finish.")

				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][CT] finish.")
	
				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][FICT] finish.")
	
				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PS] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PREG] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChRefId:${header.hdr_chnlRefId}][PRES] finish.")
	
			.end()

			.process(saveChannelRequestProcessor)

			.removeHeaders("req_*")
			.removeHeaders("hdr_*")
			.removeHeaders("fict_*")
			.removeHeaders("ps_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")

		;
		

		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-cihub").routeId("komi.call-cihub")
			.marshal(businessMessageJDF)
			.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChRefId:${header.hdr_chnlRefId}] Post CI-HUB request: ${body}")
	
			.setHeader("tmp_body", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_encr_request", simple("${body}"))
			.setBody(simple("${header.tmp_body}"))
			
			.setHeader("hdr_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
	
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.ciconnector-url}}?"
						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)				
				.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChRefId:${header.hdr_chnlRefId}] CI-HUB response: ${body}")
	
				.setHeader("tmp_body", simple("${body}"))
				.marshal().zipDeflater()
				.marshal().base64()
				.setHeader("hdr_encr_response", simple("${body}"))
				.setBody(simple("${header.tmp_body}"))
	
				.unmarshal(businessMessageJDF)
				.setHeader("hdr_error_status", constant(null))
	
			.doCatch(SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}] Call CI-HUB Timeout")
				.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
				.setHeader("hdr_error_mesg", constant("Timeout menunggu response dari CIHUB"))
		    	.setBody(constant(null))
	
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[ChRefId:${header.hdr_chnlRefId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setHeader("hdr_error_status", constant("ERROR-CIHUB"))
				.setHeader("hdr_error_mesg", simple("${exception.message}"))
		    	.setBody(constant(null))
	
			.endDoTry()
			.end()
	
			.setHeader("hdr_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
			.removeHeaders("tmp_*")
		;

	
	}

}
