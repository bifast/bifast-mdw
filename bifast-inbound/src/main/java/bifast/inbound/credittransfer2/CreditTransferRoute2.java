package bifast.inbound.credittransfer2;


import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.BuildAERequestForCbProcessor;
import bifast.inbound.credittransfer.CTCorebankRequestProcessor;
import bifast.inbound.credittransfer.CheckSAFStatusProcessor;
import bifast.inbound.credittransfer.SaveCreditTransferProcessor;
import bifast.inbound.processor.DuplicateTransactionValidation;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferRoute2 extends RouteBuilder {
	@Autowired private BuildAERequestForCbProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private CreditTransfer2Processor creditTransferProcessor;
	@Autowired private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private DuplicateTransactionValidation duplicationTrnsValidation;
	@Autowired private SaveCreditTransferProcessor saveCreditTransferProcessor;
	@Autowired private CheckSAFStatusProcessor checkSafStatus;
	
	@Override
	public void configure() throws Exception {
		JacksonDataFormat businessMessageJDF = jdfService.wrapRoot(BusinessMessage.class);

		onException(Exception.class)
			.log("Route level onException")
			.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.marshal(businessMessageJDF)
			.setHeader("hdr_tmp", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()
			.setHeader("hdr_toBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.hdr_tmp}"))
			.log("${body}")
			.handled(true)
			.to("seda:save_ct?exchangePattern=InOnly")
			.removeHeaders("*")
		;
		
		from("direct:crdttransfer2").routeId("komi.ct2")
			.process(duplicationTrnsValidation)
			
//			.log("KOMI_ID : ${header.hdr_process_data.komiTrnsId}")

			// cek apakah SAF atau bukan
			// check saf
			.process(checkSafStatus)
			
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Status SAF: ${header.ct_saf}")
			
			// if SAF = NO/NEW --> call CB, set CBSTS = ACTC/RJCT
			.filter().simple("${header.ct_saf} in 'NO,NEW'")
				.log(LoggingLevel.DEBUG, "komi.ct", 
						"[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Akan call CB")

				.process(buildAccountEnquiryRequestProcessor)
//				.process(ctCorebankRequestProcessor)

				.setHeader("ct_cbrequest", simple("${body}"))
				// send ke corebank
				.to("seda:callcb")
				
				.choice()
					.when().simple("${body.class} endsWith 'FaultPojo'")
						.setHeader("ct_cbsts", constant("ERROR"))
					.endChoice()
					.otherwise()
						.setHeader("ct_cbsts", simple("${body.status}"))
					.endChoice()
				.end()
			.end()
					
			.process(creditTransferProcessor)
			
			.log(LoggingLevel.DEBUG, "komi.ct", 
					"[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] saf ${header.ct_saf}, cb_sts ${header.ct_cbsts}")

			//if SAF=old/new and CBSTS=RJCT --> reversal
			//jika saf dan corebank error, ct proses harus diulang: reversal = UNDEFINED
			.filter().simple("${header.ct_saf} != 'NO'")
				.choice()
					.when().simple("${header.ct_cbsts} == 'RJCT'")
						.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Harus CT Reversal.")
						.setHeader("hdr_reversal", constant("YES"))
					.endChoice()
					.when().simple("${header.ct_cbsts} == 'ERROR'")
						.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Apakah CT Reversal?")
						.setHeader("hdr_reversal", constant("UNDEFINED"))
					.endChoice()
				.end()
			.end()
	
			.to("seda:save_ct")
			.removeHeaders("ct_*")

		;
	
		from("seda:save_ct?concurrentConsumers=2").routeId("savect")
			.setExchangePattern(ExchangePattern.InOnly)
			.log("Akan save ${header.hdr_msgType}")
			.process(saveCreditTransferProcessor)
			.end()
		;

	}
}
