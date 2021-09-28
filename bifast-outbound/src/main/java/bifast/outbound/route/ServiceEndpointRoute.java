package bifast.outbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelRequestWrapper;
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


	JacksonDataFormat chnlRequestJDF = new JacksonDataFormat(ChannelRequestWrapper.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		chnlRequestJDF.setInclude("NON_NULL");
		chnlRequestJDF.setInclude("NON_EMPTY");
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

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

			.post("/service")
				.consumes("application/json")
				.to("direct:service")

		;

		
		from("direct:service").routeId("komi.endpointRoute")
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
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.log("[ChRefId:${header.hdr_chnlRefId}][CT] start.")
					.to("direct:ctreq")

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

			.marshal(chnlResponseJDF)
			
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
		

	}

}
