package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.processor.CheckRequestMsgProcessor;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired private CheckRequestMsgProcessor checkRequestMsgProcessor;
	

	@Override
	public void configure() throws Exception {
		
		from("direct:receive").routeId("komi.inboundRoute")
			.process(checkRequestMsgProcessor) 
			.log("[${header.hdr_process_data.inbMsgName}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] received.")
		
			.choice().id("forward_msgtype")

				.when().simple("${header.hdr_process_data.inbMsgName} == 'Settl'")   // terima settlement
					.to("direct:settlement")
					.setBody(constant(null))

				.when().simple("${header.hdr_process_data.inbMsgName} == 'PrxNtf'")  
					.to("direct:proxynotif")
					.setBody(constant(null))

				.when().simple("${header.hdr_process_data.inbMsgName} == 'AccEnq'")   // terima account enquiry
					.to("direct:accountenq")

				.when().simple("${header.hdr_process_data.inbMsgName} == 'CrdTrn'")    // terima credit transfer
					.to("direct:crdttransfer2")
//					.to("direct:crdttransfer")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_process_data.inbMsgName} == 'RevCT'")     // reverse CT
					.to("direct:reverct")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.otherwise()	
					.log("[Inbound] Message ${header.hdr_process_data.inbMsgName} tidak dikenal")
			.end()
	
			// kirim log notif ke Portal
			.filter().simple("${header.hdr_process_data.inbMsgName} in 'AccEnq,CrdTrn,RevCT' ")
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
