package bifast.outbound.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.processor.SaveAccountEnquiryProcessor;
import bifast.outbound.credittransfer.processor.StoreCreditTransferProcessor;
import bifast.outbound.paymentstatus.StorePaymentStatusProcessor;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.processor.ExceptionToFaultMapProcessor;
import bifast.outbound.proxyregistration.processor.StoreProxyRegistrationProcessor;
import bifast.outbound.service.UtilService;

@Component
public class CihubRoute extends RouteBuilder {

	JacksonDataFormat businessMessageJDF = new JacksonDataFormat(BusinessMessage.class);

	@Autowired
	private EnrichmentAggregator enrichmentAggregator;
	@Autowired
	private SaveAccountEnquiryProcessor saveAccountEnquiryProcessor;
	@Autowired
	private StoreCreditTransferProcessor storeCreditTransferProcessor;
	@Autowired
	private StorePaymentStatusProcessor storePaymentStatusProcessor;
	@Autowired
	private StoreProxyRegistrationProcessor storeProxyRegistrationProcessor;
	@Autowired
	private UtilService utilService;
	@Autowired
	private ExceptionToFaultMapProcessor exceptionToFaultMap;

	@Override
	public void configure() throws Exception {
		
		businessMessageJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		businessMessageJDF.setInclude("NON_NULL");
		businessMessageJDF.setInclude("NON_EMPTY");
		businessMessageJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		businessMessageJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		

		// ** ROUTE GENERAL UNTUK POSTING KE CI-HUB ** //
		from("direct:call-cihub").routeId("komi.call-cihub").messageHistory()
		
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage bm = exchange.getIn().getBody(BusinessMessage.class);
					String msgType = utilService.getMsgType(bm.getAppHdr().getMsgDefIdr(), bm.getAppHdr().getBizMsgIdr());
					exchange.getMessage().setHeader("hdr_trxname", msgType);
				}
			}).id("start_route")
						
			.log("${header.hdr_trxname}")
			.setHeader("hdr_cihub_request", simple("${body}"))

			.marshal(businessMessageJDF)

			.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChnlReq:${header.hdr_chnlRefId}][${header.hdr_trxname}] request: ${body}")
	
			.setHeader("tmp_body", simple("${body}"))
			.marshal().zipDeflater()
			.marshal().base64()

			.setHeader("cihubroute_encr_request", simple("${body}"))
			.setBody(simple("${header.tmp_body}"))
			
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.ciconnector-url}}?"
						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)				
				.log(LoggingLevel.DEBUG, "komi.call-cihub", "[ChnlReq:${header.hdr_chnlRefId}][][${header.hdr_trxname}] response: ${body}")
	
				.setHeader("tmp_body", simple("${body}"))
				.marshal().zipDeflater()
				.marshal().base64()
				.setHeader("cihubroute_encr_response", simple("${body}"))
				.setBody(simple("${header.tmp_body}"))
	
				.unmarshal(businessMessageJDF)
				
				.filter().simple("${body.appHdr.msgDefIdr} == 'admi.002.001.01'")
					.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}] Call CI-HUB Rejected.")
			    	.log(LoggingLevel.ERROR, "${body.document.messageReject.rsn.errLctn}")
			    	.log(LoggingLevel.ERROR, "${body.document.messageReject.rsn.rsnDesc}")
			    	.log(LoggingLevel.ERROR, "${body.document.messageReject.rsn.addtlData}")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
							MessageRejectV01 reject = bm.getDocument().getMessageReject();
							ChnlFailureResponsePojo fault = new ChnlFailureResponsePojo();
							fault.setErrorCode("ERROR-CIHUB");
							fault.setLocation(reject.getRsn().getErrLctn());
							fault.setReason(reject.getRsn().getRsnDesc());
							exchange.getMessage().setBody(fault);
						}
					})
					.setHeader("hdr_error_status", constant("ERROR-CIHUB"))

				.end()
				
				.setHeader("hdr_error_status", constant(null))
			
			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[ChnlReq:${header.hdr_chnlRefId}] Call CI-HUB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(exceptionToFaultMap)
//				.setHeader("hdr_error_status", constant("ERROR-CIHUB"))
//				.setHeader("hdr_error_mesg", simple("${exception.message}"))
//		    	.setBody(constant(null))
	
//			.endDoTry()
			.end()
	
			.setHeader("hdr_cihubResponseTime", simple("${date:now:yyyyMMdd hh:mm:ss}"))
			.setHeader("hdr_cihub_response", simple("${body}"))
	
				
			.choice().id("end_route")
				.when().simple("${header.hdr_trxname} == 'AEReq'")
					.log("akan save ke ae")
					.process(saveAccountEnquiryProcessor)
				.when().simple("${header.hdr_trxname} == 'CTReq'")
					.process(storeCreditTransferProcessor)
				.when().simple("${header.hdr_trxname} == 'PSReq'")
					.process(storePaymentStatusProcessor)
				.when().simple("${header.hdr_trxname} == 'PxRegReq'")
					.process(storeProxyRegistrationProcessor)
			.end()

			.removeHeaders("tmp_*")
			.removeHeaders("cihubroute_*")
		;

	
	}		
}
