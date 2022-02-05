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
import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.corebank.pojo.DebitResponseDTO;
import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.corebank.processor.CbCallFaultProcessor;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.StatusReasonRepository;
import bifast.outbound.service.JacksonDataFormatService;



@Component
public class IsoAdapterRoute extends RouteBuilder{
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveCBTableProcessor saveCBTransactionProc;
	@Autowired private StatusReasonRepository reasonRepo;
	
	@Override
	public void configure() throws Exception {
		JacksonDataFormat aciRequestJDF = jdfService.basic(AccountCustInfoRequestDTO.class);
		JacksonDataFormat aciResponseJDF = jdfService.basic(AccountCustInfoResponseDTO.class);
		JacksonDataFormat debitRequestJDF = jdfService.basic(DebitRequestDTO.class);
		JacksonDataFormat debitResponseJDF = jdfService.basic(DebitResponseDTO.class);
		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);

		// ROUTE CALLCB 
		from("direct:isoadpt").routeId("komi.isoadapter")
//			.setProperty("bkp_hdr_process_data").header("hdr_process_data")

			.removeHeaders("*")

			.setHeader("cb_msgname", simple("${exchangeProperty[prop_process_data.inbMsgName]}"))
			.setHeader("cb_e2eid", simple("${exchangeProperty[prop_process_data.endToEndId]}"))


			.setProperty("pr_cbrequest", simple("${body}"))
			
			.log(LoggingLevel.DEBUG,"komi.isoadapter", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] Terima di corebank: ${body}")
					
			.choice()
				.when().simple("${body.class} endsWith 'AccountCustInfoRequestDTO'")
					.setProperty("pr_cbRequestName", constant("accountcustinfo"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.customerinfo}}"))
					.marshal(aciRequestJDF)
				.when().simple("${body.class} endsWith 'DebitRequestDTO'")
					.setProperty("pr_cbRequestName", constant("debit"))
					.setHeader("cb_url", simple("{{komi.url.isoadapter.debit}}"))
					.marshal(debitRequestJDF)
				.when().simple("${body.class} endsWith 'DebitReversalRequestPojo'")
					.setProperty("pr_cbRequestName", constant("debitreversal"))
					.marshal(debitReversalReqJDF)
					.setHeader("cb_url", simple("{{komi.url.isoadapter.reversal}}"))

				.otherwise()
		 			.log("Akan kirim : ${body.class}")
			.end()
			
			.setProperty("cb_request_str", simple("${body}"))
			
	 		.log(LoggingLevel.DEBUG, "komi.isoadapter", "[${exchangeProperty.prop_request_list.msgName}:"
	 				+ "${exchangeProperty.prop_request_list.requestId}] POST ${header.cb_url}")
	 		.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CB Request: ${body}")
			
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
				.enrich().simple("${header.cb_url}?bridgeEndpoint=true")
					
				.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
				
				.log("[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] CB response: ${body}")

		    	.choice()
		 			.when().simple("${exchangeProperty.pr_cbRequestName} == 'accountcustinfo'")
		 				.unmarshal(aciResponseJDF)
	 				.endChoice()
		 			.when().simple("${exchangeProperty.pr_cbRequestName} == 'debit'")
		 				.unmarshal(debitResponseJDF)
		 			.when().simple("${exchangeProperty.pr_cbRequestName} == 'debitreversal'")
	 					.unmarshal(debitReversalResponseJDF)
	 				.endChoice()
		 		.end()

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
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${header.cb_e2eid}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

			.filter().simple("${exchangeProperty.pr_cbRequestName} in 'debit,debitreversal'")
				.log("akan simpan ${exchangeProperty.prop_request_list.msgName}")
				.process(saveCBTransactionProc)
			.end()
			
			.removeHeaders("cb_*")
		;
		
		
	}

}
