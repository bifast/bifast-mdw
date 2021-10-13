package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlProxyResolutionRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.processor.FaultResponseProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationResponseProcessor;
import bifast.outbound.proxyregistration.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyResolutionResponseProcessor;
import bifast.outbound.proxyregistration.processor.CreateProxyAccountMappingProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyResolutionProcessor;

@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private StoreProxyResolutionProcessor storeProxyResolutionProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private ProxyResolutionResponseProcessor proxyResolutionResponseProcessor;
	@Autowired
	private ProxyEnrichmentAggregator prxyAggrStrg;
	
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

	
		// Untuk Proses Proxy Registration Request

		from("direct:prxyrgst").routeId("komi.prxyrgst")
			.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			
			// jika bukan NEWR, siapkan data request unt Proxy Resolution
			.filter().simple("${body.registrationType} != 'NEWR'")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getIn().getHeader("hdr_request_list", RequestMessageWrapper.class);
						ChnlProxyRegistrationRequestPojo regnsReq = rmw.getChnlProxyRegistrationRequest();
						ChnlProxyResolutionRequestPojo rsltReq = new ChnlProxyResolutionRequestPojo();
						rsltReq.setIntrnRefId(regnsReq.getIntrnRefId());
						rsltReq.setSenderAccountNumber(regnsReq.getAccountNumber());
						rsltReq.setProxyType(regnsReq.getProxyType());
						rsltReq.setProxyValue(regnsReq.getProxyValue());
						rmw.setChnlProxyResolutionRequest(rsltReq);
						exchange.getMessage().setHeader("hdr_request_list", rmw);
					}					
				})
				.process(proxyResolutionRequestProcessor)
				.enrich("direct:call-cihub", prxyAggrStrg)
			.end()
			
			.process(proxyRegistrationRequestProcessor)
			.setHeader("prx_birequest", simple("${body}"))

			.to("direct:call-cihub")
			
			.setHeader("prx_biresponse", simple("${body}"))

			.process(proxyRegistrationResponseProcessor)
		
			.removeHeaders("prx*")
		;
        
		
		from("direct:proxyresolution").routeId("komi.prxyrslt")
		
			.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Proxy started.")
			.process(proxyResolutionRequestProcessor)
			.setHeader("prx_birequest", simple("${body}"))
	
			.to("direct:call-cihub")
			
			.setHeader("prx_biresponse", simple("${body}"))
	
			.process(proxyResolutionResponseProcessor)
			
//			.process(storeProxyResolutionProcessor)
			
			.removeHeaders("prx*")
		;

		

	}
}

