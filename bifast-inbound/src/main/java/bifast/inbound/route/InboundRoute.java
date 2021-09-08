package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.processor.AccountEnquiryResponseProcessor;
import bifast.inbound.processor.CheckMessageTypeProcessor;
import bifast.inbound.processor.CombineLogMessageProcessor;
import bifast.inbound.processor.CreditTransferResponseProcessor;
import bifast.inbound.processor.FICrdtTrnResponseProcessor;
import bifast.inbound.processor.ReverseCTResponseProcessor;
import bifast.inbound.processor.SaveTracingTableProcessor;
import bifast.inbound.processor.SaveInboundMessageProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired
	private SaveInboundMessageProcessor saveInboundMessageProcessor;
	@Autowired
	private CombineLogMessageProcessor combineMesgProcessor;
	@Autowired
	private SaveTracingTableProcessor saveTracingTableProcessor;
	@Autowired
	private CheckMessageTypeProcessor checkMsgTypeProcessor;
	@Autowired
	private FICrdtTrnResponseProcessor fiCrdtTrnResponseProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor acctEnqResponseProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTrnResponseProcessor;
	@Autowired
	private ReverseCTResponseProcessor reverseCTResponseProcessor;
	
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
					.process(acctEnqResponseProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '010'")
					.log("akan kirim response Credit Transfer Request")
					.process(crdtTrnResponseProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '019'")
					.log("akan proses FI to FI Reversal Credit Transfer")
					.process(fiCrdtTrnResponseProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

				.when().simple("${header.hdr_msgType} == '011'")
					.log("akan proses Reversal Credit Transfer Request")
					.process(reverseCTResponseProcessor)
					.setHeader("hdr_toBIobj", simple("${body}"))

//				.when().simple("${header.rcv_msgtype} == 'SYSNOTIF'")
//					.log("akan proses System Notification")
//					.to("direct:sysnotif")

				.otherwise()	
					.log("Message tidak dikenal")
			.end()

			.choice()
				.when().simple("${header.hdr_msgType} != 'SETTLEMENT'")   // terima settlement
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
			
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.log("output response selesai")
		;

		from("seda:logandsave")
			.log("Save tables")
			.process(saveInboundMessageProcessor)
//			.process(saveTracingTableProcessor)
//			.process(combineMesgProcessor)
//			.log("akan simpan log ke {{bifast.inbound-log-folder}}")
//			.toD("file:{{bifast.inbound-log-folder}}?fileName=${header.rcv_fileName}")
		;


	}
}
