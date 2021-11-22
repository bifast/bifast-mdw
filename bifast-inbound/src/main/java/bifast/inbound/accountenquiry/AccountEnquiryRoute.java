package bifast.inbound.accountenquiry;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

	@Autowired private BuildAERequestForCbProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private AccountEnquiryResponseProcessor aeResponseProcessor;

	@Override
	public void configure() throws Exception {
			
		from("direct:accountenq").routeId("komi.accountenq")

			.setHeader("ae_obj_birequest", simple("${body}"))
										
			// prepare untuk request ke corebank
			.process(buildAccountEnquiryRequestProcessor)

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Akan call AE corebank")

			//TODO call corebank Account Enquiry
			.to("seda:callcb")

	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] selesai call AE corebank")
			.process(aeResponseProcessor)
					
			.removeHeaders("ae_*")
		;
	}

}
