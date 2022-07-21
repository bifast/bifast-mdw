package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.credittransfer.processor.PrePaymentStatusProc;
import bifast.outbound.credittransfer.processor.SaveCreditTransferProc;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CTPaymentStatusRoute extends RouteBuilder{

	@Autowired private PrePaymentStatusProc prePaymentStatusProc;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveCreditTransferProc updateCTTable;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);

		from("seda:psr").routeId("komi.psr")
			.process(prePaymentStatusProc)
			
			.filter(simple("${exchangeProperty.prop_sttlfound} == false"))
			.log(LoggingLevel.DEBUG, "komi.psr", 
					"[PSReq:${exchangeProperty.prop_request_list.requestId}] Akan PSR")

			.setHeader("cihubMsgName", constant("PSReq"))
			.to("direct:call-ciconn")				

			// kalo terima settlement, forward ke Inbound Service
			.filter().simple("${body.class} endsWith 'FlatPacs002Pojo' ")
				.choice()
					.when().simple("${body.bizSvc} == 'STTL' ")
						.log(LoggingLevel.DEBUG, "komi.psr", 
								"[PSReq:${exchangeProperty.prop_request_list.requestId}] PSR receive Settlement")
						.to("direct:fwd_settlement")
					.when().simple("${body.transactionStatus} == 'ACTC' ")
						.log(LoggingLevel.DEBUG, "komi.psr", 
							"[PSReq:${exchangeProperty.prop_request_list.requestId}] PSR receive success transaction")
					.when().simple("${body.transactionStatus} == 'RJCT' ")
						.log(LoggingLevel.DEBUG, "komi.psr", 
							"[PSReq:${exchangeProperty.prop_request_list.requestId}] Reject dan akan debit-reversal")
						// kalo terima reject, lakukan reversal

						.setHeader("tmp_body", simple("${body}"))
						.to("direct:debitreversal")
						.setBody(header("tmp_body")).removeHeader("tmp_body")
				.end()
			.end()

			// jika masih timeout (FaultPojo) doing nothing
			.filter().simple("${body.class} endsWith 'FaultPojo' ")
				.log(LoggingLevel.DEBUG, "komi.psr", 
						"[PSReq:${exchangeProperty.prop_request_list.requestId}] PaymentStatusRequest TIMEOUT")
			.end()
			
			.process(updateCTTable)
		
		;

		
		from("direct:fwd_settlement").routeId("komi.psr.fwd_settlement")
			.setProperty("pr_tmpbody", simple("${body}"))
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
					exchange.getMessage().setBody(rmc.getCihubResponse(), BusinessMessage.class);
				}
			})
			.marshal(businessMessageJDF)
//			.log("kirim settlement ${body}")
			.to("rest:post:?host={{komi.url.inbound}}")
			.setBody(simple("${exchangeProperty.pr_tmpbody}"))
		;

	}

}
