package bifast.outbound.corebank;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.AccountCustInfoRequestDTO;
import bifast.outbound.corebank.pojo.AccountCustInfoResponseDTO;
import bifast.outbound.corebank.processor.CbCallFaultProcessor;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.StatusReasonRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class AccountInfoRoute extends RouteBuilder{
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private StatusReasonRepository reasonRepo;
	
	@SuppressWarnings("deprecation")
	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestDTO.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponseDTO.class);

//		onException(Exception.class)
//			.maximumRedeliveries(1)
//			.log(LoggingLevel.ERROR, "onException!")
//			.handled(true)
//			.choice()
//				.when().simple("${exchangeProperty.CamelExceptionCaught.statusCode} == '504'")
//					.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${header.cb_e2eid}] Call CB Timeout(504).")
//				.otherwise()
//					.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${header.cb_e2eid}] Call CB Error.")
//					.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//			.end()
//	    	.process(cbFaultProcessor)
//		;

		
		// ROUTE CALLCB 
		from("direct:accinfo").routeId("komi.cb.accinfo")
//			.errorHandler(defaultErrorHandler().maximumRedeliveries(1))
			.removeHeaders("*")

			.log(LoggingLevel.DEBUG,"komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Terima di corebank: ${body}")
					
			.marshal(aciRequestJDF)
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.accinfo", "[${exchangeProperty.prop_request_list.msgName}:"
	 				+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.customerinfo}}")
	 		.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CB Request: ${body}")
			
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("{{komi.url.isoadapter.customerinfo}}?bridgeEndpoint=true")
				.aggregationStrategy(enrichmentAggregator)

				.convertBodyTo(String.class)
				
				.log("[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] CB response: ${body}")

 				.unmarshal(aciResponseJDF)

 				.setProperty("pr_response", simple("${body.status}"))
 				.setProperty("pr_reason", simple("${body.reason}"))
 			
				.filter().simple("${exchangeProperty.pr_response} != 'ACTC' ")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							String cbResponse = exchange.getProperty("pr_response", String.class);
							String cbReason = exchange.getProperty("pr_reason", String.class);
							FaultPojo fault = new FaultPojo("CB-RJCT", cbResponse, cbReason);	
							StatusReason sr = reasonRepo.findById(cbReason).orElse(new StatusReason());
							fault.setErrorMessage(cbReason + ":" + sr.getDescription());
							exchange.getMessage().setBody(fault);
							ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class);
							respColl.setFault(fault);
						}
					})
				.end()
				
	 		.endDoTry()
	    	.doCatch(org.apache.camel.http.common.HttpOperationFailedException.class).onWhen(simple("${exception.statusCode} == '504'"))
	    		.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Corebank TIMEOUT: \n ${exception.message}")
	    		.process(cbFaultProcessor)
	    	.doCatch(Exception.class)
	    		.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Caught exception ${exception.class}: \n ${exception.message}")
	    		.process(cbFaultProcessor)
	    	.end()

			
			.removeHeaders("cb_*")
		;
		
		
	}

}
