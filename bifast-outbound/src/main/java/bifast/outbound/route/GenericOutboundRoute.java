package bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;

import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.pojo.ChannelRequest;
import bifast.outbound.processor.CheckChannelRequestTypeProcessor;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.proxyregistration.ChannelProxyResolutionReq;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;

@Component
public class GenericOutboundRoute extends RouteBuilder {

	@Autowired
	private CheckChannelRequestTypeProcessor checkChannelRequest;
	
	JacksonDataFormat ChnlRequestFormat = new JacksonDataFormat(ChannelRequest.class);
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(ChannelAccountEnquiryReq.class);
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChannelFICreditTransferReq.class);
	JacksonDataFormat jsonChnlPaymentStatusRequestFormat = new JacksonDataFormat(ChannelPaymentStatusRequest.class);
	JacksonDataFormat jsonChnlReverseCTRequestFormat = new JacksonDataFormat(ChannelReverseCreditTransferRequest.class);
	JacksonDataFormat jsonChnlProxyRegistrationFormat = new JacksonDataFormat(ChannelProxyRegistrationReq.class);
	JacksonDataFormat jsonChnlProxyResolutionFormat = new JacksonDataFormat(ChannelProxyResolutionReq.class);

	private void configureJsonDataFormat() {
		ChnlRequestFormat.setInclude("NON_NULL");
		ChnlRequestFormat.setInclude("NON_EMPTY");
//		ChnlRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		jsonChnlAccountEnqrReqFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrReqFormat.setInclude("NON_EMPTY");
		jsonChnlAccountEnqrReqFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");
		jsonChnlCreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");
		jsonChnlFICreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlPaymentStatusRequestFormat.setInclude("NON_NULL");
		jsonChnlPaymentStatusRequestFormat.setInclude("NON_EMPTY");
		jsonChnlPaymentStatusRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlReverseCTRequestFormat.setInclude("NON_NULL");
		jsonChnlReverseCTRequestFormat.setInclude("NON_EMPTY");
		jsonChnlReverseCTRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlProxyRegistrationFormat.setInclude("NON_NULL");
		jsonChnlProxyRegistrationFormat.setInclude("NON_EMPTY");
		jsonChnlProxyRegistrationFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlProxyResolutionFormat.setInclude("NON_NULL");
		jsonChnlProxyResolutionFormat.setInclude("NON_EMPTY");
		jsonChnlProxyResolutionFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}


	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
		restConfiguration().component("servlet");
		
		rest("/komi")
			.post("/outbound")
			.consumes("application/json")
			.to("direct:outbound")
		;
	
		from("direct:outbound")
			.convertBodyTo(String.class)
			.unmarshal(ChnlRequestFormat)
			.process(checkChannelRequest)		// produce header rcv_msgType, rcv_channel
			
			.choice()
				.when().simple("${header.rcv_msgType} == 'AccountEnquiry'")
					.log("Account Enquiry")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlAccountEnqrReqFormat)
					.to("direct:acctenqr")
					
				.when().simple("${header.rcv_msgType} == 'CreditTransfer'")
					.log("Credit Transfer")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlCreditTransferRequestFormat)
					.to("direct:ctreq")

				.when().simple("${header.rcv_msgType} == 'FICreditTransfer'")
					.log("FI Credit Transfer")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlFICreditTransferRequestFormat)
					.to("direct:fictreq")

				.when().simple("${header.rcv_msgType} == 'PaymentStatus'")
					.log("Payment Status")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlPaymentStatusRequestFormat)
					.to("direct:pymtstatus")

				.when().simple("${header.rcv_msgType} == 'ReverseCreditTransfer'")
					.log("Reverse Credit Transfer")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlReverseCTRequestFormat)
					.to("direct:reversect")

				.when().simple("${header.rcv_msgType} == 'ProxyRegistration'")
					.log("Proxy Registration")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlProxyRegistrationFormat)
					.to("direct:proxyregistration")

				.when().simple("${header.rcv_msgType} == 'ProxyResolution'")
					.log("Proxy Resolution")
					.setBody(simple("${header.rcv_channel}"))
					.marshal(jsonChnlProxyResolutionFormat)
					.to("direct:proxyresolution")

			.end()
			
			.removeHeaders("rcv_*")
		;
	
	}

}
