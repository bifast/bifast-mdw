package bifast.inbound.settlement;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.exception.NonSettleCreditTransferException;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

//@Component
public class SettlementSAFRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);

		onException(NonSettleCreditTransferException.class).routeId("sttl.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.sttl.saf&action=stop&async=true")
		;
		
		
		from("sql:select ct.id, ct.req_bizmsgid, ct.komi_trns_id as komi_id,  "
				+ "ct.recpt_bank, cht.request_time  "
				+ "from kc_credit_transfer ct "
				+ "where ct.call_status = 'PENDING'"
				+ "?delay=10000"
				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.sttl.saf")
//			.autoStartup(false)
						
			.setHeader("sttlct_qryresult", simple("${body}"))
			.log("[CTReq:${header.ps_qryresult[channel_ref_id]}] PymtStsSAF started.")

			// selesai dan matikan router jika tidak ada lagi SAF
			.filter().simple("${body} == null")
				.throwException(NonSettleCreditTransferException.class, "CreditTransfer Settlement Selesai.")			  
			.end()	
						
			.log(LoggingLevel.DEBUG, "komi.sttl.saf", "Akan proses response SAF")
			
			// build CT untuk dikirim ke cbs
			// kirim CT ke cbs
			// jika sukses, update call_status = SUCCESS
			
			// jika gagal ct ke cbs, catat sebagai Reversal
			
//			.process(psResponseProcessor)

			.removeHeaders("sttlct_*")
					
		;


	}

}
