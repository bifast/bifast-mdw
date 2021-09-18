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
	private MockCBResponseProcessor mockCBResponse;
	@Autowired
	private FaultProcessor faultProcessor;

	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);
	JacksonDataFormat cbInstructionWrapper = new JacksonDataFormat(CBInstructionWrapper.class);

	@Override
	public void configure() throws Exception {
		
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		
		cbInstructionWrapper.setInclude("NON_NULL");
		cbInstructionWrapper.setInclude("NON_EMPTY");

		onException(Exception.class) 
			.log("ERROR route callingCB")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.to("sql:update outbound_message set resp_status = 'ERROR-CB', "
					+ " error_msg= :#${body.faultResponse.reason} "
					+ "where id= :#${header.hdr_idtable}  ")
			.marshal(chnlResponseJDF)

			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("ct_*")
			.removeHeader("HttpMethod")
			.handled(true);
		;

		from("direct:callcb").routeId("callingCB")
			.log("[ChRefId:${header.hdr_chnl_req_id}] [callingCB] started")
			
			.log("${body}")
			
			.unmarshal(cbInstructionWrapper)
			
			.log("tidak error")
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{bifast.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
			
			.process(mockCBResponse)
			.marshal(cbInstructionWrapper)
			.log("output CB: ${body}")
			.unmarshal(cbInstructionWrapper)

		;
	}

}
