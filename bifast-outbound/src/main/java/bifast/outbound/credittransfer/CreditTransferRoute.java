package bifast.outbound.credittransfer;

import java.time.format.DateTimeFormatter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.processor.AftDebitCallProc;
import bifast.outbound.credittransfer.processor.BuildCTRequestProcessor;
import bifast.outbound.credittransfer.processor.BuildDebitRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.processor.SaveCreditTransferProc;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired private BuildDebitRequestProcessor buildDebitRequestProcessor;
	@Autowired private BuildCTRequestProcessor crdtTransferProcessor;
	@Autowired private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired private SaveCreditTransferProc saveCrdtTrnsProcessor;
	@Autowired private AftDebitCallProc afterDebitCallProc;

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
    
	@Override
	public void configure() throws Exception {
	
		from("direct:credittrns").routeId("komi.ct")
			
			.setHeader("ct_progress", constant("Start"))
			.setProperty("pr_response", constant("ACTC"))

			// FILTER-A debit-account di cbs dulu donk untuk e-Channel
			.filter().simple("${exchangeProperty.prop_request_list.merchantType} != '6010' ")
				.log(LoggingLevel.DEBUG, "komi.ct", 
						"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Transaksi non-teller")
				.setHeader("ct_progress", constant("CB"))
				.process(buildDebitRequestProcessor)
				.to("direct:debit")
				.process(afterDebitCallProc)

			.end()
		
	    	.filter(exchangeProperty("pr_response").isEqualTo("ACTC"))

			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] "
					+ "setelah corebank, ${exchangeProperty.pr_response}.")

			// lanjut submit ke BI
			.process(crdtTransferProcessor)
			
			// save data awal
			.to("seda:savecredittransfer?exchangePattern=InOnly&blockWhenFull=true")   // simpan data awal

			.to("direct:call-ciconn?timeout=0")
					
			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] response class dari cihub: ${body}")
			.to("seda:savecredittransfer?exchangePattern=InOnly&blockWhenFull=true")   // update data

			// periksa hasil call cihub
			.choice()
				.when().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} == 'K000'")
					.setHeader("ct_progress", constant("TIMEOUT"))
				.when().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} != 'K000'")
					.setHeader("ct_progress", constant("ERROR"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.reasonCode} == 'U900'")
					.setHeader("ct_progress", constant("TIMEOUT"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.transactionStatus} == 'ACTC'")
					.setHeader("ct_progress", constant("SUCCESS"))
				.otherwise()
					.setHeader("ct_progress", constant("REJECT"))
			.end()
			
			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] hasil cihub, ${header.ct_progress}.")

			// jika response RJCT, harus reversal ke corebanking
			// unt Teller tidak dilakukan KOMI tapi oleh Teller langsung
			.choice()
				.when().simple("${header.ct_progress} in 'REJECT' "
					+ "&& ${exchangeProperty.prop_request_list.merchantType} != '6010'")
					.log(LoggingLevel.DEBUG, "komi.ct", 
							"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] akan reversal")
					
					.to("seda:debitreversal?exchangePattern=InOnly&blockWhenFull=true")   // update data
//					.to("direct:debitreversal")

				.when().simple("${header.ct_progress} == 'TIMEOUT'")
					.log(LoggingLevel.DEBUG, "komi.ct", 
						"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] akan payment status")
					.to("seda:psr?exchangePattern=InOnly")
			.end()
			
			.process(crdtTransferResponseProcessor)		
			
			.removeHeaders("ct_*")
		;
		
		
		from("seda:savecredittransfer?exchangePattern=InOnly").routeId("komi.ct.savedb")
			.process(saveCrdtTrnsProcessor)
		;
	
	}
}
