package bifast.inbound.accountenquiry;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

	@Autowired private AccountEnquiryResponseProcessor aeResponseProcessor;
	@Autowired private BuildAERequestForCbProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private IsoAERequestPrc isoAERequestPrc;
	@Autowired private IsoAEResponsePrc isoAEResponsePrc;

	@Override
	public void configure() throws Exception {
			
		from("direct:accountenq").routeId("komi.accountenq")

			.setHeader("ae_obj_birequest", simple("${body}"))
										
			// prepare untuk request ke corebank
//			.process(buildAccountEnquiryRequestProcessor)
			.process(isoAERequestPrc)

	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] Akan call AE corebank")

//			.to("seda:callcb")
			.to("seda:isoadpt")

	 		.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_process_data.inbMsgName}:${header.hdr_process_data.endToEndId}] selesai call AE corebank")
			.process(isoAEResponsePrc)
//			.process(aeResponseProcessor)

					
			.removeHeaders("ae_*")
		;
	}

}
