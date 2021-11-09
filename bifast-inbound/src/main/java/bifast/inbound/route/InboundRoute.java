package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.SaveAccountEnquiryProcessor;
import bifast.inbound.credittransfer.SaveCreditTransferProcessor;
import bifast.inbound.processor.CheckRequestMsgProcessor;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.inbound.settlement.SaveSettlementMessageProcessor;
import bifast.inbound.settlement.SettlementProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired private SaveCreditTransferProcessor saveCreditTransferProcessor;
	@Autowired private SaveSettlementMessageProcessor saveSettlementMessageProcessor;
	@Autowired private CheckRequestMsgProcessor checkRequestMsgProcessor;
	@Autowired private SettlementProcessor settlementProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	

	@Override
	public void configure() throws Exception {
		JacksonDataFormat jsonBusinessMessageDataFormat = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
		restConfiguration()
			.component("servlet")
		;
			
		rest("/api")
			.post("/inbound")
				.description("REST listener untuk terima message")
				.consumes("application/json")
				.to("direct:receive")
		;	
		
		from("direct:receive").routeId("komi.inboundEndpoint").messageHistory()
			.convertBodyTo(String.class).id("start_route")
			
			// simpan msg inbound compressed
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_frBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}")).id("process_encr_request")
			
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("hdr_frBIobj", simple("${body}"))   // pojo BusinessMessage simpan ke header

			.process(checkRequestMsgProcessor) 
			.id("process1")
			
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] received.")
		
			.choice().id("forward_msgtype")

				.when().simple("${header.hdr_msgType} == 'SETTLEMENT'")   // terima settlement
					.to("direct:settlement")
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == 'PROXYNOTIF'")  
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == '510'")   // terima account enquiry
					.to("direct:accountenq")

				.when().simple("${header.hdr_msgType} == '010'")    // terima credit transfer
					.to("direct:crdttransfer")
//					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '110'")    // terima credit transfer with proxy
					.to("direct:crdttransfer")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '011'")     // reverse CT
					.to("direct:reverct")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.otherwise()	
					.log("[Inbound] Message ${header.hdr_msgType} tidak dikenal")
			.end()

			// selain SETTLEMENT di zip dulu untuk save table
			.filter().simple("${header.hdr_msgType} !in 'SETTLEMENT, PROXYNOTIF'")
				.marshal(jsonBusinessMessageDataFormat) 
				// simpan outbound compress
				.setHeader("hdr_tmp", simple("${body}"))
				.marshal().zipDeflater()
				.marshal().base64()
				.setHeader("hdr_toBI_jsonzip", simple("${body}"))
				.setBody(simple("${header.hdr_tmp}"))
			.end()
			
			.to("seda:logandsave?exchangePattern=InOnly")
			.id("end_route")

			// jika CT yang mesti reversal
			.choice()
				.when().simple("${header.resp_reversal} == 'PENDING'")
					.to("seda:reversal?exchangePattern=InOnly")
			.end()
			
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] completed.")
			.removeHeaders("*")
		
		;

		from("seda:reversal")
			.setBody(simple("${header.hdr_frBIobj}"))
			.to("direct:reversal")
		;

		from("seda:logandsave").routeId("savedb")
			.choice()
			.when().simple("${header.hdr_msgType} == 'SETTLEMENT'")   // terima settlement
				.process(saveSettlementMessageProcessor)
				
			.when().simple("${header.hdr_msgType} == '510'")  // account enquiry
				.process(saveAccountEnquiryProcessor)
			.otherwise()
				.log("${header.hdr_msgType}")
				.process(saveCreditTransferProcessor)
			.end()
		;


	}
}
