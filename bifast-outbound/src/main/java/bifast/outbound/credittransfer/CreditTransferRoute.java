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
	private FlattenIsoMessageService flattenIsoMessage;
	@Autowired
	private JacksonDataFormatService jdfService;

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
    
	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);
		
//		from("direct:crdttrns").routeId("komi.ct")
//			.setHeader("ct_channelRequest", simple("${body}"))
//				
//
//			.log(LoggingLevel.DEBUG, "komi.ct", "[ChnlReq:${header.hdr_chnlRefId}] direct:crdttrns...")
//
//			// invoke corebanking
//			.setHeader("hdr_errorlocation", constant("corebank service call"))		
//			.process(ctCorebankRequestProcessor)  // build request param
//			
//			.to("direct:callcb")
//			
//			.log("setelah cb: ${body.status}")
//			.choice()
//				.when().simple("${body.status} == 'SUCCESS'")
//					.to("seda:ct_aft_corebank")
//				.endChoice()
//				
//				.when().simple("${body.status} == 'FAILED'")
//					.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}][CT] Corebank reject.")
//					.setHeader("ct_failure_point", constant("CBRJCT"))
//					.setHeader("hdr_error_status", constant("REJECT-CB"))
//					
//					.process(new Processor() {
//						public void process(Exchange exchange) throws Exception {
//							ChnlCreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("ct_channelRequest", ChnlCreditTransferRequestPojo.class);
//							
//							ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
//							chnResponse.setOrignReffId(chnRequest.getChannelRefId());
//							
//							ChannelResponseWrapper respWr = new ChannelResponseWrapper();
//							respWr.setResponseCode("REJECT-CB");
//							respWr.setResponseMessage("REJECT-CB");
//							
//							respWr.setDate(LocalDateTime.now().format(dateformatter));
//							respWr.setTime(LocalDateTime.now().format(timeformatter));
//							respWr.setChnlCreditTransferResponse(chnResponse);
//							exchange.getMessage().setBody(respWr);
//						}
//					})
//				.endChoice()
//
//			.end()
//
//			.removeHeaders("ct_*")
//			.removeHeaders("resp_*")
//		;


		from("seda:ct_aft_corebank").routeId("komi.ct.aft_cbcall")
			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}][CT] After corebank accept.")
			
			.process(crdtTransferProcessor)
		
			// kirim ke CI-HUB
			.to("direct:call-cihub")

			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}][CT] setelah call CIHUB.")
			
			.log("sesudah call cihub ${body.class}")

			// TODO jika response RJCT, harus reversal ke corebanking

			// jika timeout, check settlement dulu
			.filter().simple("${body.class} endsWith 'ChnlFailureResponsePojo' && ${body.reasonCode} == 'K000'")
				.log(LoggingLevel.DEBUG, "komi.ct.aft_cbcall", "[ChnlReq:${header.hdr_chnlRefId}][CT] akan cari Settlement.")
				.to("seda:caristtl")
			.end()
			
			.process(saveCrdtTrnsProcessor)
    		
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
