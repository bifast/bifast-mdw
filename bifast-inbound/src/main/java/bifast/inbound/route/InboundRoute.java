package bifast.inbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.accountenquiry.SaveAccountEnquiryProcessor;
import bifast.inbound.credittransfer.CreditTransferProcessor;
import bifast.inbound.credittransfer.SaveCreditTransferProcessor;
import bifast.inbound.processor.SaveSettlementMessageProcessor;
import bifast.inbound.processor.SettlementProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired
	private SaveCreditTransferProcessor saveCreditTransferProcessor;
	@Autowired
	private SaveSettlementMessageProcessor saveSettlementMessageProcessor;
	@Autowired
	private CreditTransferProcessor creditTransferProcessor;
	@Autowired
	private SettlementProcessor settlementProcessor;
	
	JacksonDataFormat jsonBusinessMessageDataFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJson() {
		jsonBusinessMessageDataFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.setInclude("NON_NULL");
		jsonBusinessMessageDataFormat.setInclude("NON_EMPTY");
	}
	
	@Override
	public void configure() throws Exception {
		configureJson();
		
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

			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage inputMsg = exchange.getMessage().getBody(BusinessMessage.class);
					String trnType = inputMsg.getAppHdr().getBizMsgIdr().substring(16,19);
					if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("pacs.002")) 
						trnType = "SETTLEMENT";
					else if (inputMsg.getAppHdr().getMsgDefIdr().startsWith("prxy.901"))
						trnType = "PROXYNOTIF";
					exchange.getMessage().setHeader("hdr_msgType", trnType);
				} }).id("process1")
			
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] received.")

			.choice().id("forward_msgtype")

				.when().simple("${header.hdr_msgType} == 'SETTLEMENT'")   // terima settlement
					.process(settlementProcessor)
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == 'PROXYNOTIF'")   // terima settlement
					.setBody(constant(null))

				.when().simple("${header.hdr_msgType} == '510'")   // terima account enquiry
					.to("direct:accountenq")
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '010'")    // terima credit transfer
					.to("direct:crdttransfer")
					.setHeader("hdr_toBIobj", simple("${body}"))

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
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("resp_*")
			.removeHeader("HttpMethod")
		
			
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
