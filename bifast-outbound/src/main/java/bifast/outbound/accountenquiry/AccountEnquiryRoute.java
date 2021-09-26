package bifast.outbound.accountenquiry;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.processor.AccountEnquiryRequestProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.processor.FlatResponseProcessor;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired
	private AccountEnquiryRequestProcessor buildAccountEnquiryRequestProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private FlatResponseProcessor flatResponseProcessor;
	
	@Override
	public void configure() throws Exception {


		from("direct:acctenqr").routeId("komi.acctenq")
		
			.setHeader("ae_channel_request", simple("${body}"))
			.process(buildAccountEnquiryRequestProcessor)
			.setHeader("ae_objreq_bi", simple("${body}"))
	
			.to("direct:call-cihub")
			
			.setHeader("ae_objresp_bi", simple("${body}"))
	
			.process(accountEnqrResponseProcessor)
			
			.removeHeaders("ae*")
		;


		from("direct:acctenqrflt").routeId("komi.acctenqflt")
		
			.setHeader("ae_channel_request", simple("${body}"))
			.process(buildAccountEnquiryRequestProcessor)
			.setHeader("ae_objreq_bi", simple("${body}"))
	
			.to("direct:call-cihub")
			
			.setHeader("ae_objresp_bi", simple("${body}"))
	
			.process(flatResponseProcessor)
			
			.removeHeaders("ae*")
	;

	}

}
