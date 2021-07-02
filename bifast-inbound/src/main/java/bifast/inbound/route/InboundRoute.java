package bifast.inbound.route;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.processor.AccountEnquiryResponseProcessor;
import bifast.inbound.processor.CombineLogMessageProcessor;
import bifast.inbound.processor.CreditTransferResponseProcessor;
import bifast.inbound.processor.FICrdtTrnResponseProcessor;
import bifast.inbound.processor.Pacs008Processor;
import bifast.inbound.processor.ReverseCTResponseProcessor;
import bifast.inbound.processor.SaveSettlementTableProcessor;
import bifast.inbound.processor.StoreInboundProcessor;
import bifast.library.iso20022.custom.BusinessMessage;


@Component
public class InboundRoute extends RouteBuilder {

	@Autowired
	private StoreInboundProcessor storeInboundProcessor;
	@Autowired
	private CombineLogMessageProcessor combineMesgProcessor;
	@Autowired
	private SaveSettlementTableProcessor saveSettlementProcessor;
	@Autowired
	private Pacs008Processor pacs008Processor;
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

			.unmarshal(jsonBusinessMessageDataFormat)  // ubah ke pojo BusinessMessage
			.setHeader("rcv_bi", simple("${body}"))   // pojo BusinessMessage simpan ke header
			.setHeader("rcv_msgname", simple("${body.appHdr.msgDefIdr}"))

			.choice()

				.when().simple("${header.rcv_msgname} startsWith 'pacs.002'")   // terima settlement
					.log("Simpan ke table settlement")
					
//					.to("seda:settlement")
//					.process(saveSettlementProcessor)  // save ke table settlement_prc
										
					// TODO response apa harus kirim setelah terima settlement ?
					.setHeader("resp_jsonbi",constant(""))   

				.when().simple("${header.rcv_msgname} startsWith 'pacs.008'")  // Account Enquiry or Credit Transfer PA
					.log("Terima pacs.008")
					.process(pacs008Processor)
					
					.choice()
						.when().simple("${header.rcv_msgtype} == 'ACCTENQR'")
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

						.when().simple("${header.rcv_msgtype} == 'REVCRDTTRN'")
							.log("akan proses Reversal Credit Transfer Request")
							.process(reverseCTResponseProcessor)
							.setHeader("resp_bi", simple("${body}"))
							.marshal(jsonBusinessMessageDataFormat)
							.setHeader("resp_jsonbi",simple("${body}"))

					.endChoice()
					
				.when().simple("${header.rcv_msgname} startsWith 'pacs.009'")  // FI TO FI Credit Transfer Request
					.log("Terima pacs.009")
					.process(fiCrdtTrnResponseProcessor)
					.setHeader("resp_bi", simple("${body}"))
					.marshal(jsonBusinessMessageDataFormat)
					.setHeader("resp_jsonbi",simple("${body}"))

				.otherwise()	
					.log("Message tidak dikenal")
			.end()
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:final")
			
			.setBody(simple("${header.resp_jsonbi}"))
			.removeHeaders("rcv_*")
			.removeHeaders("resp_*")
			.log("output response selesai")
		;

		from("seda:settlement")
			.process(saveSettlementProcessor)  // save ke table settlement_prc
			.setBody(constant("{}"))
		;

		from("seda:final")
			.log("tes delay 10s")
			.delay(10000)
			.log("  lanjut")
			.process(storeInboundProcessor)
			.process(combineMesgProcessor)
			.toD("file:{{bifast.inbound-log-folder}}?fileName=${header.rcv_fileName}")
		;


	}
}
