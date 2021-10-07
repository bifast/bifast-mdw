package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.CBDebitInstructionResponsePojo;
import bifast.outbound.credittransfer.processor.BuildCTRequestProcessor;
import bifast.outbound.credittransfer.processor.CTCorebankRequestProcessor;
import bifast.outbound.credittransfer.processor.CreditTransferResponseProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.processor.FlatResponseProcessor;

@Component
public class CreditTransferRoute extends RouteBuilder {

	@Autowired
	private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired
	private BuildCTRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FlatResponseProcessor flatResponseProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:flatctreq").routeId("komi.flatct.start")
			.setHeader("ct_channelRequest", simple("${body}"))
			.process(crdtTransferProcessor)

			.setHeader("ct_birequest", simple("${body}"))	
			.to("direct:call-cihub")
			.setHeader("ct_biresponse", simple("${body}"))

			.process(flatResponseProcessor)
			.removeHeaders("ct*")

		;
		
		
		from("direct:ct_aepass").routeId("komi.ct.afterAERoute")
			.setHeader("ct_channelRequest", simple("${body}"))

			.log(LoggingLevel.DEBUG, "komi.ct.afterAERoute", "[ChnlReq:${header.hdr_chnlRefId}] direct:ct_aepass...")

			// invoke corebanking
			.setHeader("hdr_errorlocation", constant("corebank service call"))		
			.process(ctCorebankRequestProcessor)  // build request param
			
			.to("direct:callcb")
			
			.log("komitrxid di ct route: ${header.hdr_request_list.komiTrxId}" )

			.log("setelah cb: ${body.status}")
			.choice()
				.when().simple("${body.status} == 'SUCCESS'")
					.to("seda:ct_lolos_corebank")
				.endChoice()
				
				.when().simple("${body.status} == 'FAILED'")
					.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}] Corebank reject.")
					.setHeader("ct_failure_point", constant("CBRJCT"))
					.setHeader("hdr_error_status", constant("REJECT-CB"))
					
					.process(new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							CBDebitInstructionResponsePojo cbResp = exchange.getMessage().getBody(CBDebitInstructionResponsePojo.class);
							ChnlCreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("ct_channelRequest", ChnlCreditTransferRequestPojo.class);
							
							ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
							chnResponse.setOrignReffId(chnRequest.getOrignReffId());
							chnResponse.setStatus("RJCTCB");
							chnResponse.setAddtInfo(cbResp.getAddtInfo());
							
							ChannelResponseWrapper respWr = new ChannelResponseWrapper();
							respWr.setChnlCreditTransferResponse(chnResponse);
							exchange.getMessage().setBody(respWr);
						}
					})
				.endChoice()

			.end()

			.removeHeaders("ct_*")
			.removeHeaders("resp_*")
		;


		from("seda:ct_lolos_corebank").routeId("komi.ct.after_cbcall")
			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}] Corebank accept.")

			.process(crdtTransferProcessor)
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
					rmw.setCreditTransferRequest(bm);
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
			})
		
			// kirim ke CI-HUB
			.to("direct:call-cihub")

			.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}] setelah call CIHUB.")
			
    		// periksa apakah ada hasil dari call cihub
    		.filter().simple("${body.class} endsWith 'ChnlFailureResponsePojo' && ${body.errorCode} == 'TIMEOUT-CIHUB'")
    			.to("direct:sttlpymtsts")
    		.end()  
    		
//				Check hasil akhir setelah settlement dan PS
			.choice()
				.when().simple("${body.class} endsWith 'BusinessMessage'")
			    	.setHeader("hdr_error_status", constant(null))
			    	.setHeader("hdr_error_mesg", constant(null))
				.endChoice()
				
				.when().simple("${body.class} endsWith 'ChnlFailureResponsePojo'")
					.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}] setelah PS berisi Failure")
			    	.setHeader("hdr_error_status", constant(null))
			    	.setHeader("hdr_error_mesg", constant(null))
		    	.endChoice()
		    	
				.otherwise()
					.log(LoggingLevel.DEBUG, "komi.ct.after_cbcall", "[ChnlReq:${header.hdr_chnlRefId}] PS juga gagal.")
					.setHeader("ct_failure_point", constant("PSTIMEOUT"))
			    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(504))
			    	.setHeader("hdr_error_status", constant("TIMEOUT-CIHUB"))
			    	.setHeader("hdr_error_mesg", constant("Timeout waiting CIHUB response"))
		    	.endChoice()
			.end()
			
			// TODO jika response RJCT, harus reversal ke corebanking
			
//			// prepare untuk response ke channel
    		.setHeader("hdr_errorlocation", constant("CTRoute/ResponseProcessor"))
    		
			.process(crdtTransferResponseProcessor)
			
			.removeHeaders("ct_*")
		;
		
	}
}
