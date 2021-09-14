package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.processor.AccountEnquiryProcessor;
import bifast.inbound.processor.CheckMessageTypeProcessor;
import bifast.inbound.processor.CreditTransferProcessor;
import bifast.inbound.processor.FICreditTransferProcessor;
import bifast.inbound.processor.SaveInboundMessageProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired
	private SaveInboundMessageProcessor saveInboundMessageProcessor;
	@Autowired
	private CheckMessageTypeProcessor checkMsgTypeProcessor;
	@Autowired
	private FICreditTransferProcessor fICreditTransferProcessor;
	@Autowired
	private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired
	private CreditTransferProcessor creditTransferProcessor;
	
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
		
		from("direct:receive").routeId("receive")
			.convertBodyTo(String.class)
			
			// simpan msg inbound compressed
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_frBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))

			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage

			.setHeader("req_cihubRequestTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_frBIobj", simple("${body}"))   // pojo BusinessMessage simpan ke header

			.setHeader("hdr_msgName", simple("${body.appHdr.msgDefIdr}"))
			
			.process(checkMsgTypeProcessor)   // set header.hdr_msgType
			.choice()

				.when().simple("${header.hdr_msgType} == 'SETTLEMENT'")   // terima settlement
					.log("Simpan ke table settlement")
					.setBody(constant(null))
					
				.when().simple("${header.hdr_msgType} == '510'")   // terima account enquiry
					.log("akan kirim response account enquiry")
					.process(accountEnquiryProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '010'")    // terima credit transfer
					.log("akan proses Credit Transfer Request")
					.process(creditTransferProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '011'")     // reverse CT
					.log("akan proses Reversal Credit Transfer Request")
					.process(creditTransferProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '019'")     // FI CT
					.log("akan proses FI to FI Reversal Credit Transfer")
					.process(fICreditTransferProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))


//				.when().simple("${header.rcv_msgtype} == 'SYSNOTIF'")
//					.log("akan proses System Notification")
//					.to("direct:sysnotif")

				.otherwise()	
					.log("Message tidak dikenal")
			.end()

			.choice()
				.when().simple("${header.hdr_msgType} != 'SETTLEMENT'")   
					.marshal(jsonBusinessMessageDataFormat) 
					// simpan outbound compress
					.setHeader("hdr_tmp", simple("${body}"))
					.marshal().zipDeflater()
					.marshal().base64()
					.setHeader("hdr_toBI_jsonzip", simple("${body}"))
					.setBody(simple("${header.hdr_tmp}"))
			.end()
			
			.setHeader("req_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))

			.to("seda:logandsave?exchangePattern=InOnly")
			
			.choice()
				.when().simple("${header.resp_reversal} == 'PENDING'")
					.to("seda:reversal?exchangePattern=InOnly")
			.end()
			
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeaders("resp_*")
			.log("output response selesai")
			
		;

		from("seda:reversal")
			.setBody(simple("${header.hdr_frBIobj}"))
			.to("direct:reversal")
		;

		from("seda:logandsave")
			.log("Save tables")
			.process(saveInboundMessageProcessor)
		;


	}
}
