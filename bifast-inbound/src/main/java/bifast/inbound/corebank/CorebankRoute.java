package bifast.inbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.inbound.settlement.CbSettlementRequestPojo;


@Component
public class CorebankRoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private SaveCorebankTransactionProcessor saveCbProcessor;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat accountEnquiryJDF = jdfService.basic(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat accountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);
		JacksonDataFormat creditJDF = jdfService.basic(CbCreditRequestPojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
		JacksonDataFormat settlementJDF = jdfService.basic(CbSettlementRequestPojo.class);

		// ROUTE CALLCB 
		from("seda:callcb").routeId("komi.cb.corebank")
		
			.choice()
				.when().simple("${body.class} endsWith 'CbAccountEnquiryRequestPojo'")
					.setHeader("cb_requestName", constant("accountinquiry"))
					.marshal(accountEnquiryJDF)

				.when().simple("${body.class} endsWith 'CbCreditRequestPojo'")
					.setHeader("cb_requestName", constant("credit"))
					.marshal(creditJDF)

				.when().simple("${body.class} endsWith 'CbSettlementRequestPojo'")
					.setHeader("cb_requestName", constant("settlement"))
					.marshal(settlementJDF)
			.end()
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChReq:${header.hdr_request_list.requestId}]"
	 														+ " CB Request: ${body}")
			.setHeader("HttpMethod", constant("POST"))
			.enrich()
				.simple("http://{{komi.url.corebank}}/${header.cb_requestName}?"
					+ "socketTimeout=5000&" 
					+ "bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

	 		.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] CB Response: ${body}")

	 		
	 		.choice()
	 			.when().simple("${header.cb_requestName} == 'accountinquiry'")
	 				.unmarshal(accountEnquiryResponseJDF)
	 				.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							CbAccountEnquiryResponsePojo cbResponse = exchange.getMessage().getBody(CbAccountEnquiryResponsePojo.class);
							ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
							processData.setCorebankResponse(cbResponse);
							exchange.getMessage().setHeader("hdr_process_data", processData);
						}
	 				})
	 			.endChoice()

	 			.when().simple("${header.cb_requestName} == 'credit'")
 					.unmarshal(creditResponseJDF)
	 			.endChoice()

	 		.end()
	 		
	 		.to("seda:savecb")
			.removeHeaders("cb_*")
		;
		
		
		from("seda:savecb")
			.process(saveCbProcessor)
		;
	}

}
