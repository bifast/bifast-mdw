package bifast.inbound.corebank;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class CorebankRoute extends RouteBuilder{
	@Autowired private CbMockResponse cbMock;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private SaveCorebankTransactionProcessor saveCbProcessor;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlAccountEnquiryJDF = jdfService.basic(CbAccountEnquiryRequestPojo.class);
		JacksonDataFormat cbAccountEnquiryResponseJDF = jdfService.basic(CbAccountEnquiryResponsePojo.class);
//		JacksonDataFormat chnlDebitResponseJDF = jdfService.basic(CbDebitResponsePojo.class)
//		JacksonDataFormat accountCustomerInfoRequestJDF = jdfService.basic(CbAccountCustomerInfoRequestPojo.class);
//		JacksonDataFormat accountCustomerInfoResponseJDF = jdfService.basic(CbAccountCustomerInfoResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:callcb").routeId("komi.cb.corebank")
		

			.choice()
				.when().simple("${body.class} endsWith 'CbAccountEnquiryRequestPojo'")
					.setHeader("cb_requestName", constant("accountinquiry"))
					.marshal(chnlAccountEnquiryJDF)
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
	 				.unmarshal(cbAccountEnquiryResponseJDF)
	 				.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							CbAccountEnquiryResponsePojo cbResponse = exchange.getMessage().getBody(CbAccountEnquiryResponsePojo.class);
							ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
							processData.setCorebankResponse(cbResponse);
							exchange.getMessage().setHeader("hdr_process_data", processData);
						}
	 				})
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
