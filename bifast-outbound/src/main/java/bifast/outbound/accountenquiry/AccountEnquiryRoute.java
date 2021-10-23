package bifast.outbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.processor.AEProxyEnrichmentProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryRequestProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.processor.SaveAccountEnquiryProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyregistration.processor.ProxyRegistrationRequestProcessor;
import bifast.outbound.proxyregistration.processor.ProxyResolutionRequestProcessor;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired
	private AccountEnquiryRequestProcessor buildAccountEnquiryRequestProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private AEProxyEnrichmentProcessor aeProxyEnrichment;
	ProxyRegistrationRequestProcessor proxyRegistrationRequestProcessor;
	@Autowired
	private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;

	@Override
	public void configure() throws Exception {


		from("direct:acctenqr").routeId("komi.acctenq")
		
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					ChnlAccountEnquiryRequestPojo chnReq = rmw.getChnlAccountEnquiryRequest();
					exchange.getMessage().setBody(chnReq);
				}	
			})
			
			.filter().simple("${body.creditorAccountNumber} == null")
				.log("accountNo is null")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
						ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
						ChnlProxyResolutionRequestPojo prxRes = new ChnlProxyResolutionRequestPojo();
						
						prxRes.setChannelRefId(aeReq.getChannelRefId());
						prxRes.setProxyType(aeReq.getProxyType());
						prxRes.setProxyValue(aeReq.getProxyId());
						prxRes.setSenderAccountNumber(aeReq.getSenderAccountNumber());
						rmw.setChnlProxyResolutionRequest(prxRes);
						exchange.getMessage().setHeader("hdr_request_list", rmw);
						exchange.getMessage().setBody(prxRes);
					}
					
				})

				.log("${body}")
				.process(proxyResolutionRequestProcessor)
				.to("direct:call-cihub")
				.log("${body.class}")
				.process(aeProxyEnrichment)
				
			.end()
			
			.process(buildAccountEnquiryRequestProcessor)
	
			.to("direct:call-cihub")
			.log("${body.class}")
			.process(saveAccountEnquiryProcessor)

			.process(accountEnqrResponseProcessor)

		;

	}

}
