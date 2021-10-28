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
		
		from("seda:ct_aft_corebank").routeId("komi.ct")
			
			.setHeader("ct_progress", constant("Start"))
			
			// FILTER-A debit-account dulu donk untuk e-Channel
			.filter().simple("${header.hdr_request_list.merchantType} != '6010' ")
				.setHeader("ct_progress", constant("CB"))
				.process(buildDebitRequestProcessor)
				.to("seda:callcb")
			.end()
		
			// periksa hasil debit-account
			.choice()
				.when().simple("${header.ct_progress} == 'CB' && ${body.class} endsWith 'FaultPojo' ")
					.setHeader("ct_progress", constant("STOP"))
				.when().simple("${header.ct_progress} == 'CB' && ${body.class} endsWith 'CBDebitResponsePojo' ")
					.setHeader("ct_progress", constant("LANJUTCIHUB"))
				.otherwise()
					.setHeader("ct_progress", constant("LANJUTCIHUB"))
			.end()
			.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CT] setelah corebank, ${header.ct_progress}.")
		
			.filter().simple("${header.ct_progress} == 'LANJUTCIHUB'")

				.process(crdtTransferProcessor)
				.to("direct:call-cihub")
				.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] setelah call CIHUB")
				.log("ReasonCode: ${body.reasonCode}")
				
			.end()

			// periksa hasil call cihub
			.choice()
				.when().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} == 'K000'")
					.setHeader("ct_progress", constant("SETTLEMENT"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.reasonCode} == 'U900'")
					.setHeader("ct_progress", constant("SETTLEMENT"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.transactionStatus} == 'ACTC'")
					.setHeader("ct_progress", constant("SUCCESS"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.transactionStatus} != 'ACTC'")
					.setHeader("ct_progress", constant("REJECT"))
			.end()
			
			.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CT] hasil cihub, ${header.ct_progress}.")

			// FILTER-C: jika timeout, check settlement dulu
//			.filter().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} == 'K000'")
			.filter().simple("${header.ct_progress} == 'SETTLEMENT'")
				.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan cari Settlement.")
				.to("seda:caristtl")
			.end()
			
			//TODO jika ada settlmenet, progress SUCCESS

			.log("4 ${header.ct_progress}")
			.filter().simple("${header.ct_progress} == 'REJECT'")
				.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CT] akan reversal.")
				// TODO jika response RJCT, harus reversal ke corebanking
			.end()

			.filter().simple("${header.hdr_request_list.creditTransferRequest} != null")
				.process(saveCrdtTrnsProcessor)
    		.end()
		
    		.log("${header.ct_progress}")
    		.log("${body}")
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
