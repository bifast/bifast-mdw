package bifast.outbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.processor.FaultResponseProcessor;
import bifast.outbound.processor.SaveTableChannelProcessor;
import bifast.outbound.processor.ValidateAndTransformProcessor;

@Component
public class ServiceEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private FaultResponseProcessor faultProcessor;
	@Autowired
	private ValidateAndTransformProcessor validateInputProcessor;
	@Autowired
	private SaveTableChannelProcessor saveChannelRequestProcessor;


	JacksonDataFormat chnlRequestJDF = new JacksonDataFormat(RequestMessageWrapper.class);
	JacksonDataFormat jdfChnlRequestNoWr = new JacksonDataFormat(RequestMessageWrapper.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		jdfChnlRequestNoWr.setInclude("NON_NULL");
		jdfChnlRequestNoWr.setInclude("NON_EMPTY");
		jdfChnlRequestNoWr.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
//		jdfChnlRequestNoWr.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		chnlRequestJDF.setInclude("NON_NULL");
		chnlRequestJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setPrettyPrint(true);

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
//			.removeHeaders("ps_*")
			.removeHeaders("ae_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
    	;

		restConfiguration().component("servlet");
		
		rest("/")

			.post("/service")
				.consumes("application/json")
				.to("direct:service")

		;

		
		from("direct:service").routeId("komi.endpointRoute")
			.convertBodyTo(String.class)
		
			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			
//			.log("${body}")
			.unmarshal(chnlRequestJDF)

			.setHeader("hdr_errorlocation", constant("EndpointRoute/checkChannelRequest"))
			.process(checkChannelRequest)		// produce header hdr_msgType,hdr_channelRequest
			
			.setHeader("hdr_channelRequest", simple("${body}"))
			
			.process(validateInputProcessor)
			
			.process(saveChannelRequestProcessor)

			.log("di serviceendpoint komitrxid: ${header.hdr_request_list.komiTrxId}" )

			.choice()
				.when().simple("${header.hdr_msgType} == 'AEReq'")
					.log("[ChnlReq:${header.hdr_chnlRefId}] Account Enquiry Request start.")
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_msgType} == 'CTReq'")
					.log("[ChnlReq:${header.hdr_chnlRefId}] Credit Transfer Request start.")
					.to("direct:ct_aepass")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChnlReq:${header.hdr_chnlRefId}][PREG] start.")
					.to("direct:prxyrgst")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChnlReq:${header.hdr_chnlRefId}][PRES] start.")
					.to("direct:proxyresolution")

			.end()

			.marshal(chnlResponseJDF)
			
			.choice()
					
				.when().simple("${header.hdr_msgType} == 'AEReq'")
					.log("[ChnlReq:${header.hdr_chnlRefId}] Account Enquiry Request finish.")

				.when().simple("${header.hdr_msgType} == 'CTReq'")
					.log("[ChnlReq:${header.hdr_chnlRefId}] Credit Transfer Request finish.")
	
//				.when().simple("${header.hdr_msgType} == 'pymtsts'")
//					.log("[ChRefId:${header.hdr_chnlRefId}][PS] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("[ChnlReq:${header.hdr_chnlRefId}][PREG] finish.")
	
				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("[ChnlReq:${header.hdr_chnlRefId}][PRES] finish.")
	
			.end()

			.process(saveChannelRequestProcessor)

			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeader("HttpMethod")
			.removeHeader("cookie")
		;
		

	}

}
