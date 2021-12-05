package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundJsonRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat jsonBusinessMessageDataFormat = jdfService.unwrapRoot(BusinessMessage.class);

		
		restConfiguration()
			.component("servlet")
		;
			
		rest("/json")
			.post("/service")
				.consumes("application/json")
				.to("direct:parsejson")
		;


		from("direct:parsejson").routeId("komi.jsonEndpoint")
			.convertBodyTo(String.class)
			.setHeader("hdr_inputformat", constant("json"))
			
			// simpan msg inbound compressed
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_frBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))
			
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("hdr_frBIobj", simple("${body}"))   // pojo BusinessMessage simpan ke header

			.to("direct:receive")
			
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
	
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] completed.")
			.removeHeaders("*")

			
		;


	}
}
