package bifast.outbound.credittransfer;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.processor.BuildCBDebitRequestProcessor;
import bifast.outbound.credittransfer.processor.BuildCTRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.processor.ExceptionResponseProcessor;
import bifast.outbound.credittransfer.processor.StoreCreditTransferProcessor;
import bifast.outbound.exception.DebitException;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired private BuildCBDebitRequestProcessor buildDebitRequestProcessor;
	@Autowired private BuildCTRequestProcessor crdtTransferProcessor;
	@Autowired private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired private ExceptionResponseProcessor exceptionResponseProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private StoreCreditTransferProcessor saveCrdtTrnsProcessor;
	
	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
    
	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlResponseJDF = jdfService.basicPrettyPrint(ChannelResponseWrapper.class);

	    onException(DebitException.class)
	    	.process(exceptionResponseProcessor)
			.to("seda:logportal?exchangePattern=InOnly")
	    	.marshal(chnlResponseJDF)
	    	.log(LoggingLevel.ERROR, "komi.ct", "[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Debit account gagal.")
	    	.log(LoggingLevel.ERROR, "komi.ct", "${body}")
	    	.removeHeaders("*")
	    	.handled(true)
	    ;
	    
		from("seda:credittrns").routeId("komi.ct")
			
			.setHeader("ct_progress", constant("Start"))
			
			// FILTER-A debit-account di cbs dulu donk untuk e-Channel
			.filter().simple("${header.hdr_request_list.merchantType} != '6010' ")
				.log(LoggingLevel.DEBUG, "komi.ct", 
						"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] selain dari Teller")
				.setHeader("ct_progress", constant("CB"))
				.process(buildDebitRequestProcessor)
				.log("[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] call Corebank")
				.to("seda:callcb")
			.end()
		
			// periksa hasil debit-account. Jika failure raise exception
			.filter().simple("${body.class} endsWith 'FaultPojo'")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						FaultPojo fault = exchange.getMessage().getBody(FaultPojo.class);
						throw new DebitException("Debit failure", fault);
					}
				})
			.end()

			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] "
					+ "setelah corebank, ${header.ct_progress}.")
		
			// lanjut submit ke BI
			.process(crdtTransferProcessor)
			
			// save data awal
			.to("seda:savecredittransfer?exchangePattern=InOnly")   // data awal

			.to("direct:call-cihub?timeout=0")
			
			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] response class dari cihub: ${body}")
			.to("seda:savecredittransfer?exchangePattern=InOnly")   // update data


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
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] hasil cihub, ${header.ct_progress}.")

			// jika response RJCT/ERROR, harus reversal ke corebanking
			// unt Teller tidak dilakukan KOMI tapi oleh Teller langsung
			.filter().simple("${header.ct_progress} in 'REJECT,ERROR' && ${header.hdr_request_list.merchantType} != '6010'")
				.log(LoggingLevel.DEBUG, "komi.ct", "akan reversal")
				.to("seda:debitreversal")
			.end()
	
			.process(crdtTransferResponseProcessor)		

			.removeHeaders("ct_*")
			
		;
		
		
		from("seda:savecredittransfer")
			.process(saveCrdtTrnsProcessor)
		;
		
		
		
	}
}
