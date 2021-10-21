package bifast.outbound.credittransfer;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.paymentstatus.processor.PaymentStatusRequestProcessor;
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.report.pojo.RequestPojo;
import bifast.outbound.report.pojo.ResponsePojo;

@Component
public class SttlAndPymtSttsProcessor extends RouteBuilder {
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;

	JacksonDataFormat settlementRequestJDF = new JacksonDataFormat(RequestPojo.class);
	JacksonDataFormat settlementResponseJDF = new JacksonDataFormat(ResponsePojo.class);

	@Override
	public void configure() throws Exception {
		
		settlementRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementRequestJDF.setInclude("NON_NULL");
		settlementRequestJDF.setInclude("NON_EMPTY");
		settlementRequestJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		settlementResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		settlementResponseJDF.setInclude("NON_NULL");
		settlementResponseJDF.setInclude("NON_EMPTY");

		
		from("direct:sttlpymtsts").routeId("komi.sttlpymtsts")
		
			.setBody(simple("${header.hdr_request_list.creditTransferRequest}"))
				
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					BusinessMessage reqData = exchange.getMessage().getBody(BusinessMessage.class);
					RequestPojo sttlReq = new RequestPojo();	
					sttlReq.setMsgType("Settlement");
					sttlReq.setEndToEndId(reqData.getAppHdr().getBizMsgIdr());
					exchange.getIn().setBody(sttlReq);
			}})
    			
			.doTry()
    			.marshal(settlementRequestJDF)
    			.log(LoggingLevel.DEBUG, "komi.sttlpymtsts", "[ChnlReq:${header.hdr_chnlRefId}] Settlement request: ${body}")

    			.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.inbound-url}}?"
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
    			.log(LoggingLevel.DEBUG, "komi.sttlpymtsts", "[ChnlReq:${header.hdr_chnlRefId}] Settlement response: ${body}")
				
				.unmarshal(settlementResponseJDF)
				
				.choice()
					.when().simple("${body.businessMessage} != null")
						.setBody(simple("${body.businessMessage}"))
					.endChoice()
					.otherwise()
						.setBody(constant(null))
					.endChoice()
				.end()
			
			.endDoTry()
    		.doCatch(Exception.class)
    			.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}] Settlement Enquiry error")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
    			.setBody(constant(null))
    		.end()

    		// PAYMENT STATUS
			.filter().simple("${body} == null")
			
				.setBody(simple("${header.hdr_request_list.creditTransferRequest}"))
				
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						BusinessMessage ctReq = exchange.getMessage().getBody(BusinessMessage.class);
//						ChnlPaymentStatusRequestPojo request = new ChnlPaymentStatusRequestPojo();
//						request.setsetOrgnlEndToEndId(ctReq.getAppHdr().getBizMsgIdr());
//						request.setRecptBank(ctReq.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
//						exchange.getIn().setBody(request);
					}
					
				})

				.process(paymentStatusRequestProcessor)
		
				.log(LoggingLevel.DEBUG, "komi.sttlpymtsts", "[ChnlReq:${header.hdr_chnlRefId}] tidak ada settlement, harus PS.")		
				.to("direct:call-cihub")
					
			.end()	
		;
		
	}
}
