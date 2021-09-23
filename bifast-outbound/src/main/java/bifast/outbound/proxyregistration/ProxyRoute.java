package bifast.outbound.proxyregistration;

import java.net.SocketTimeoutException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.processor.FaultResponseProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationResponseProcessor;
import bifast.outbound.proxyregistration.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyResolutionResponseProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyRegistrationProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyResolutionProcessor;

@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private StoreProxyRegistrationProcessor storeProxyRegistrationProcessor;
	@Autowired
	private StoreProxyResolutionProcessor storeProxyResolutionProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private FaultResponseProcessor faultProcessor;

	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

		
        onException(Exception.class).routeId("Proxy Exception Handler")
	    	.log("Fault di Prxy Excp, ${header.hdr_errorlocation}")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
	    	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.process(faultProcessor)
			.marshal(chnlResponseJDF)
			.removeHeaders("hdr_*")
			.removeHeaders("req_*")
			.removeHeader("HttpMethod")
	    	.handled(true)
    	;

		// Untuk Proses Proxy Registration Request

		from("direct:prxyrgst").routeId("komi.prxyrgst")
			.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			.process(proxyRegistrationRequestProcessor)
			.setHeader("prx_birequest", simple("${body}"))

			.to("direct:call-cihub")
			
			.setHeader("prx_biresponse", simple("${body}"))

			.process(proxyRegistrationResponseProcessor)
			.process(storeProxyRegistrationProcessor)
			
			.removeHeaders("prx*")
		;
        
		
		from("direct:proxyresolution").routeId("komi.prxyrslt")
		
			.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			.process(proxyResolutionRequestProcessor)
			.setHeader("prx_birequest", simple("${body}"))
	
			.to("direct:call-cihub")
			
			.setHeader("prx_biresponse", simple("${body}"))
	
			.process(proxyResolutionResponseProcessor)
			.process(storeProxyResolutionProcessor)
			
			.removeHeaders("prx*")
		;

		

	}
}

