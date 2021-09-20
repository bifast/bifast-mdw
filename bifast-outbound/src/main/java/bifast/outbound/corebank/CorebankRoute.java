package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	@Autowired
	private SaveCBTableProcessor saveCBTableProcessor;

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
			.log("[ChRefId:${header.hdr_chnlRefId}][Corebank] started")
				
			.setHeader("cb_request", simple("${body}"))
			
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{komi.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
			
			.process(mockCBResponse)
			.setHeader("cb_response", simple("${body}"))

			.to("seda:savecbtable?exchangePattern=InOnly")

			.log("[ChRefId:${header.hdr_chnlRefId}][Corebank] finish")

			.removeHeaders("cb_*")
		;
		
		from("seda:savecbtable").routeId("save_cb_trns")
			.log("Akan save table")
			.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] Akan save table.")
			.process(saveCBTableProcessor)
			.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] corebank table saved.")
		;
	}

}
