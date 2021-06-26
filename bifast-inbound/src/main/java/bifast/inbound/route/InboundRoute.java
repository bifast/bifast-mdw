package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.service.Pacs008Processor;
import bifast.inbound.service.SaveSettlementTableProcessor;
import bifast.inbound.service.StoreInboundProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired
	private StoreInboundProcessor storeInboundProcessor;
	@Autowired
	private SaveSettlementTableProcessor saveSettlementProcessor;
	@Autowired
	private Pacs008Processor pacs008Processor;

	JacksonDataFormat jsonBusinessMessageDataFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJson() {
		jsonBusinessMessageDataFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
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
				.consumes("application/json")
				.to("direct:receive")
			;
		
		
		from("direct:receive").routeId("receive")
			.convertBodyTo(String.class)
			.setHeader("rcv_json", simple("${body}"))  // simpan versi json dari inputan
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("rcv_obj", simple("${body}"))   // pojo BusinessMessage simpan ke header
			.setHeader("rcv_msgname", simple("${body.appHdr.msgDefIdr}"))
			.process(storeInboundProcessor)
			
			.choice()

				.when().simple("${header.rcv_msgname} startsWith 'pacs.002'")   // terima settlement
					.log("Simpan ke table settlement")
					.setBody(simple("${header.rcv_obj}"))
					.process(saveSettlementProcessor)  // save ke table settlement_prc
					.setBody(constant("{}"))
					
				.when().simple("${header.rcv_msgname} startsWith 'pacs.008'")  // Account Enquiry or Credit Transfer PA
					.log("Terima pacs.008")
					.process(pacs008Processor)
//					.marshal(jsonBusinessMessageDataFormat)
					
				.when().simple("${header.rcv_msgname} startsWith 'pacs.009'")  // FI TO FI Credit Transfer Request
					.log("Terima pacs.009")
//					.to("seda:pacs009")
//					.marshal(jsonBusinessMessageDataFormat)
//
				.otherwise()	
					.log("Message tidak dikenal")
			.end()
			
			.removeHeaders("apphdr_*")
			.removeHeaders("rcv_*")
		;

	
	}
}
