package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CbCustomerInfoRequestPojo;
import bifast.outbound.corebank.pojo.CbCustomerInfoResponsePojo;
import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitResponsePojo;
import bifast.outbound.corebank.pojo.CbDebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.CbDebitReversalResponsePojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired
	private SaveCBTableProcessor saveCBTableProcessor;
	@Autowired
	private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat debitRequestJDF = jdfService.wrapRoot(CbDebitRequestPojo.class);
		JacksonDataFormat debitResponseJDF = jdfService.basic(CbDebitResponsePojo.class);
		JacksonDataFormat debitReversalRequestJDF = jdfService.wrapRoot(CbDebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(CbDebitReversalResponsePojo.class);
		JacksonDataFormat customerInfoRequestJDF = jdfService.wrapRoot(CbCustomerInfoRequestPojo.class);
		JacksonDataFormat customerInfoResponseJDF = jdfService.basic(CbCustomerInfoResponsePojo.class);

		// ROUTE CALLCB 
		from("seda:callcb").routeId("komi.corebank")
		
			.log("[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] call Corebank")

			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					Object req = exchange.getMessage().getBody(Object.class);
					rmw.setCorebankRequest(req);
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
			})
					
			.log("${body.class}")
			
			.choice()
				.when().simple("${body.class} endsWith 'CbDebitRequestPojo'")
					.setHeader("cb_requestName", constant("debit"))
					.marshal(debitRequestJDF)
				.when().simple("${body.class} endsWith 'CbCustomerInfoRequestPojo'")
					.setHeader("cb_requestName", constant("emailphonelist"))
					.marshal(customerInfoRequestJDF)
				.when().simple("${body.class} endsWith 'CbDebitReversalRequestPojo'")
					.setHeader("cb_requestName", constant("debitreversal"))
					.marshal(debitReversalRequestJDF)
			.end()
			
	 		.log(LoggingLevel.DEBUG, "komi.corebank", "[ChReq:${header.hdr_request_list.requestId}]"
	 														+ " CB Request: ${body}")

			.setHeader("HttpMethod", constant("POST"))
			.enrich()
				.simple("http://{{komi.url.corebank}}?"
					+ "socketTimeout=5000&" 
					+ "bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

			.convertBodyTo(String.class)
			
//			.process(mockResponse)
	 		.log(LoggingLevel.DEBUG, "komi.corebank", "[ChReq:${header.hdr_request_list.requestId}]"
	 														+ " CB Response: ${body}")

			.choice()
				.when().simple("${header.cb_requestName} == 'debit'")
					.unmarshal(debitResponseJDF)
				.when().simple("${header.cb_requestName} ==  'emailphonelist'")
					.unmarshal(customerInfoResponseJDF)
				.when().simple("${header.cb_requestName} ==  'debitreversal'")
					.unmarshal(debitReversalResponseJDF)
			.end()
				
			.choice()
				.when().simple("${body.status} == 'RJCT'")
			 		.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							CbDebitResponsePojo response = exchange.getMessage().getBody(CbDebitResponsePojo.class);
							ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
							FaultPojo fault = new FaultPojo();
							fault.setCallStatus("REJECT-CB");
							fault.setResponseCode(response.getStatus());
							fault.setReasonCode(response.getReason());
							rmc.setCorebankResponse(fault);
							rmc.setFault(fault);
							exchange.getMessage().setHeader("hdr_response_list", rmc);
							exchange.getMessage().setBody(fault);
						}
			 		})
		 		.endChoice()
		 		
		 		.otherwise()
			 		.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							Object response = exchange.getMessage().getBody(Object.class);
							ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
							rmc.setCorebankResponse(response);
							exchange.getMessage().setHeader("hdr_response_list", rmc);
							}
			 			})
		 		.endChoice()

	 		.end()
			
//			.to("seda:savecbtable?exchangePattern=InOnly")


			.removeHeaders("cb_*")
		;
		
		from("seda:savecbtable").routeId("komi.cb.save_logtable")
			.process(saveCBTableProcessor)
		;
	}

}
