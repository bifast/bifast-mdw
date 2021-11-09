package bifast.inbound.credittransfer;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.pojo.CbCreditRequestPojo;
import bifast.inbound.corebank.pojo.CbCreditResponsePojo;
import bifast.inbound.model.CreditTransfer;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.processor.DuplicateTransactionValidation;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferRoute extends RouteBuilder {
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private CreditTransferProcessor creditTransferProcessor;
	@Autowired private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private DuplicateTransactionValidation duplicationTrnsValidation;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat cbCreditTransferRequestJDF = jdfService.unwrapRoot(CbCreditRequestPojo.class);
		JacksonDataFormat cbCreditTransferResponseJDF = jdfService.basic(CbCreditResponsePojo.class);
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
		
		from("direct:crdttransfer").routeId("crdttransfer")
			.process(duplicationTrnsValidation)
			
			// cek apakah SAF atau bukan
			//flattening and check saf
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
								break;
							}
							else 
								cbSts = "RJCT";
						}
					}
					exchange.getMessage().setHeader("ct_saf", saf);
					exchange.getMessage().setHeader("ct_cbsts", cbSts);
					exchange.getMessage().setBody(flat);
				}
			})
			
			.log("status saf: ${header.ct_saf}")
			
			
			//TODO if SAF = NO/NEW --> call CB, set CBSTS = ACTC/RJCT
			.filter().simple("${header.ct_saf} in 'NO,NEW'")
				.log("akan call cb")
				.process(ctCorebankRequestProcessor)
				.setHeader("ct_cbrequest", simple("${body}"))
				.marshal(cbCreditTransferRequestJDF)
				.log("CB CT: ${body}")
				// send ke corebank
				.doTry()
					.setHeader("HttpMethod", constant("POST"))
					.enrich("http:{{komi.url.corebank}}/credit?"
//							+ "socketTimeout={{komi.timeout}}&" 
							+ "bridgeEndpoint=true",
							enrichmentAggregator)
					.convertBodyTo(String.class)
					.log("cb response ${body}")
					.unmarshal(cbCreditTransferResponseJDF)
					.setHeader("ct_cbsts", simple("${body.status}"))
				.doCatch(Exception.class)
					.log("Gagal ke corebank")
			    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
					.setHeader("ct_cbsts", constant("ERROR"))
					.setBody(constant(null))
				.end()
			.end()

			.log("cbsts: ${header.ct_cbsts}")		
								
			.process(creditTransferProcessor)

			//TODO if SAF=old/new and CBSTS=RJCT --> reversal
			.filter().simple("${header.ct_saf} != 'NO' && ${header.ct_cbsts} == 'RJCT'")
				.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Harus CT Reversal.")
				.setHeader("hdr_reversal", constant("YES"))
			.end()
	
			.removeHeaders("ct_*")

		;
	}

}
