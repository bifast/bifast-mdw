package bifast.outbound.proxyregistration;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Proxy;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationResponseProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyRegistrationProcessor;
import bifast.outbound.repository.ProxyRepository;

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
	@Autowired ProxyEnrichmentAggregator prxyAggrStrg;
	@Autowired ProxyRepository proxyRepo;	
	
	JacksonDataFormat chnlResponseJDF = new JacksonDataFormat(ChannelResponseWrapper.class);

	@Override
	public void configure() throws Exception {

		chnlResponseJDF.setInclude("NON_NULL");
		chnlResponseJDF.setInclude("NON_EMPTY");

	
		// Untuk Proses Proxy Registration Request

		from("direct:prxyrgst").routeId("komi.prxyrgst")
			.log(LoggingLevel.DEBUG, "komi.prxy.prxyrgst", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Proxy started.")
			
			// jika bukan NEWR, siapkan data request unt ambil RegistrationID
			.log("${body.registrationType}")
			.filter().simple("${body.registrationType} != 'NEWR' && ${body.registrationId} == null")
				.log(LoggingLevel.DEBUG, "komi.prxy.prxyrgst", 
						"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Cari registrationID.")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getIn().getHeader("hdr_request_list", RequestMessageWrapper.class);
						ChnlProxyRegistrationRequestPojo regnsReq = rmw.getChnlProxyRegistrationRequest();
						Optional<Proxy> oPrxy = proxyRepo.getByProxyTypeAndProxyValue(regnsReq.getProxyType(), regnsReq.getProxyValue());
						if (oPrxy.isPresent()) {
							regnsReq.setRegistrationId(oPrxy.get().getRegistrationId());
							rmw.setChnlProxyRegistrationRequest(regnsReq);
							exchange.getMessage().setHeader("hdr_request_list", rmw);
							exchange.getMessage().setBody(regnsReq);
							System.out.println("RegId " + regnsReq.getRegistrationId());
						}
					}})
			.end()	

			.filter().simple("${body.registrationType} != 'NEWR' && ${body.registrationId} == null ")
				.log(LoggingLevel.DEBUG, "komi.prxyrgst", 
						"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Akan call Proxy Resolution")
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

			.filter().simple("${body.class} endsWith 'ChnlProxyRegistrationRequestPojo'")
				
				.process(proxyRegistrationRequestProcessor)
				
				.to("direct:call-cihub")
				.log(LoggingLevel.DEBUG, "komi.prxy.prxyrgst", 
						"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Dari call-cihub berupa ${body}")
				.process(saveProxyRegnProc)
			.end()

			.process(proxyRegistrationResponseProcessor)
		;
        
	}
}

