package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import bifast.outbound.corebank.pojo.CBDebitInstructionRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.processor.EnrichmentAggregator;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private MockCBResponseProcessor mockCBResponse;
	@Autowired
	private SaveCBTableProcessor saveCBTableProcessor;

	JacksonDataFormat chnlDebitRequestJDF = new JacksonDataFormat(CBDebitInstructionRequestPojo.class);
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);
	JacksonDataFormat cbInstructionWrapper = new JacksonDataFormat(CBInstructionWrapper.class);

	@Override
	public void configure() throws Exception {
		
		chnlDebitRequestJDF.setInclude("NON_NULL");
		chnlDebitRequestJDF.setInclude("NON_EMPTY");
		chnlDebitRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		chnlDebitRequestJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");
		
		cbInstructionWrapper.setInclude("NON_NULL");
		cbInstructionWrapper.setInclude("NON_EMPTY");
		cbInstructionWrapper.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		from("direct:callcb").routeId("komi.cb.corebank")
			.log("[ChReq:${header.hdr_chnlRefId}][CB] started")
			
			.setHeader("cb_request", simple("${body}"))
			
			.setHeader("hdr_errorlocation", constant("corebank service call"))		

			.marshal(cbInstructionWrapper)
	 		.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChReq:${header.hdr_chnlRefId}][CB] CB Request: ${body}")
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{komi.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
//	 		.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] CB Response: ${body}")
			
	 		.unmarshal(cbInstructionWrapper)
			.process(mockCBResponse)

//			.choice()
//				.when().simple("${body.status} != 'SUCCESS'")
//					.setHeader("hdr_error_status", constant("REJECT-CB"))
//					.setHeader("hdr_error_mesg", simple("${body.addtInfo}"))
//			.end()
			
			.to("seda:savecbtable?exchangePattern=InOnly")

			.log("[ChReq:${header.hdr_chnlRefId}][CB] finish")

			.removeHeaders("cb_*")
		;
		
		from("seda:savecbtable").routeId("komi.cb.save_logtable")
			.process(saveCBTableProcessor)
		;
	}

}
