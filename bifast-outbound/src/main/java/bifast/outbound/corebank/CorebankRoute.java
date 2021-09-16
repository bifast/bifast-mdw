package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultProcessor;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private MockCBDebitResponseProcessor mockDebitResponse;
	@Autowired
	private MockCBFIResponseProcessor mockFIResponse;
	@Autowired
	private FaultProcessor faultProcessor;

	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);
	JacksonDataFormat cbInstructionWrapper = new JacksonDataFormat(CBInstructionWrapper.class);
	JacksonDataFormat cbDebitResponseJDF = new JacksonDataFormat(CBDebitInstructionResponsePojo.class);

	@Override
	public void configure() throws Exception {
		
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		cbInstructionWrapper.setInclude("NON_NULL");
		cbInstructionWrapper.setInclude("NON_EMPTY");
		
		cbDebitResponseJDF.setInclude("NON_NULL");
		cbDebitResponseJDF.setInclude("NON_EMPTY");
		cbDebitResponseJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		onException(Exception.class) 
			.log("dalam route callingCB")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-CB', "
					+ " error_msg= :#${body.faultResponse.reason} "
//					+ " error_msg= 'Error terkait service call ke CB' "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)

			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ct_*")
			.removeHeader("HttpMethod")
			.handled(true);
		;

		from("direct:callcb").routeId("callingCB")
			.log("Akan call corebank system")
			
			.log("${body}")
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{bifast.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
			
//			.process(mockFIResponse)
			.process(mockDebitResponse)
			.marshal(cbDebitResponseJDF)
			.log("${body}")

		;
	}

}
