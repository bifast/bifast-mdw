package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.exception.PSNotFoundException;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.paymentstatus.processor.BuildMessageForPortalProcessor;
import bifast.outbound.paymentstatus.processor.BuildPaymentStatusSAFRequestProcessor;
import bifast.outbound.paymentstatus.processor.PaymentStatusResponseProcessor;
import bifast.outbound.paymentstatus.processor.ProcessQuerySAFProcessor;
import bifast.outbound.paymentstatus.processor.UpdateStatusSAFProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class PaymentStatusSAFRoute extends RouteBuilder {
	@Autowired private BuildMessageForPortalProcessor logPortalProcessor;
	@Autowired private BuildPaymentStatusSAFRequestProcessor buildPSRequest;;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private PaymentStatusResponseProcessor psResponseProcessor;
	@Autowired private ProcessQuerySAFProcessor processQueryProcessor;
	@Autowired private UpdateStatusSAFProcessor updateStatusProcessor;

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		JacksonDataFormat portalLogJDF = jdfService.wrapPrettyPrint(PortalApiPojo.class);

		onException(PSNotFoundException.class).routeId("ps.onException")
			.handled(true)
			.to("controlbus:route?routeId=komi.ps.saf&action=stop&async=true")
		;
		
		
		from("sql:select ct.id, cht.channel_ref_id, ct.req_bizmsgid, ct.komi_trns_id as komi_id, "
				+ "ct.e2e_id, chnl.channel_type, "
				+ "ct.recpt_bank, cht.request_time  "
				+ "from kc_credit_transfer ct "
				+ "join kc_channel_transaction cht on ct.komi_trns_id = cht.komi_trns_id "
				+ "join kc_channel chnl on chnl.channel_id = cht.channel_id "
				+ "where ct.call_status = 'TIMEOUT' "
				+ "order by ps_counter "
				+ "limit 5"
				+ "?delay=15000"
				+ "&sendEmptyMessageWhenIdle=true"
				)
			.routeId("komi.ps.saf")
//			.autoStartup(false)
						
			.setHeader("ps_qryresult", simple("${body}"))
//			.log("[CTReq:${header.ps_qryresult[e2e_id]}] PymtStsSAF started.")

			// selesai dan matikan router jika tidak ada lagi SAF
			.filter().simple("${body} == null")
//				.to("controlbus:route?routeId=komi.ps.saf&action=stop&async=true")
				.throwException(PSNotFoundException.class, "PS Selesai.")			  
			.end()	
						
			// check settlement dulu
			.process(processQueryProcessor)
			.unmarshal().base64()
			.unmarshal().zipDeflater()
			
//			.log(LoggingLevel.DEBUG, "komi.ps.saf", "[CTReq:${header.ps_request.channelNoref}] Original request: ${body}")

			.unmarshal(businessMessageJDF)
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					rmw.setCreditTransferRequest(bm);
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
				
			})
			.to("direct:caristtl")
			// selesai check settlement

//			.log("hdr_settlement: ${header.hdr_settlement}")
			.filter().simple("${header.hdr_settlement} == 'NOTFOUND'")	// jika tidak ada settlement
//				.log(LoggingLevel.DEBUG, "komi.ps.saf", 
//						"[CTReq:${header.ps_qryresult[channel_ref_id]}]Tidak ada settlement, build PS Request")
				.process(buildPSRequest)
				.to("direct:call-cihub")				
			.end()
			
			//TODO kalo terima settlement, forward ke Inbound Service
			.filter().simple("${header.hdr_settlement} == 'NOTFOUND' "
						+ "&& ${body.class} endsWith 'FlatPacs002Pojo' "
						+ "&& ${body.transactionStatus} == 'ACSC' ")
//				.log(LoggingLevel.DEBUG, "komi.ps.saf", "Terima settlement dari CIHUB harus kirim ke Inbound Service")
				.to("seda:fwd_settlement?exchangePattern=InOnly")
			.end()

//			.log(LoggingLevel.DEBUG, "komi.ps.saf", "Akan proses response SAF")
			.process(psResponseProcessor)

//			.log(LoggingLevel.DEBUG, "komi.ps.saf", "Akan update status Credit Transfer SAF")
			.process(updateStatusProcessor)
			
			.log("[CTReq:${header.ps_request.channelNoref}] PymtStsSAF result: ${body.psStatus}")

			.filter().simple("${body.psStatus} == 'REJECTED'")
				.log("${body.reqBizmsgid} Rejected")
					//TODO lakukan reversal
				.to("seda:debitreversal")
			.end()
			
			//TODO call notifikasi
			.filter().simple("${header.ps_notif} == 'yes'")
				.log("akan notifikasi")
			.end()
			
			.filter().simple("${body.psStatus} != 'UNDEFINED'")
				.process(logPortalProcessor)
				.marshal(portalLogJDF)
//				.log(LoggingLevel.DEBUG, "komi.notif.portal", "Log-notif ${body}")
			    .removeHeaders("CamelHttp*")
				.to("rest:post:?host={{komi.url.portalapi}}")
			.end()
			
			.removeHeaders("ps_*")
					
		;

		from("seda:fwd_settlement").routeId("komi.ps.fwd_settlement")
			.setHeader("ps_tmpbody", simple("${body}"))
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
					exchange.getMessage().setBody(rmc.getCihubResponse(), BusinessMessage.class);
				}
			})
			.marshal(businessMessageJDF)
			.log("${body}")
			.to("rest:post:?host={{komi.url.inbound}}")
			.setBody(simple("${header.ps_tmpbody}"))
		;


	}

}
