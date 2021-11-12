package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationResponseProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyRegistrationProcessor;

@Component
public class ProxyRegistrationRoute extends RouteBuilder {

	@Autowired
	private ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyRegistrationResponseProcessor proxyRegistrationResponseProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private StoreProxyRegistrationProcessor saveProxyRegnProc;

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
				.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Proxy Resolution dulu.")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getIn().getHeader("hdr_request_list", RequestMessageWrapper.class);
						ChnlProxyRegistrationRequestPojo regnsReq = rmw.getChnlProxyRegistrationRequest();
						ChnlProxyResolutionRequestPojo rsltReq = new ChnlProxyResolutionRequestPojo();
						rsltReq.setChannelRefId(null);
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
			
			.log("${body}")
			
			.filter().simple("${body.class} endsWith 'ChnlProxyRegistrationRequestPojo'")
				.process(proxyRegistrationRequestProcessor)
				.to("direct:call-cihub")
				.log(LoggingLevel.INFO, "komi.prxy.prxyrgst", "[ChRefId:${header.hdr_chnlRefId}] Dari call-cihub berupa ${body}")
				.process(saveProxyRegnProc)
			.end()

			.process(proxyRegistrationResponseProcessor)
		;
        
	}
}

