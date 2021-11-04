package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CbDebitReversalPojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.service.CorebankService;

@Component
public class DebitReversalRoute extends RouteBuilder{
	@Autowired
	private CorebankService cbService;

	@Override
	public void configure() throws Exception {
		
		from("seda:debitreversal").routeId("komi.cb.reversal")
			.log("[ChnlReq:${header.hdr_request_list.requestId}][CTReq] akan Reversal.")
			.setHeader("ct_progress", constant("REVERSAL"))
			.setHeader("ct_tmpbody", simple("${body}"))
			
			// build reversal message
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					ChnlCreditTransferRequestPojo chnReq = rmw.getChnlCreditTransferRequest();
					CbDebitReversalPojo reversalReq = cbService.initDebitReversal(chnReq, rmw.getMerchantType());
					exchange.getMessage().setBody(reversalReq);
				}
			})
			
			//submit reversal
			.to("seda:callcb")
			
			.filter().simple("${body} is 'bifast.outbound.pojo.FaultPojo'")
				.log("Fault!")
			.end()

			// jika gagal reversal return as Fault, otherwise DebitResponse
			.choice()
				.when().simple("${body.class} endsWith 'CbDebitResponsePojo'")
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
