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
import bifast.inbound.pojo.flat.FlatPacs008Pojo;
import bifast.inbound.processor.DuplicateTransactionValidation;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.repository.CreditTransferRepository;
import bifast.inbound.service.FlattenIsoMessageService;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
public class CreditTransferRoute extends RouteBuilder {
	@Autowired private FlattenIsoMessageService flatteningIsoMessageService;
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private CreditTransferProcessor creditTransferProcessor;
	@Autowired private CTCorebankRequestProcessor ctCorebankRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private DuplicateTransactionValidation duplicationTrnsValidation;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat cbCreditTransferRequestJDF = jdfService.wrapRoot(CbCreditRequestPojo.class);
		JacksonDataFormat cbCreditTransferResponseJDF = jdfService.unwrapRoot(CbCreditResponsePojo.class);
		JacksonDataFormat businessMessageJDF = jdfService.wrapRoot(BusinessMessage.class);

		onException(Exception.class)
//			.maximumRedeliveries(5).delay(1000)
			.log("Route level onException")
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
			.log("CT: ${body}")
			.process(duplicationTrnsValidation)
			
			// cek apakah SAF atau bukan
			//flattening and check saf
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage inboundMessage = exchange.getMessage().getBody(BusinessMessage.class);
					FlatPacs008Pojo flat = flatteningIsoMessageService.flatteningPacs008(inboundMessage);
					exchange.getMessage().setBody(flat);
					
					String saf = "NO";
					if ((null != flat.getCpyDplct()) && (flat.getCpyDplct().equals("DUPL")))
						saf =  "YES";
					
					if (saf.equals("YES")) {
						List<CreditTransfer> lCT = ctRepo.findAllByCrdtTrnRequestBizMsgIdr(flat.getBizMsgIdr());
						saf = "NEW";
						for (CreditTransfer ct : lCT) {
							if (ct.getReasonCode().equals("U149"))
								continue;
							
							if (ct.getResponseCode().equals("ACTC")) {
								saf = "ACTC";
								break;
							}
							else 
								saf = "RJCT";
						}
					}
					exchange.getMessage().setHeader("ct_saf", saf);
				}
			})
			
			.log("status saf: ${header.ct_saf}")
			
			.removeHeaders("*")
			.stop()
			
			.process(ctCorebankRequestProcessor)
			.setHeader("ct_cbrequest", simple("${body}"))
			.marshal(cbCreditTransferRequestJDF)
			.log("CB CT: ${body}")
		
			// send ke corebank
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.url.corebank}}?"
//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.unmarshal(cbCreditTransferResponseJDF)
			.doCatch(Exception.class)
				// TODO bagaimana kalau error waktu call corebanking
				.log("Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setBody(constant(null))
			.end()
			
			// process corebank response
			.process(creditTransferProcessor)
//			.marshal(businessMessageJDF)
			
			.removeHeaders("ct_*")

		;
	}

}
