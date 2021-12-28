package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class DebitReversalRoute extends RouteBuilder{
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private CbFaultExceptionProcessor cbFaultProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CreditTransferRepository ctRepo;
	
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat debitReversalRequestJDF = jdfService.wrapRoot(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.wrapRoot(DebitReversalResponsePojo.class);

		from("seda:debitreversal").routeId("komi.reverse_ct")
			.log("[ChnlReq:${header.hdr_request_list.requestId}][CTReq] akan Reversal.")
			.setHeader("ct_progress", constant("REVERSAL"))
			.setHeader("ct_tmpbody", simple("${body}"))
				
			// build reversal message
			.process(debitReversalRequestProcessor)
			.marshal(debitReversalRequestJDF)
			.log("Call ISOAdapter: ${body}")
			//submit reversal
//			.to("seda:callcb")
			.doTry()

				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("http://{{komi.url.isoadapter}}?"
	//					+ "socketTimeout=7000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
	
				.convertBodyTo(String.class)
				.unmarshal(debitReversalResponseJDF)
				
				.filter().simple("${body.status} != 'ACTC' ")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							DebitReversalResponsePojo resp = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
							FaultPojo fault = new FaultPojo();
							fault.setResponseCode(resp.getStatus());
							fault.setReasonCode(resp.getReason());
							exchange.getMessage().setBody(fault);
						}
					})
				.end()
				
			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_request_list.requestId}] Call Corebank Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
			.end()
			
			.log("Hasil reversal: ${body}")
			.filter().simple("${body} is 'bifast.outbound.pojo.FaultPojo'")
				.log("Fault!")
			.end()

			// jika gagal reversal return as Fault, otherwise DebitResponse
			.choice()
				.when().simple("${body.class} endsWith 'DebitReversalResponsePojo'")
					.setBody(simple("${header.ct_tmpbody}"))
				.endChoice()
				
				.when().simple("${body.class} endsWith 'FaultPojo'")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							FaultPojo fault = exchange.getMessage().getBody(FaultPojo.class);		
							fault.setCallStatus("TIMEOUT");
							fault.setResponseCode("KSTS");
							fault.setReasonCode("K000");
							exchange.getMessage().setBody(fault);
						}
					})
				.endChoice()
			.end()
		
		;
	}

		
		
}
