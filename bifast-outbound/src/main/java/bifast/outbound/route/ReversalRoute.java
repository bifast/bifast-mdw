package bifast.outbound.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;
import bifast.outbound.reversect.ReverseCreditTrnRequestProcessor;
import bifast.outbound.reversect.ReverseCreditTrnResponseProcessor;

@Component
public class ReversalRoute extends RouteBuilder {

	@Autowired
	private ReverseCreditTrnRequestProcessor reverseCTRequestProcessor;
	@Autowired
	private ReverseCreditTrnResponseProcessor reverseCTResponseProcessor;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	JacksonDataFormat jsonChnlReverseCTRequestFormat = new JacksonDataFormat(ChannelReverseCreditTransferRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	@Override
	public void configure() throws Exception {

		jsonChnlReverseCTRequestFormat.setInclude("NON_NULL");
		jsonChnlReverseCTRequestFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		from("sql:select * "
				+ "from CREDIT_TRANSFER ct "
				+ "where ct.reversal = 'PENDING'?"
					+ "outputType=SelectList&"
					+ "outputHeader=rcv_qryresult&"
					+ "maxMessagesPerPoll=3")
			.routeId("QueryReversal")

			.log("${header.req_qryresult}")
//			.when().simple("${header.rcv_qryresult[crdtStatus]} == 'ACTC'")

			.setHeader("req_channelRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.process(reverseCTRequestProcessor)
			
//			.removeHeaders("req_*")
		;


		// Untuk Proses Reverse Credit Transfer
//		from("direct:reversect")
			
//		setHeader("rcv_msgType", "ReverseCreditTransfer");
//		setHeader("rcv_channel", req.getReverseCreditTransferRequest());

//			.setHeader("log_filename", simple("prxyrgst.${header.rcv_channel.intrnRefId}.arch"))
//
//			.convertBodyTo(String.class)
//			.unmarshal(jsonChnlReverseCTRequestFormat)
//			.setHeader("req_channelReq",simple("${body}"))
//			.setHeader("req_msgType", constant("ReverseCreditTransfer"))
			
			// convert channel request jadi pacs008 message
//			.process(reverseCTRequestProcessor)
//			.setHeader("req_objbi", simple("${body}"))
//			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
//			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{bifast.ciconnector-url}}?"
//					+ "socketTimeout={{bifast.timeout}}&" 
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
//			
//			.unmarshal(jsonBusinessMessageFormat)
//			.setHeader("resp_objbi", simple("${body}"))	
//
//			.process(reverseCTResponseProcessor)
//			.setHeader("resp_channel", simple("${body}"))
//
//			.setExchangePattern(ExchangePattern.InOnly)
//			.to("seda:endlog")
//			
//			.setBody(simple("${header.resp_channel}"))
//			.marshal(jsonChnlResponseFormat)
//			.removeHeaders("resp_*")
//			.removeHeaders("req_*")
//		;
		
	}

}
