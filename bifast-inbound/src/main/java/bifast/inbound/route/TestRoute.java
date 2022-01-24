package bifast.inbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.IsoAERequestPrc;
import bifast.inbound.accountenquiry.IsoAEResponsePrc;
import bifast.inbound.corebank.CbCallFaultProcessor;
import bifast.inbound.corebank.isopojo.AccountEnquiryInboundRequest;
import bifast.inbound.corebank.isopojo.AccountEnquiryInboundResponse;
import bifast.inbound.processor.CheckRequestMsgProcessor;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class TestRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CheckRequestMsgProcessor checkRequestMsgProcessor;
	@Autowired private IsoAEResponsePrc aeResponseProcessor;
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private IsoAERequestPrc isoAERequestPrc;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat jsonBusinessMessageDataFormat = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat accountEnquiryReqJDF = jdfService.basic(AccountEnquiryInboundRequest.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(AccountEnquiryInboundResponse.class);


		from("direct:testae")
			.convertBodyTo(String.class)
			
			.log(LoggingLevel.DEBUG,"komi.jsonEndpoint", "-------****------")
			.log(LoggingLevel.DEBUG,"komi.jsonEndpoint", "Terima: ${body}")

			// simpan msg inbound compressed
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_frBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))
			.removeHeader("hdr_tmp")
			
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("hdr_frBIobj", simple("${body}"))   // pojo BusinessMessage simpan ke header
		
			// dari InboundRoute
			.process(checkRequestMsgProcessor) 
			.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] received.")

			// dari AccountEnquiryRoute
			.setHeader("ae_obj_birequest", simple("${body}"))
			
			// prepare untuk request ke corebank
			.process(isoAERequestPrc)
	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Akan call AE corebank")

//			.to("seda:callcb")
//			.delay(simple("{{test.delay}}"))

	 		// CorebankRoute
			.log(LoggingLevel.DEBUG,"komi.corebank", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Terima di corebank: ${body}")
			.setHeader("cb_requestName", constant("accountinquiry"))
			.marshal(accountEnquiryReqJDF)
	 		.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}]"
						+ " CB Request: ${body}")

			.setProperty("bkp_hdr_process_data").header("hdr_process_data")

	 		.removeHeaders("*")
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))

				.log("${headers}")
				.enrich("http://10.11.100.116:9003/komi/api/v1/adapter/accountinquiry?"
						+ "socketTimeout=10000&" 
//						+ "copyHeaders=false&"
						+ "bridgeEndpoint=true", enrichmentAggregator)
//					.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
		 		.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] CB Response: ${body}")

 				.unmarshal(accountEnquiryResponseJDF)

	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

			.setHeader("hdr_process_data", exchangeProperty("bkp_hdr_process_data"))

//	 		.removeHeaders("*")
//	 		.stop()
	 		
	 		// kembali ke AERoute
	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] selesai call AE corebank")
			.process(aeResponseProcessor)

			// kembali ke InboundRoute
			.setHeader("hdr_tmpbody", simple("${body}"))
			.to("seda:portalnotif?exchangePattern=InOnly")
			.setBody(simple("${header.hdr_tmpbody}"))

			// kembalik JsonRoute
			.marshal(jsonBusinessMessageDataFormat) 
			.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Response: ${body}")
			.setHeader("hdr_tmp", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))

			.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] completed.")
			.removeHeaders("*")
		;
	}

}
