package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.FaultResponseProcessor;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private MockCBResponseProcessor mockCBResponse;
	@Autowired
	private FaultResponseProcessor faultProcessor;
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
//			.setHeader("hdr_cbresponse", simple("${body}"))

			.choice()
				.when().simple("${body.cbDebitInstructionResponse.status} != 'SUCCESS'")
					.setHeader("hdr_error_status", constant("REJECT-CB"))
					.setHeader("hdr_error_mesg", simple("${body.cbDebitInstructionResponse.addtInfo}"))
//					.setHeader("hdr_error_mesg", constant("Transaksi CB gagal"))
			.end()
			
			.to("seda:savecbtable?exchangePattern=InOnly")

			.log("[ChRefId:${header.hdr_chnlRefId}][Corebank] finish")

			.removeHeaders("cb_*")
		;
		
		from("seda:savecbtable").routeId("save_cb_trns")
			.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] Akan save table.")
			.process(saveCBTableProcessor)
			.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] corebank table saved.")
		;
	}

}
