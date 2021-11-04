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
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class CorebankRoute extends RouteBuilder{
	@Autowired private CbMockResponse cbMock;
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlAccountEnquiryJDF = jdfService.basic(CbAccountEnquiryRequestPojo.class);
//		JacksonDataFormat chnlDebitResponseJDF = jdfService.basic(CbDebitResponsePojo.class)
//		JacksonDataFormat accountCustomerInfoRequestJDF = jdfService.basic(CbAccountCustomerInfoRequestPojo.class);
//		JacksonDataFormat accountCustomerInfoResponseJDF = jdfService.basic(CbAccountCustomerInfoResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:callcb").routeId("komi.cb.corebank")
		

			.choice()
				.when().simple("${body.class} endsWith 'CbAccountEnquiryRequestPojo'")
					.setHeader("cb_requestName", constant("account-enquiry"))
					.marshal(chnlAccountEnquiryJDF)
					.log("${body}")
			.end()
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChReq:${header.hdr_request_list.requestId}]"
	 														+ " CB Request: ${body}")
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{komi.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
//	 		.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] CB Response: ${body}")
			
			.process(cbMock)
			

			.removeHeaders("cb_*")
		;
		
	}

}
