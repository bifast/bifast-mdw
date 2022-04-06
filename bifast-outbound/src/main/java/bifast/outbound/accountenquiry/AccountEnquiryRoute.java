package bifast.outbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.accountenquiry.processor.AEFaultResponseProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryRequestProcessor;
import bifast.outbound.accountenquiry.processor.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.processor.ValidateAEProcessor;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyinquiry.processor.ProxyResolutionRequestProcessor;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class AccountEnquiryRoute extends RouteBuilder{

	@Autowired private AccountEnquiryRequestProcessor buildAccountEnquiryRequestProcessor;
	@Autowired private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired private AEFaultResponseProcessor faultResponseProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private ProxyResolutionRequestProcessor proxyResolutionRequestProcessor;
//	@Autowired private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired private ValidateAEProcessor validateAEProc;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlResponseJDF = jdfService.basicPrettyPrint(ChannelResponseWrapper.class);

		onException(Exception.class).routeId("komi.acctenq.onException")
			.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.process(faultResponseProcessor)
			.to("seda:savetablechannel?exchangePattern=InOnly")
			.marshal(chnlResponseJDF)
			.removeHeaders("*")
	    	.handled(true)
		;


		from("direct:acctenqr").routeId("komi.acctenq")
			
			.process(validateAEProc)
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
					ChnlAccountEnquiryRequestPojo chnReq = rmw.getChnlAccountEnquiryRequest();
					exchange.getMessage().setBody(chnReq);
				}	
			})
			
			// akan panggil Proxy Resolution
			.filter().simple("${body.creditorAccountNumber} == null || ${body.creditorAccountNumber} == '' ")
				.log(LoggingLevel.DEBUG, "komi.acctenq", "[${exchangeProperty.prop_request_list.msgName}:"
						+ "${exchangeProperty.prop_request_list.requestId}] akan panggil ProxyResolution.")
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
						ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();
						ChnlProxyResolutionRequestPojo prxRes = new ChnlProxyResolutionRequestPojo();
						
						prxRes.setChannelRefId(aeReq.getChannelRefId());
						prxRes.setProxyType(aeReq.getProxyType());
						prxRes.setProxyValue(aeReq.getProxyId());
						prxRes.setSenderAccountNumber(aeReq.getSenderAccountNumber());
						rmw.setChnlProxyResolutionRequest(prxRes);
						exchange.setProperty("prop_request_list", rmw);
						exchange.getMessage().setBody(prxRes);
					}
				})
				.process(proxyResolutionRequestProcessor)
				.to("direct:call-cihub")
//				.process(aeProxyEnrichment)
				
			.end()
			// selesai panggil Proxy Resolution
			.log(LoggingLevel.DEBUG, "komi.acctenq", 
					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] ${body.class}")

			.filter().simple("${body.class} endsWith 'ChnlAccountEnquiryRequestPojo'")
				.log(LoggingLevel.DEBUG, "komi.acctenq", 
						"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Akan bikin AE Request msg")
				.process(buildAccountEnquiryRequestProcessor)					
				.to("direct:call-cihub")
			.end()
			
//			.process(saveAccountEnquiryProcessor)

			.process(accountEnqrResponseProcessor)

		;

	}

}
