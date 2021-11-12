package bifast.outbound.proxyinquiry;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.proxyinquiry.processor.ProxyRegistrationInquiryRequestProcessor;
import bifast.outbound.proxyinquiry.processor.ProxyRegistrationInquiryResponseProcessor;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionResponseProcessor;

@Component
public class ProxyInquiryRoute extends RouteBuilder {

	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	
	@Autowired
	private ProxyRegistrationInquiryRequestProcessor proxyRegistrationInquiryRequestProcessor;
	
	@Autowired
	private ProxyRegistrationInquiryResponseProcessor proxyRegistrationInquiryResponseProcessor;
	
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {
		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");      
		
		from("direct:proxyresolution").routeId("komi.prxyrslt")
		
			.log(LoggingLevel.DEBUG, "komi.prxyrslt", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			.process(proxyResolutionRequestProcessor)
	
			.to("direct:call-cihub")
			
			.process(proxyResolutionResponseProcessor)
			
//			.process(storeProxyResolutionProcessor)
			
		;

		from("direct:prxyrgstinquiry").routeId("komi.prxyrgstinquiry")
		
			.log(LoggingLevel.DEBUG, "komi.prxyrgstinquiry", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			.process(proxyRegistrationInquiryRequestProcessor)
	
			.to("direct:call-cihub")
			
			.log("${body}")
			.process(proxyRegistrationInquiryResponseProcessor)
			
	//		.process(storeProxyResolutionProcessor)
			
		;

	}
}

