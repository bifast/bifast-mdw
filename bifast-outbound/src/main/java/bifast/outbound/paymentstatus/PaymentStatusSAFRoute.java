package bifast.outbound.paymentstatus;

import java.time.Instant;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CbDebitRequestPojo;
import bifast.outbound.paymentstatus.processor.BuildPaymentStatusSAFRequestProcessor;
import bifast.outbound.paymentstatus.processor.PaymentStatusResponseProcessor;
import bifast.outbound.paymentstatus.processor.UpdateStatusSAFProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;

//@Component
public class PaymentStatusSAFRoute extends RouteBuilder {
	@Autowired
	private BuildPaymentStatusSAFRequestProcessor buildPSRequest;;
	@Autowired
	private PaymentStatusResponseProcessor psResponseProcessor;
	@Autowired
	private UpdateStatusSAFProcessor updateStatusProcessor;

	@Override
	public void configure() throws Exception {
		
		from("sql:select ct.id, ct.req_bizmsgid, ct.komi_trns_id as komi_id, chnl.channel_type, ct.recpt_bank, cht.request_time  "
			+ "from kc_credit_transfer ct "
			+ "join kc_channel_transaction cht on ct.komi_trns_id = cht.komi_trns_id "
			+ "join kc_channel chnl on chnl.channel_id = cht.channel_id "
			+ " where ct.call_status = 'TIMEOUT'")
			.routeId("komi.ps.saf")
						
			// simpan dulu hasil query dan daftar requestmessagewrapper
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					@SuppressWarnings("unchecked")
					HashMap<String, String> arr = exchange.getMessage().getBody(HashMap.class);
					UndefinedCTPojo ct = new UndefinedCTPojo();
					ct.setId(String.valueOf(arr.get("id")));
					ct.setKomiTrnsId(arr.get("komi_id"));
					ct.setRecipientBank(arr.get("recpt_bank"));
					ct.setReqBizmsgid(arr.get("req_bizmsgid"));
					ct.setChannelType(arr.get("channel_type"));
					
					System.out.println("orgnlDateTime " + arr.get("req_bizmsgid"));
//					ct.setOrgnlDateTime(arr.get("request_time"));
					exchange.getMessage().setHeader("ps_request", ct);
					
					RequestMessageWrapper rmw = new RequestMessageWrapper();
					rmw.setChannelRequest(ct);
					rmw.setKomiStart(Instant.now());
					rmw.setMsgName("PaymentStsSAF");
					exchange.getMessage().setHeader("hdr_request_list", rmw);

				}
			})
			
			.process(buildPSRequest)
			
			.to("direct:call-cihub")
			.process(psResponseProcessor)

			.process(updateStatusProcessor)

			.filter().simple("${body.psStatus} == 'REJECTED'")
				.log("${body.reqBizmsgid} Rejected")
					//TODO lakukan reversal
				//init data reversal dulu
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						CbDebitRequestPojo reversalReq = new CbDebitRequestPojo();
						reversalReq.setAmount(null);
					}
					
				})
				.to("seda:debitreversal")
			.end()
					
		;

	}

}
