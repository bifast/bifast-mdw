package bifast.outbound.credittransfer;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.pojo.CBDebitRequestPojo;
import bifast.outbound.corebank.pojo.CBDebitResponsePojo;
import bifast.outbound.credittransfer.processor.BuildCBDebitRequestProcessor;
import bifast.outbound.credittransfer.processor.BuildCTRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.credittransfer.processor.FindingSettlementProcessor;
import bifast.outbound.credittransfer.processor.StoreCreditTransferProcessor;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.service.FlattenIsoMessageService;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private BuildCTRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private StoreCreditTransferProcessor saveCrdtTrnsProcessor;
	@Autowired
	private FindingSettlementProcessor findingSettlementProcessor;
	@Autowired
	private BuildCBDebitRequestProcessor buildDebitRequestProcessor;
	@Autowired
	private FlattenIsoMessageService flattenIsoMessage;
	@Autowired
	private JacksonDataFormatService jdfService;

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
    
	@Override
	public void configure() throws Exception {
		JacksonDataFormat cbDebitRequestJDF = jdfService.basic(CBDebitRequestPojo.class);
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
		from("seda:ct_aft_corebank").routeId("komi.ct.aft_cbcall")
			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] After corebank accept.")
			
			// TODO debit-account dulu donk
			.process(buildDebitRequestProcessor)
			.to("seda:callcb")
			.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] request Core: ${body}")

			.log("after call cb: ${body.class}")
			
			.filter().simple("${body.class} endsWith 'CBDebitResponsePojo'")

				.log("status corebank sukses")
				// kirim ke CI-HUB
				.process(crdtTransferProcessor)
				.to("direct:call-cihub")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] setelah call CIHUB")
				.log("sesudah call cihub ${body.class}")
				.log("ReasonCode: ${body.reasonCode}")
				
			.end()
			

			// TODO jika response RJCT, harus reversal ke corebanking

			// jika timeout, check settlement dulu
			.filter().simple("${body.class} endsWith 'ChnlFailureResponsePojo' && ${body.reasonCode} == 'K000'")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan cari Settlement.")
				.to("seda:caristtl")
			.end()
			// termasuk timeout jike response ci-hub reason_code "U900"
			
//			.process(saveCrdtTrnsProcessor)
    		
			.process(crdtTransferResponseProcessor)
			
			.removeHeaders("ct_*")
			
			// return berupa class BusinessMessage atau ChnlFailureResponsePojo
		;
		

		from("seda:caristtl")
			.log("dicari settlement-nya")
			.setHeader("tmp_body", simple("${body}"))

			.process(findingSettlementProcessor)

			.filter().simple("${body} != null")
				.unmarshal().base64()
				.unmarshal().zipDeflater()
				.unmarshal(businessMessageJDF)
				
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
						FlatPacs002Pojo pacs002 = flattenIsoMessage.flatteningPacs002(bm);
						exchange.getMessage().setBody(pacs002);
						ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list",ResponseMessageCollection.class);
						rmc.setFault(null);
						exchange.getMessage().setHeader("hdr_response_list", rmc);
					}
				})
			.end()
			
			.filter().simple("${body} == null")
				.setBody(simple("${header.tmp_body}"))
			.end()
			
			.removeHeader("tmp_body")
		;
		
	}
}
