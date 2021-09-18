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
import bifast.outbound.processor.FaultProcessor;
import bifast.outbound.processor.SaveTableChannelProcessor;
import bifast.outbound.processor.ValidateInputProcessor;

@Component
public class ServiceEndpointRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	@Autowired
	private FaultProcessor faultProcessor;
	@Autowired
	private ValidateInputProcessor validateInputProcessor;
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
        	.log("Fault di EndpointRoute, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-KM', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
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
		
		rest("/komi")

			.post("/outbound")
				.consumes("application/json")
				.to("direct:outbound")

			.post("/history")
				.consumes("application/json")
				.to("direct:aa")
		;

		from("direct:outbound").routeId("OutboundRoute")
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
					.log("Account Enquiry")
					.to("direct:acctenqr")
					
				.when().simple("${header.hdr_msgType} == 'crdttrns'")
					.to("direct:ctreq")

				.when().simple("${header.hdr_msgType} == 'ficrdttrns'")
					.to("direct:fictreq")

				.when().simple("${header.hdr_msgType} == 'pymtsts'")
					.log("Payment Status Request")
					.to("direct:paymentstatus")

				.when().simple("${header.hdr_msgType} == 'reversect'")
					.to("direct:ctreq")

				.when().simple("${header.hdr_msgType} == 'prxyrgst'")
					.log("Proxy Registration")
					.to("direct:proxyregistration")

				.when().simple("${header.hdr_msgType} == 'prxyrslt'")
					.log("Proxy Resolution")
					.to("direct:proxyresolution")

			.end()

			.marshal(chnlResponseJDF)

			.log("Proses ${header.hdr_msgType} selesai")
			.removeHeaders("req*")
			.removeHeaders("resp_*")
			.removeHeaders("hdr_*")
			.removeHeader("HttpMethod")

		;
	
	}

}
