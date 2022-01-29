package bifast.inbound.accountenquiry;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

//	@Autowired private AccountEnquiryResponseProcessor aeResponseProcessor;
//	@Autowired private BuildAERequestForCbProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private IsoAERequestPrc isoAERequestPrc;
	@Autowired private IsoAEResponsePrc isoAEResponsePrc;
	@Autowired private SaveAccountEnquiryProcessor saveAEPrc;
	
	@Override
	public void configure() throws Exception {
			
		from("direct:accountenq").routeId("komi.accountenq")

			.setHeader("ae_obj_birequest", simple("${body}"))
										
			// prepare untuk request ke corebank
//			.process(buildAccountEnquiryRequestProcessor)
			.process(isoAERequestPrc)

	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Akan call AE corebank")

//			.to("seda:callcb")
			.to("direct:isoadpt")

	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] selesai call AE corebank")
			.process(isoAEResponsePrc)
			
			.to("seda:save_ae?exchangePattern=InOnly")
		
			.removeHeaders("ae_*")
		;
		
		from("seda:save_ae?concurrentConsumers=5").routeId("komi.saveae")
			.setExchangePattern(ExchangePattern.InOnly)
			.setHeader("hdr_frBI_jsonzip", exchangeProperty("bkp_hdr_frBI_jsonzip"))
			.setHeader("tmp_body", simple("${body}"))
			.marshal().zipDeflater().marshal().base64()
			.setHeader("hdr_toBI_jsonzip", simple("${body}"))
			.setBody(simple("${header.tmp_body}"))
			.process(saveAEPrc)
			.log(LoggingLevel.DEBUG, "komi.saveae", "Recorded ${header.hdr_process_data.inbMsgName}")
		;

	}

}
