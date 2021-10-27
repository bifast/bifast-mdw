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
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
		from("seda:ct_aft_corebank").routeId("komi.ct.aft_cbcall")
			
			// TODO debit-account dulu donk untuk e-Channel
			.filter().simple("${header.hdr_request_list.merchantType} != '6010' ")
				.process(buildDebitRequestProcessor)
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] request corebank: ${body}")
				.to("seda:callcb")
			.end()
		
//			.filter().simple("${body.class} endsWith 'CBDebitResponsePojo' && ${body.status} == 'ACTC'")
			.filter().simple("${header.hdr_response_list.fault} == null")

				.log("Akan panggil cihub")
				// kirim ke CI-HUB
				.process(crdtTransferProcessor)
				.to("direct:call-cihub")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] setelah call CIHUB")
				.log("ReasonCode: ${body.reasonCode}")
				
			.end()

			// jika timeout, check settlement dulu
			.filter().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} == 'K000'")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan cari Settlement.")
				.to("seda:caristtl")
			.end()

			// termasuk timeout jike response ci-hub reason_code "U900"
			.filter().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.reasonCode} == 'U900'")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan cari Settlement.")
				.to("seda:caristtl")
			.end()

			// jika REJECT maka harus reversal
			.filter().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.transactionStatus} != 'ACTC'")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan reversal.")
				// TODO jika response RJCT, harus reversal ke corebanking
			.end()

			.filter().simple("${header.hdr_request_list.creditTransferRequest} != null")
				.process(saveCrdtTrnsProcessor)
    		.end()
		
			.process(crdtTransferResponseProcessor)
			
			.removeHeaders("ct_*")
			
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
						rmc.setSettlement(pacs002);
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
