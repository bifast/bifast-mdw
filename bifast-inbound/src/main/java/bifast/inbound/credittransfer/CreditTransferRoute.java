package bifast.inbound.credittransfer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.processor.DuplicateTransactionValidation;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferRoute extends RouteBuilder {
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private CreditTransferProcessor creditTransferProcessor;
	@Autowired private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private DuplicateTransactionValidation duplicationTrnsValidation;

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
			.to("seda:logandsave?exchangePattern=InOnly")
			.removeHeaders("*")
		;
		
		from("direct:crdttransfer").routeId("komi.ct")
			.process(duplicationTrnsValidation)
			
//			.log("KOMI_ID : ${header.hdr_process_data.komiTrnsId}")

			// cek apakah SAF atau bukan
			// check saf
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
					FlatPacs008Pojo flat = (FlatPacs008Pojo) processData.getBiRequestFlat();
					
					String saf = "NO";
					String cbSts = "";
					if ((null != flat.getCpyDplct()) && (flat.getCpyDplct().equals("DUPL")))
						saf =  "YES";
					
					if (saf.equals("YES")) {
						saf = "NEW";
						List<CreditTransfer> lCT = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flat.getBizMsgIdr());
						for (CreditTransfer ct : lCT) {
							if (ct.getReasonCode().equals("U149"))
								continue;
							if (ct.getResponseCode().equals("ACTC")) {
								cbSts = "ACTC";
								saf = "OLD";
								break;
							}
							else {
								cbSts = "RJCT";
								saf = "OLD";
							}
						}
					}				
					exchange.getMessage().setHeader("ct_saf", saf);
					exchange.getMessage().setHeader("ct_cbsts", cbSts);
					exchange.getMessage().setBody(flat);
				}
			})
			
			.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Status SAF: ${header.ct_saf}")
			
			// if SAF = NO/NEW --> call CB, set CBSTS = ACTC/RJCT
			.filter().simple("${header.ct_saf} in 'NO,NEW'")
				.log(LoggingLevel.DEBUG, "komi.ct", 
						"[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Akan call CB")

				.process(ctCorebankRequestProcessor)
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
	
			.removeHeaders("ct_*")

		;
	}

}
