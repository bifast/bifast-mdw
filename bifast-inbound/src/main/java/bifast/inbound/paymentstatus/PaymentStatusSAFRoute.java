package bifast.inbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.notification.PortalApiPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class PaymentStatusSAFRoute extends RouteBuilder {
	@Autowired private EnrichmentAggregator enrichmentAggr;
	@Autowired private BuildPSSAFRequestProcessor buildPSRequest;;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private ProcessQuerySAFProcessor processQueryProcessor;
	@Autowired private PSFilter psFilter;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat portalLogJDF = jdfService.wrapRoot(PortalApiPojo.class);

//		onException(PSNotFoundException.class).routeId("ps.onException")
//			.handled(true)
//			.to("controlbus:route?routeId=komi.inqsttl.saf&action=stop&async=true")
//		;
		
		
		from("sql:select ct.id, "
				+ "ct.req_bizmsgid, "
				+ "ct.komi_trns_id as komi_id, "
				+ "ct.full_request_msg as txtctreq, "
				+ "ct.e2e_id, "
				+ "ct.recpt_bank, "
				+ "ct.create_dt, "
				+ "ct.ps_counter "
				+ "from kc_credit_transfer ct "
				+ "where ct.sttl_bizmsgid = 'WAITING' "
				+ "and ct.ps_counter < 6 "
				+ "limit 10"
				+ "?delay=60000"
//				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.inbct.saf")
						
//			.log("[CTReq:${header.ps_qryresult[e2e_id]}] PymtStsSAF started.")

			.process(processQueryProcessor)
//			.filter().method(psFilter, "timeIsDue")
			
			.log("[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Retry ${exchangeProperty.pr_psrequest.psCounter}")
		
//			.log("hdr_settlement: ${header.hdr_settlement}")
			.log(LoggingLevel.DEBUG, "komi.ps.saf", 
					"[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Siapkan PS Request")
			.process(buildPSRequest)

//			.to("direct:call-cihub")				
			.marshal(businessMessageJDF)
			
			.log("Akan post: ${body}")
			.stop()
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.log(LoggingLevel.DEBUG, "komi.call-cihub", 
						"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] "
						+ "sisa waktu ${exchangeProperty.prop_remain_time}")
	
				.enrich()
					.simple("{{komi.url.ciconnector}}?"
						+ "socketTimeout=6&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggr)
				
				.convertBodyTo(String.class)				
				.log("[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CIHUB response: ${body}")
	
				.setHeader("tmp_body", simple("${body}"))
				.marshal().zipDeflater().marshal().base64()
				.setHeader("cihubroute_encr_response", simple("${body}"))
				
//				.process(new Processor() {
//					public void process(Exchange exchange) throws Exception {
//						String body = exchange.getMessage().getBody(String.class);
//						RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
//						rmw.setCihubEncriptedResponse(body);
//						exchange.setProperty("prop_request_list", rmw);
//					}
//				})
				
				.setBody(simple("${header.tmp_body}"))	
				.unmarshal(businessMessageJDF)
//				.process(new Processor() {
//					public void process(Exchange exchange) throws Exception {
//						BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
//						ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
//						rmc.setCihubResponse(bm);
//						exchange.setProperty("prop_response_list", rmc);
//					}
//				})
//								
//				.process(flatResponseProcessor)
				
				.filter().simple("${body.class} endsWith 'FaultPojo'")
					.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CI-HUB Response: ${header.tmp_body}")
		    	.end()
			
			.endDoTry()
			.doCatch(java.net.SocketTimeoutException.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] CI-HUB TIMEOUT.")
	//	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//		    	.process(exceptionToFaultMap)
			.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
//		    	.process(exceptionToFaultMap)
			.end()

			// kalo terima settlement, forward ke Inbound Service
			.filter().simple("${body.class} endsWith 'FlatPacs002Pojo' "
							+ "&& ${body.bizSvc} == 'STTL' ")
				.log(LoggingLevel.DEBUG, "komi.ps.saf", 
						"[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Settlement dari CIHUB kirim ke Inbound Service")
				.to("seda:fwd_settlement?exchangePattern=InOnly")
			.end()

//			.log(LoggingLevel.DEBUG, "komi.ps.saf", 
//					"[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Akan proses response SAF")
//			.process(psResponseProcessor)
//
//			.log(LoggingLevel.DEBUG, "komi.ps.saf", 
//					"[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Akan update status Credit Transfer SAF")
//			.process(updateStatusProcessor)
//			
//			.log("[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] PymtStsSAF result: ${body.psStatus}")
//
//			.filter().simple("${body.psStatus} == 'REJECTED'")
//				.to("direct:debitreversal")
//			.end()
			
			//TODO call notifikasi
			.filter().simple("${exchangeProperty.pr_notif} == 'yes'")
				//TODO akan notifkikasi
				.log("akan notifikasi")
			.end()
			
//			.filter().simple("${body.psStatus} != 'UNDEFINED'")
//				.process(logPortalProcessor)
//				.marshal(portalLogJDF)
////				.log(LoggingLevel.DEBUG, "komi.notif.portal", "Log-notif ${body}")
//			    .removeHeaders("CamelHttp*")
//				.to("rest:post:?host={{komi.url.portalapi}}")
//			.end()
//			
//			.removeHeaders("ps_*")
					
		;

//		from("seda:fwd_settlement").routeId("komi.ps.fwd_settlement")
//			.process(new Processor() {
//				public void process(Exchange exchange) throws Exception {
//					ResponseMessageCollection rmc = exchange.getProperty("prop_response_list", ResponseMessageCollection.class);
//					exchange.getMessage().setBody(rmc.getCihubResponse(), BusinessMessage.class);
//				}
//			})
//			.marshal(businessMessageJDF)
//			.to("rest:post:?host={{komi.url.inbound}}")
//		;


	}

}
