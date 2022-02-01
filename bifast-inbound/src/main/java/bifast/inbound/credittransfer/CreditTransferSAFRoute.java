package bifast.inbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.SettlementRequest;
import bifast.inbound.credittransfer.processor.CTCorebankRequestProcessor;
import bifast.inbound.credittransfer.processor.InitiateCTJobProcessor;
import bifast.inbound.exception.CTSAFException;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs002Pojo;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.inbound.settlement.SettlementCreditProcessor;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferSAFRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CTCorebankRequestProcessor ctRequestProcessor;
//	@Autowired private BuildSettlementCBRequestProcessor buildSettlementRequest;
	@Autowired private SettlementCreditProcessor settlementRequestPrc;
	@Autowired private InitiateCTJobProcessor initCTJobProcessor;
	@Autowired private FlattenIsoMessageService flatMessageService;
	
	
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat settlementRequestJDF = jdfService.basic(SettlementRequest.class);

		onException(CTSAFException.class).routeId("ctsaf.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.ct.saf&action=stop&async=true")
		;
		
		from("sql:select kct.id , kct.komi_trns_id, kct.req_bizmsgid, kct.response_code, "
				+ "kct.full_request_msg ct_msg, sttl.full_message sttl_msg, sttl.e2e_id "
				+ "from kc_credit_transfer kct "
				+ "join kc_settlement sttl on sttl.e2e_id = kct.e2e_id "
				+ "where kct.cb_status = 'READY' "
				+ "?delay=10000"
				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.ct.saf")
						
			// selesai dan matikan router jika tidak ada lagi SAF
			.filter().simple("${body} == null")
				.throwException(CTSAFException.class, "CT SAF Selesai.")			  
			.end()	

			// ***************** //
			
//			.setHeader("ctsaf_qryresult", simple("${body}"))
			.setProperty("ctsaf_qryresult", simple("${body}"))
			.log("[CTSAF:${exchangeProperty.ctsaf_qryresult[e2e_id]}] Submit CreditTransfer started.")
			
			.setBody(simple("${exchangeProperty.ctsaf_qryresult[CT_MSG]}"))
			.unmarshal().base64().unmarshal().zipDeflater()
			.unmarshal(businessMessageJDF)
			.setHeader("ctsaf_orgnCdTrns", simple("${body}"))

			.log(LoggingLevel.DEBUG,"komi.ct.saf", 
					"[CTSAF:${exchangeProperty.ctsaf_qryresult[e2e_id]}] akan initiate data.")

			.process(initCTJobProcessor)  // hdr_process_data

			.process(ctRequestProcessor)
			// send ke corebank
			.to("direct:isoadpt")
			
			.log(LoggingLevel.DEBUG, "komi.ct.saf", "Selesai call credit account")
			
			.choice()
				.when().simple("${body.class} endsWith 'FaultPojo'")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'DONE', reversal = 'PENDING' "
							+ "where id = :#${exchangeProperty.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'RJCT'")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'DONE', reversal = 'PENDING' "
							+ "where id = :#${exchangeProperty.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'ERROR'")
					//TODO harus report ke admin
					.log(LoggingLevel.ERROR, "[CTSAF:${exchangeProperty.ctsaf_qryresult[e2e_id]}] error submit credit-account ke cbs")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'ERROR' "
							+ "where id = :#${exchangeProperty.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'TIMEOUT'")
					//TODO harus report ke admin
					.log(LoggingLevel.ERROR, "[CTSAF:${exchangeProperty.ctsaf_qryresult[e2e_id]}] TIMEOUT submit credit-account ke cbs")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'TIMEOUT' "
							+ "where id = :#${exchangeProperty.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'ACTC'")
//					.setHeader("ctsaf_settlement", constant("YES"))
					.setProperty("ctsaf_settlement", constant("YES"))
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'DONE' "
							+ "where id = :#${exchangeProperty.ctsaf_qryresult[id]}")
				.endChoice()
			.end()

			.filter().simple("${exchangeProperty.ctsaf_settlement} == 'YES'")
				.log(LoggingLevel.DEBUG,"komi.ct.saf", "[CTSAF:${exchangeProperty.ctsaf_qryresult[e2e_id]}] akan submit Settlement")
				.to("direct:post_settlement")
			.end()
			
			.delay(2000)
			.throwException(CTSAFException.class, "CT SAF Selesai.")			  
					
		;

		from ("direct:post_settlement").routeId("komi.post_settlement")
			.log(LoggingLevel.DEBUG,"komi.post_settlement", "Akan post settlement")
			.setBody(simple("${exchangeProperty.ctsaf_qryresult[STTL_MSG]}"))
			.unmarshal().base64().unmarshal().zipDeflater()
			.unmarshal(businessMessageJDF)

			.process(settlementRequestPrc)
			.log("ori noref: ${body.originalNoRef}")
			.to("direct:isoadpt")
			
		;

	}

}
