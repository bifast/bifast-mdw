package bifast.inbound.credittransfer2;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.exception.CTSAFException;
import bifast.inbound.notification.PortalApiPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferSubmissionRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private CTCorebankRequest2Processor ctRequestProcessor;
//	@Autowired private buildSettlementProcessor buildSettlementRequest;
	@Autowired private InitiateCTJobProcessor initCTJobProcessor;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat creditRequestJDF = jdfService.wrapRoot(CbCreditRequestPojo.class);
		JacksonDataFormat creditResponseJDF = jdfService.basic(CbCreditResponsePojo.class);

		onException(CTSAFException.class).routeId("ctsaf.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.ct.saf&action=stop&async=true")
		;
		
		from("sql:select kct.id , kct.komi_trns_id , kct.response_code , kct.full_request_msg "
				+ "from kc_credit_transfer kct "
				+ "where kct.cb_status = 'PENDING' "
				+ "and kct.sttl_bizmsgid is not null "
				+ "?delay=10000"
				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.ct.saf")
						
			// selesai dan matikan router jika tidak ada lagi SAF
			.filter().simple("${body} == null")
				.throwException(CTSAFException.class, "CT SAF Selesai.")			  
			.end()	

			// ***************** //
			
			.setHeader("ctsaf_qryresult", simple("${body}"))
			.log("[CTSAF:${header.ctsaf_qryresult[komi_trns_id]}] Submit CreditTransfer started.")

			.setBody(simple("${header.ctsaf_qryresult[FULL_REQUEST_MSG]}"))
			.unmarshal().base64().unmarshal().zipDeflater()
//			.log("setelah unzip ${body}")
			.unmarshal(businessMessageJDF)
			.setHeader("ctsaf_orgnCdTrns", simple("${body}"))

			.process(initCTJobProcessor)  // hdr_process_data

			.process(ctRequestProcessor)
			// send ke corebank
			.to("direct:post_credit_cb")
			
			.log("selesai call post_credit")
			.marshal(creditResponseJDF).log("${body}").unmarshal(creditResponseJDF)
			
			.choice()
				.when().simple("${body.status} == 'RJCT'")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'DONE', reversal = 'PENDING' "
							+ "where id = :#${header.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'ERROR'")
					//TODO harus report ke admin
					.log(LoggingLevel.ERROR,"komi.ct.saf", "error submit credit-account ke cbs")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'ERROR' "
							+ "where id = :#${header.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'TIMEOUT'")
					//TODO harus report ke admin
					.log(LoggingLevel.ERROR,"komi.ct.saf", "TIMEOUT submit credit-account ke cbs")
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'TIMEOUT' "
							+ "where id = :#${header.ctsaf_qryresult[id]}")
				.endChoice()
				.when().simple("${body.status} == 'ACTC'")
					.setHeader("ctsaf_settlement", constant("YES"))
					.to("sql:update kc_credit_transfer "
							+ "set cb_status = 'DONE' "
							+ "where id = :#${header.ctsaf_qryresult[id]}")
				.endChoice()
			.end()

			.filter().simple("${header.ctsaf_settlement} == 'YES'")
				.to("direct:post_settlement")
			.end()
			
			.delay(2000)
			.throwException(CTSAFException.class, "CT SAF Selesai.")			  
					
		;

		from ("direct:post_settlement").routeId("post_settlement")
			.log(LoggingLevel.DEBUG,"post_settlement", "Akan post settlement")
			
		;

	}

}
