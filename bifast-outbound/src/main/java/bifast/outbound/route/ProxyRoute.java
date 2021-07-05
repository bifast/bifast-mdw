package bifast.outbound.route;

import java.net.SocketTimeoutException;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.AccountEnquiryProcessor;
import bifast.outbound.accountenquiry.AccountEnquiryResponseProcessor;
import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.credittransfer.CreditTransferRequestProcessor;
import bifast.outbound.credittransfer.CreditTransferResponseProcessor;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.ficredittransfer.FICreditTransferRequestProcessor;
import bifast.outbound.ficredittransfer.FICreditTransferResponseProcessor;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.paymentstatus.PaymentStatusFaultProcessor;
import bifast.outbound.paymentstatus.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.PaymentStatusResponseProcessor;
import bifast.outbound.pojo.ChannelResponseMessage;
import bifast.outbound.processor.CombineMessageProcessor;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.SaveOutboundMesgProcessor;
import bifast.outbound.processor.SaveTracingTableProcessor;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;
import bifast.outbound.reversect.ReverseCreditTrnRequestProcessor;
import bifast.outbound.reversect.ReverseCreditTrnResponseProcessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@Component
public class ProxyRoute extends RouteBuilder {

	@Autowired
	private AccountEnquiryProcessor accountEnquiryProcessor;
	@Autowired
	private AccountEnquiryResponseProcessor accountEnqrResponseProcessor;
	@Autowired
	private CreditTransferRequestProcessor crdtTransferProcessor;
	@Autowired
	private CreditTransferResponseProcessor crdtTransferResponseProcessor;
	@Autowired
	private FICreditTransferRequestProcessor fiCrdtTransferRequestProcessor;
	@Autowired
	private FICreditTransferResponseProcessor fiCrdtTransferResponseProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private ReverseCreditTrnRequestProcessor reverseCTRequestProcessor;
	@Autowired
	private ReverseCreditTrnResponseProcessor reverseCTResponseProcessor;
	@Autowired
	private CombineMessageProcessor combineMessageProcessor;
	@Autowired
	private SaveOutboundMesgProcessor saveOutboundMesg;
	@Autowired
	private SaveTracingTableProcessor saveTracingTable;
	@Autowired
	private PaymentStatusFaultProcessor paymentStatusProcessor;

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(ChannelAccountEnquiryReq.class);
	JacksonDataFormat jsonChnlCreditTransferRequestFormat = new JacksonDataFormat(ChannelCreditTransferRequest.class);
	JacksonDataFormat jsonChnlFICreditTransferRequestFormat = new JacksonDataFormat(ChannelFICreditTransferReq.class);
	JacksonDataFormat jsonChnlPaymentStatusRequestFormat = new JacksonDataFormat(ChannelPaymentStatusRequest.class);
	JacksonDataFormat jsonChnlReverseCTRequestFormat = new JacksonDataFormat(ChannelReverseCreditTransferRequest.class);
	JacksonDataFormat jsonChnlResponseFormat = new JacksonDataFormat(ChannelResponseMessage.class);
	

	JacksonDataFormat jsonBusinessMessageFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJsonDataFormat() {
		jsonChnlAccountEnqrReqFormat.setInclude("NON_NULL");
		jsonChnlAccountEnqrReqFormat.setInclude("NON_EMPTY");
//		jsonChnlAccountEnqrReqFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlCreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlCreditTransferRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlCreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlFICreditTransferRequestFormat.setInclude("NON_NULL");
		jsonChnlFICreditTransferRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlFICreditTransferRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlPaymentStatusRequestFormat.setInclude("NON_NULL");
		jsonChnlPaymentStatusRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlPaymentStatusRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlReverseCTRequestFormat.setInclude("NON_NULL");
		jsonChnlReverseCTRequestFormat.setInclude("NON_EMPTY");
//		jsonChnlReverseCTRequestFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

		jsonChnlResponseFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonChnlResponseFormat.setInclude("NON_NULL");
		jsonChnlResponseFormat.setInclude("NON_EMPTY");
		jsonChnlResponseFormat.setPrettyPrint(true);
		jsonChnlResponseFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);

		jsonBusinessMessageFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageFormat.setInclude("NON_NULL");
		jsonBusinessMessageFormat.setInclude("NON_EMPTY");
		jsonBusinessMessageFormat.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		jsonBusinessMessageFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);

	}
	
	@Override
	public void configure() throws Exception {

		configureJsonDataFormat();
		
		restConfiguration().component("servlet")

        ;
		
		rest("/channel")
			.post("/prxyregister")
				.consumes("application/json")
				.to("direct:acctenqr")
				
			.post("/cstmrcrdtrn")
				.description("Pengiriman instruksi Credit Transfer ke bank lain melalui BI-FAST")
				.consumes("application/json")
				.to("direct:ctreq")

		;

		// Untuk Proses Account Enquiry Request

		from("direct:acctenqr").routeId("direct:acctenqr")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlAccountEnqrReqFormat)
			.setHeader("req_channelReq",simple("${body}"))
			.setHeader("req_msgType", constant("AccountEnquiry"))

			// convert channel request jadi pacs008 message
			.process(accountEnquiryProcessor)
			.setHeader("req_objbi", simple("${body}"))
			.marshal(jsonBusinessMessageFormat)
			
			// kirim ke CI-HUB
//			.enrich("rest:post:mock/cihub?host=localhost:9006&producerComponentName=http&bridgeEndpoint=true", enrichmentAggregator)
			.setHeader("HttpMethod", constant("POST"))
			.enrich("http:localhost:9006/mock/cihub?bridgeEndpoint=true&socketTimeout=6000", enrichmentAggregator)
			.convertBodyTo(String.class)
			.unmarshal(jsonBusinessMessageFormat)
			.setHeader("resp_objbi", simple("${body}"))	
			
			// prepare untuk response ke channel
			.process(accountEnqrResponseProcessor)
			.setHeader("resp_channel", simple("${body}"))
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:endlog")
			
			.setBody(simple("${header.resp_channel}"))
			.marshal(jsonChnlResponseFormat)
			.removeHeaders("req*")
			.removeHeaders("resp_*")
		;

		// Untuk Proses Credit Transfer Request



		from("seda:endlog")
			.log("Save log and trace")
			.process(saveOutboundMesg)
			.process(saveTracingTable)
			.process(combineMessageProcessor)
			.toD("file:{{bifast.outbound-log-folder}}?fileName=${header.req_fileName}")
			;

		
	}
}
