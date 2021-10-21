package bifast.outbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.processor.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.processor.PaymentStatusResponseProcessor;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.chnlrequest.PaymentStatusRequestSAFPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.report.pojo.RequestPojo;
import bifast.outbound.report.pojo.ResponsePojo;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class PaymentStatusSAFRoute extends RouteBuilder {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
   
    @Autowired
    private PaymentStatusChannelRequestProcessor paymentStatusChannelRequestProcessor;
	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;
	@Autowired
	private ChannelTransactionRepository channelRequestRepo;
	@Autowired
	private EnrichmentAggregator enrichmentAggregator;

	@Autowired
	private CreditTransferRepository creditTransferRepo;
	
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

		
	
		from("direct:pssaf").routeId("komi.ps.saf")
			.setHeader("ps_channelrequest", simple("${body}"))

			//cari dulu dari endtoendid berdasarkan chnlRefId
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					PaymentStatusRequestSAFPojo psReq = rmw.getPaymentStatusRequestSAF();
					List<ChannelTransaction> lChannelTrns = channelRequestRepo.findByChannelIdAndChannelRefId(rmw.getChannelId(), psReq.getOrgnlRefId());
					if (lChannelTrns.size() > 0) {
						Optional<CreditTransfer> oCreditTrns = creditTransferRepo.findByKomiTrnsId(lChannelTrns.get(0).getKomiTrnsId());
						if (oCreditTrns.isPresent()) {
							psReq.setOrgnlEndToEndId(oCreditTrns.get().getCrdtTrnRequestBizMsgIdr());
							psReq.setCreditorAccountNumber(oCreditTrns.get().getCreditorAccountNumber());
						}
					}
					rmw.setPaymentStatusRequestSAF(psReq);
					exchange.getMessage().setHeader("hdr_request_list", rmw);
				}
			})
			
			.choice()
				.when().simple("${header.hdr_request_list.chnlPaymentStatusRequest.orgnlEndToEndId} == null")
					.log("ga ketemu channel requestnya")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
							ChnlPaymentStatusRequestPojo psReq = rmw.getChnlPaymentStatusRequest();
							ChannelResponseWrapper channelResponseWr = new ChannelResponseWrapper();
							channelResponseWr.setResponseCode("U106");
							channelResponseWr.setResponseMessage("Original Payment Not Found");
							channelResponseWr.setDate(LocalDateTime.now().format(dateformatter));
							channelResponseWr.setTime(LocalDateTime.now().format(timeformatter));
							channelResponseWr.setContent(new ArrayList<>());
							ChnlCreditTransferResponsePojo chnResp = new ChnlCreditTransferResponsePojo();
							
							chnResp.setOrignReffId(psReq.getChannelRefId());
						
							channelResponseWr.getContent().add(chnResp);
							exchange.getMessage().setBody(channelResponseWr);
						}
					})

				.otherwise()
					.log("oke bisa lanjut cari settlement")
					.to("direct:findsettlement")
			.end()
			
			
			.log("${body.class}")
			.filter().simple("${body} == null")
				.log("akan call payment status request ke cihub")
				.setHeader("ps_objreq_bi", simple("${body}"))
				.process(paymentStatusRequestProcessor)
				.to("direct:call-cihub")
			.end()
				
			.process(paymentStatusResponseProcessor)

			.removeHeaders("ps*")
			
		;

//		from("direct:ps").routeId("komi.ps")
//			.setHeader("ps_channelrequest", simple("${body}"))
//			
//			.process(paymentStatusRequestProcessor)
//			.setHeader("ps_objreq_bi", simple("${body}"))
//	
//			.to("direct:call-cihub")
//				
//
//			.removeHeaders("ps*")
//		;
		
		from("direct:findsettlement")
			.log("akan cari settlement")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					PaymentStatusRequestSAFPojo psReq = rmw.getPaymentStatusRequestSAF();
					RequestPojo sttlReq = new RequestPojo();
					sttlReq.setMsgType("Settlement");
					sttlReq.setEndToEndId(psReq.getOrgnlEndToEndId());
					exchange.getMessage().setBody(sttlReq);
				}
			})

			.doTry()
				.marshal(settlementRequestJDF)
				.log("settlement mulai: ${body}")
				
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
						.log("disini")
						.setBody(simple("${body.businessMessage}"))
						.log("${body}")
						.log("selesai")
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
		;
	}

}
