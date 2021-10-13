package bifast.inbound.accountenquiry;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountEnquiryRoute extends RouteBuilder {

	@Autowired
	private AECorebankReponseProcessor aeCorebankReponseProcessor;
	@Autowired
	private BuildAccountEnquiryRequestMessageProcessor buildAccountEnquiryRequestProcessor;

	@Override
	public void configure() throws Exception {
//		onException(org.apache.http.conn.HttpHostConnectException.class)
//			.maximumRedeliveries(5).delay(1000)
//			.log("Route level onException")
////			.handled(true)
//			.setBody(constant(null))
//		;
		
		from("direct:accountenq").routeId("komi.accountenq")
				
			.log(LoggingLevel.DEBUG, "komi.accountenq", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] ${body}")
			// prepare untuk request ke corebank
			.process(buildAccountEnquiryRequestProcessor)
			.setHeader("ae_cbrequest", simple("${body}"))
				
			//TODO call corebank Account Enquiry
			.process(aeCorebankReponseProcessor)
			
			.removeHeaders("ae_*")

		;
	}

}
