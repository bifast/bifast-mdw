package bifast.inbound.settlement;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.credittransfer2.JobWakeupProcessor;

@Component
public class SettlementRoute extends RouteBuilder {
	@Autowired private BuildSettlementCBRequestProcessor settlementProcessor;
	@Autowired private SaveSettlementMessageProcessor saveSettlement;
	@Autowired private JobWakeupProcessor jobWakeupProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:settlement").routeId("komi.settlement")
			
			// prepare untuk request ke corebank
//			.process(settlementProcessor)

	 		.log(LoggingLevel.DEBUG, "komi.settlement", 
	 				"[${header.hdr_process_data.inbMsgName}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Terima settlement")

//	 		.choice()
//	 			.when().simple("${body.orgnlKomiTrnsId} != null")
//	 				.to("seda:callcb")
//				.otherwise()
//					.log("Settlement tidak match")
//			.end()

			.process(saveSettlement)
			
			.process(jobWakeupProcessor)
			
	 		.log(LoggingLevel.DEBUG, "komi.settlement", 
	 				"[${header.hdr_process_data.inbMsgName}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] selesai proses Settlement ")
		;

	}

}
