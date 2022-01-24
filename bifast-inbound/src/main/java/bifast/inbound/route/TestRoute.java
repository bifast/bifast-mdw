package bifast.inbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.AccountEnquiryResponseProcessor;
import bifast.inbound.accountenquiry.BuildAERequestForCbProcessor;
import bifast.inbound.corebank.CbCallFaultProcessor;
import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.processor.CheckRequestMsgProcessor;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class TestRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CheckRequestMsgProcessor checkRequestMsgProcessor;
	@Autowired private BuildAERequestForCbProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private AccountEnquiryResponseProcessor aeResponseProcessor;
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat jsonBusinessMessageDataFormat = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat accountEnquiryJDF = jdfService.wrapRoot(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);


		from("direct:testae")
			.convertBodyTo(String.class)
		
			.setHeader("hdr_inputformat", constant("json"))

			.log(LoggingLevel.DEBUG,"komi.jsonEndpoint", "-------****------")
			.log(LoggingLevel.DEBUG,"komi.jsonEndpoint", "Terima: ${body}")


			// simpan msg inbound compressed
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_frBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))
			
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("hdr_frBIobj", simple("${body}"))   // pojo BusinessMessage simpan ke header
		
			// dari InboundRoute
			.process(checkRequestMsgProcessor) 
			.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] received.")

			// dari AccountEnquiryRoute
			.setHeader("ae_obj_birequest", simple("${body}"))
			
			// prepare untuk request ke corebank
			.process(buildAccountEnquiryRequestProcessor)
	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Akan call AE corebank")

//			.to("seda:callcb")
			.delay(simple("{{test.delay}}"))

	 		// CorebankRoute
			.log(LoggingLevel.DEBUG,"komi.corebank", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Terima di corebank: ${body}")
			.setHeader("cb_requestName", constant("accountinquiry"))
			.marshal(accountEnquiryJDF)
	 		.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}]"
						+ " CB Request: ${body}")

	 		.removeHeaders("*")
	 		.stop()
	 		
	 		.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					CbAccountEnquiryResponsePojo aeResponse = new CbAccountEnquiryResponsePojo();
					aeResponse.setAccountNumber(null);
				}
	 		})
			.unmarshal(accountEnquiryResponseJDF)

//	 		.doTry()
//				.setHeader("HttpMethod", constant("POST"))
//				.enrich()
//					.simple("{{komi.url.corebank}}?"
//						+ "socketTimeout=10000&" 
//						+ "bridgeEndpoint=true")
//					.aggregationStrategy(enrichmentAggregator)
//				.convertBodyTo(String.class)
//		 		.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] CB Response: ${body}")
//
// 				.unmarshal(accountEnquiryResponseJDF)
//
//	 		.endDoTry()
//	    	.doCatch(Exception.class)
//				.log(LoggingLevel.ERROR, "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Call CB Error.")
//		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//		    	.process(cbFaultProcessor)
//	    	.end()

	 		
	 		
	 		// kembali ke AERoute
	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] selesai call AE corebank")
			.process(aeResponseProcessor)

			.removeHeaders("ae_*")

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
