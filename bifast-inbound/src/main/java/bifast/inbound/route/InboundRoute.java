package bifast.inbound.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.SaveAccountEnquiryProcessor;
import bifast.inbound.corebank.PendingCorebankProcessor;
import bifast.inbound.credittransfer.SaveCreditTransferProcessor;
import bifast.inbound.processor.CheckRequestMsgProcessor;
import bifast.inbound.settlement.SaveSettlementMessageProcessor;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired private PendingCorebankProcessor pendingCorebankProcessor;
	@Autowired private CheckRequestMsgProcessor checkRequestMsgProcessor;
	@Autowired private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired private SaveSettlementMessageProcessor saveSettlementMessageProcessor;
	

	@Override
	public void configure() throws Exception {
		
		from("direct:receive").routeId("komi.inboundRoute")
			.process(checkRequestMsgProcessor) 
			.log(LoggingLevel.DEBUG,"komi.inboundRoute", "Disini ${header.hdr_msgType}")
			.log("=====*****=====")
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] ${header.hdr_msgType} received.")
		
			.choice().id("forward_msgtype")

				.when().simple("${header.hdr_msgType} == 'SETTLEMENT'")   // terima settlement
					.to("direct:settlement")
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == 'PROXYNOTIF'")  
					.to("direct:proxynotif")
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == '510'")   // terima account enquiry
					.to("direct:accountenq")

				.when().simple("${header.hdr_msgType} in '010,110'")    // terima credit transfer
					.to("direct:crdttransfer2")
//					.to("direct:crdttransfer")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '011'")     // reverse CT
					.to("direct:reverct")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.otherwise()	
					.log("[Inbound] Message ${header.hdr_msgType} tidak dikenal")
			.end()
	
			// kirim log notif ke Portal
			.filter().simple("${header.hdr_msgType} in '510,010,110,011' ")
				.setHeader("hdr_tmpbody", simple("${body}"))
				.to("seda:portalnotif?exchangePattern=InOnly")
				.setBody(simple("${header.hdr_tmpbody}"))
			.end()
				
			// jika CT yang mesti reversal
//			.choice()
//				.when().simple("${header.hdr_reversal} == 'PENDING'")
//					.to("seda:reversal?exchangePattern=InOnly")
//			.end()
		;

		from("seda:reversal")
			.setBody(simple("${header.hdr_frBIobj}"))
			.to("direct:reversal")
		;


	}
}
