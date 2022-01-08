package bifast.outbound.corebank;

import java.lang.reflect.Method;

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

	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private SaveCBTableProcessor saveCBTableProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CBExceptionToFaultProcessor exceptionToFaultProcessor;

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
		
			.log(LoggingLevel.DEBUG, "komi.corebank", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] call Corebank")

			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					Object req = exchange.getMessage().getBody(Object.class);
					rmw.setCorebankRequest(req);
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
			})
							
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
			
	 		.log("[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}]"
	 														+ " CB Request: ${body}")

			.doTry()

				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("http://{{komi.url.corebank}}?"
	//					+ "socketTimeout=7000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)

				.convertBodyTo(String.class)
				
		 		.log("[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}]"
							+ " CB Response: ${body}")

				.choice()
					.when().simple("${header.cb_requestName} == 'debit'")
						.unmarshal(debitResponseJDF)
					.when().simple("${header.cb_requestName} ==  'emailphonelist'")
						.unmarshal(customerInfoResponseJDF)
					.when().simple("${header.cb_requestName} ==  'debitreversal'")
						.unmarshal(debitReversalResponseJDF)
				.end()

			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Call Corebank Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(exceptionToFaultProcessor)
	
			.end()

					
			.choice()
				.when().simple("${body.class} endsWith 'FaultPojo'")
		 			.log(LoggingLevel.DEBUG, "komi.corebank", "[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Fault")
				.when().simple("${body.status} == 'RJCT'")
			 		.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							Object oResponse = exchange.getMessage().getBody(Object.class);
							ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
							FaultPojo fault = new FaultPojo();
							fault.setCallStatus("REJECT-CB");
							Method mthStatus = oResponse.getClass().getMethod("getStatus");
							String status = (String) mthStatus.invoke(oResponse);
							String reason =  (String) oResponse.getClass().getMethod("getReason").invoke(oResponse);
							fault.setResponseCode(status);
							fault.setReasonCode(reason);
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
