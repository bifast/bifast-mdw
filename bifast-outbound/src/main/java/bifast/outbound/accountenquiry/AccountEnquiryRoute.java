package bifast.outbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.accountenquiry.processor.AccountEnquiryRequestProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionRequestProcessor;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired private AccountEnquiryRequestProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;

	@Override
	public void configure() throws Exception {

		from("direct:acctenqr").routeId("komi.acctenq")
			
			.choice()
				.when().simple("${body.creditorAccountNumber} == '' ") // jika pake proxyId panggil proxyResolution
					.log(LoggingLevel.DEBUG, "komi.acctenq", "[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] akan panggil ProxyResolution.")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
							ChnlAccountEnquiryRequestPojo aeReq = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();
							ChnlProxyResolutionRequestPojo prxRes = new ChnlProxyResolutionRequestPojo();
							
							prxRes.setChannelRefId(aeReq.getChannelRefId());
							prxRes.setProxyType(aeReq.getProxyType());
							prxRes.setProxyValue(aeReq.getProxyId());
							prxRes.setSenderAccountNumber(aeReq.getSenderAccountNumber());
							rmw.setChnlProxyResolutionRequest(prxRes);
//							exchange.getMessage().setBody(prxRes);
						}
					})
					.process(proxyResolutionRequestProcessor)
				.endChoice()

				.otherwise()
					.log(LoggingLevel.DEBUG, "komi.acctenq", 
						"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Akan bikin AE Request msg")
					.process(buildAccountEnquiryRequestProcessor)					
				.endChoice()
			.end()
			.to("direct:call-ciconn")

			.process(accountEnqrResponseProcessor)

		;

	}
}
