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
			.setHeader("rcv_jsonbi", simple("${body}"))
			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			
			.setHeader("rcv_bi", simple("${body}"))   // pojo BusinessMessage simpan ke header
			.setHeader("rcv_msgname", simple("${body.appHdr.msgDefIdr}"))
			
			.process(checkMsgTypeProcessor)   // set header.rcv_msgType
			.choice()

				.when().simple("${header.rcv_msgType} == 'SETTLEMENT'")   // terima settlement
					.log("Simpan ke table settlement")
					
					// TODO response apa harus kirim setelah terima settlement ?
					.setHeader("resp_jsonbi",constant(""))   

				.when().simple("${header.rcv_msgType} == 'ACCTENQR'")   // terima settlement
					.log("akan kirim response account enquiry")
					.process(acctEnqResponseProcessor)
					.setHeader("resp_bi", simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.setHeader("resp_jsonbi",simple("${body}"))

				.when().simple("${header.rcv_msgtype} == 'CRDTTRN'")
					.log("akan kirim response Credit Transfer Request")
					.process(crdtTrnResponseProcessor)
					.setHeader("resp_bi", simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.setHeader("resp_jsonbi",simple("${body}"))

				.when().simple("${header.rcv_msgtype} == 'FICDTTRN'")
					.log("akan proses FI to FI Reversal Credit Transfer")
					.process(fiCrdtTrnResponseProcessor)
					.setHeader("resp_bi", simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.setHeader("resp_jsonbi",simple("${body}"))

				.when().simple("${header.rcv_msgtype} == 'REVCRDTTRN'")
					.log("akan proses Reversal Credit Transfer Request")
					.process(reverseCTResponseProcessor)
					.setHeader("resp_bi", simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.setHeader("resp_jsonbi",simple("${body}"))

//				.when().simple("${header.rcv_msgtype} == 'SYSNOTIF'")
//					.log("akan proses System Notification")
//					.to("direct:sysnotif")

				.otherwise()	
					.log("Message tidak dikenal")
			.end()
			
			.to("seda:logandsave?exchangePattern=InOnly")
			
			.setBody(simple("${header.resp_jsonbi}"))
			.removeHeaders("rcv_*")
			.removeHeaders("resp_*")
			.log("output response selesai")
		;

		from("seda:logandsave")
			.process(saveInboundMessageProcessor)
			.process(saveTracingTableProcessor)
			.process(combineMesgProcessor)
			.log("akan simpan log ke {{bifast.inbound-log-folder}}")
			.toD("file:{{bifast.inbound-log-folder}}?fileName=${header.rcv_fileName}")
		;


	}
}
