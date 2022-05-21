package bifast.outbound.corebank;


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.corebank.pojo.DebitResponseDTO;
import bifast.outbound.corebank.processor.CbCallFaultProcessor;
import bifast.outbound.corebank.processor.SaveCBTableProcessor;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.StatusReasonRepository;
import bifast.outbound.service.JacksonDataFormatService;



@Component
public class DebitRoute extends RouteBuilder{
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveCBTableProcessor saveCBTransactionProc;
	@Autowired private StatusReasonRepository reasonRepo;
	
	@SuppressWarnings("deprecation")
	@Override
	public void configure() throws Exception {
		JacksonDataFormat debitRequestJDF = jdfService.basic(DebitRequestDTO.class);
		JacksonDataFormat debitResponseJDF = jdfService.basic(DebitResponseDTO.class);


		// ROUTE CALLCB 
		from("direct:debit").routeId("komi.cb.debit")

			.removeHeaders("*")

			.setProperty("pr_cbrequest", simple("${body}"))
			
			.log(LoggingLevel.DEBUG,"komi.cb.debit", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Terima di corebank: ${body}")
					
			.marshal(debitRequestJDF)
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.debit", "[${exchangeProperty.prop_request_list.msgName}:"
	 				+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.debit}}")
	 		.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CB Request: ${body}")
			 		
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("{{komi.url.isoadapter.debit}}?bridgeEndpoint=true")
					
				.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.log("[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] CB response: ${body}")

 				.unmarshal(debitResponseJDF)

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

			.log(LoggingLevel.DEBUG, "komi.cb.debit", "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] "
					+ "akan simpan debit msg")
			.process(saveCBTransactionProc)
			
			.removeHeaders("cb_*")
		;
		
		
	}

}
