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
			
			// FILTER-A debit-account corebank dulu donk untuk e-Channel
			.filter().simple("${header.hdr_request_list.merchantType} != '6010' ")
				.setHeader("ct_progress", constant("CB"))
				.process(buildDebitRequestProcessor)
				.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CTReq] call Corebank: ${body}")
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
			.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CTReq] setelah corebank, ${header.ct_progress}.")
		
			.filter().simple("${header.ct_progress} == 'LANJUTCIHUB'")
				.process(crdtTransferProcessor)
				.to("direct:call-cihub")
				.process(saveCrdtTrnsProcessor)

			.end()

			// periksa hasil call cihub
			.choice()
				.when().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} == 'K000'")
					.setHeader("ct_progress", constant("TIMEOUT"))
				.when().simple("${body.class} endsWith 'FaultPojo' && ${body.reasonCode} != 'K000'")
					.setHeader("ct_progress", constant("ERROR"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.reasonCode} == 'U900'")
					.setHeader("ct_progress", constant("TIMEOUT"))
				.when().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.transactionStatus} == 'ACTC'")
					.setHeader("ct_progress", constant("SUCCESS"))
				.otherwise()
					.setHeader("ct_progress", constant("REJECT"))
			.end()
			
			.log("[ChnlReq:${header.hdr_request_list.requestId}][CTReq] hasil cihub, ${header.ct_progress}.")

			// FILTER-C: jika timeout, check settlement dulu
			.filter().simple("${header.ct_progress} == 'TIMEOUT'")
				.log("[ChnlReq:${header.hdr_request_list.requestId}][CTReq] cari Settlement.")
				.to("seda:caristtl")
			.end()
			
			//cek hasil find settlement 
			.filter().simple("${body.class} endsWith 'FlatPacs002Pojo' && ${body.bizSvc} == 'STTL'")
				.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_request_list.requestId}][CTReq] dapat Settlement.")
				.setHeader("ct_progress", constant("SUCCESS"))
			.end()

			.log("[ChnlReq:${header.hdr_request_list.requestId}][CTReq] B, ${header.ct_progress}.")

			// jika response RJCT/ERROR, harus reversal ke corebanking
			.filter().simple("${header.ct_progress} in 'REJECT,ERROR'")
				.log("akan reversal")
				.to("seda:debitreversal")
			.end()
	
			.process(crdtTransferResponseProcessor)
			
			.removeHeaders("ct_*")
			
		;
		

		from("seda:caristtl").routeId("komi.findsttl")
			.log(LoggingLevel.DEBUG, "komi.findsttl", "[ChnlReq:${header.hdr_request_list.requestId}][CTReq] sedang cari settlement.")
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
