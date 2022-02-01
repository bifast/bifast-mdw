package bifast.inbound.settlement;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.credittransfer.processor.JobWakeupProcessor;

@Component
public class SettlementRoute extends RouteBuilder {
//	@Autowired private BuildSettlementCBRequestProcessor settlementProcessor;
	@Autowired private SettlementCreditProcessor settlementProcessor;
	@Autowired private SaveSettlementMessageProcessor saveSettlement;
	@Autowired private JobWakeupProcessor jobWakeupProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:settlement").routeId("komi.settlement")
			
			// prepare untuk request ke corebank

	 		.log(LoggingLevel.DEBUG, "komi.settlement", 
	 				"[${exchangeProperty.prop_process_data.inbMsgName}:${exchangeProperty.prop_process_data.endToEndId}] Terima settlement")

			.process(saveSettlement)
			
			.choice()
				.when().simple("${header.sttl_transfertype} == 'Inbound'")
		 			.log(LoggingLevel.DEBUG, "komi.settlement", 
		 					"[${exchangeProperty.prop_process_data.inbMsgName}:${exchangeProperty.prop_process_data.endToEndId}] activate credit-posting job")

					.process(jobWakeupProcessor)
				.otherwise()
					.process(settlementProcessor)
//					.to("direct:post_credit_cb")
					.to("direct:isoadpt")
			 		.log("[${exchangeProperty.prop_process_data.inbMsgName}:${exchangeProperty.prop_process_data.endToEndId}] Selesai posting settlement")
			.end()

		;

	}

}
