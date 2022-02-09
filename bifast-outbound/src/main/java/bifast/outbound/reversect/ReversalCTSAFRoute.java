package bifast.outbound.reversect;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.exception.PSNotFoundException;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class ReversalCTSAFRoute extends RouteBuilder {
	@Autowired private BuildRCTLogForPortalProc logPortalProcessor;
	@Autowired private BuildRevCTRequestProcessor buildRevCTRequestProc;
	@Autowired private InitRevCTProc initProc;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveReversalCTProc saveRCTPrc;
	@Autowired private RCTUpdateStatus updateStatusProcessor;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat portalLogJDF = jdfService.wrapPrettyPrint(PortalApiPojo.class);

		onException(PSNotFoundException.class).routeId("revct.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.revct.saf&action=stop&async=true")
		;
		
		
		from("sql:select ct.id, "
				+ "ct.komi_trns_id as komi_id, "
				+ "ct.full_request_msg as txt_req_msg, "
				+ "ct.e2e_id, "
				+ "ct.error_message "
				+ "from kc_credit_transfer ct "
				+ "where ct.reversal = 'PENDING'"
				+ "?delay=120000"
//				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.revct.saf")
			
			.process(initProc)
			
			.log(LoggingLevel.DEBUG, "komi.revct.saf", 
					"[RevCT:${exchangeProperty.pr_rctrequest.endToEndId}] Siapkan Rev-CT Request")
			.process(buildRevCTRequestProc)
								
			.to("direct:call-cihub")				


			.log(LoggingLevel.DEBUG, "komi.revct.saf", 
					"[RevCT:${exchangeProperty.pr_rctrequest.endToEndId}] Akan update status Credit Transfer SAF")
			.process(updateStatusProcessor)
			
			.process(saveRCTPrc)
						
			//TODO call notifikasi
			.filter().simple("${exchangeProperty.pr_notif} == 'yes'")
				//TODO akan notifkikasi
				.log(LoggingLevel.ERROR, "komi.revct.saf", 
						"[RevCT:${exchangeProperty.pr_rctrequest.endToEndId}] Reversal Credit Transfer REJECTED.")
			.end()
			
			.process(logPortalProcessor)
			.marshal(portalLogJDF)
		    .removeHeaders("CamelHttp*")
			.to("rest:post:?host={{komi.url.portalapi}}")
			
					
		;


	}

}
