package bifast.outbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class DebitReversalRoute extends RouteBuilder{
	@Autowired private CbFaultExceptionProcessor cbFaultProcessor;
	@Autowired private CorebankTransactionRepository cbRepo;
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	
	private static Logger logger = LoggerFactory.getLogger(DebitReversalRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat debitReversalRequestJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.wrapRoot(DebitReversalResponsePojo.class);

		from("seda:debitreversal").routeId("komi.reverse_ct")
			
			.log(LoggingLevel.DEBUG, "komi.reverse_ct", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Akan debit-reversal")

			.setHeader("ct_progress", constant("REVERSAL"))
			.setHeader("revct_tmpbody", simple("${body}"))
				
			// build reversal message
			.process(debitReversalRequestProcessor)
			.setHeader("revct_revRequest", simple("${body}"))
			.marshal(debitReversalRequestJDF)
			.setHeader("revct_strRequest", simple("${body}"))

			//submit reversal
//			.to("seda:callcb")

			.to("direct:postcreditreversal?exchangePattern=InOptionalOut")
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:savecbdebitrevr")

			// jika gagal reversal return as Fault, otherwise DebitResponse
			.choice()
				.when().simple("${body.class} endsWith 'DebitReversalResponsePojo'")
					.setBody(simple("${header.revct_tmpbody}"))
				.endChoice()
				
				.when().simple("${body.class} endsWith 'FaultPojo'")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							FaultPojo fault = exchange.getMessage().getBody(FaultPojo.class);		
							fault.setCallStatus("TIMEOUT");
							fault.setResponseCode("KSTS");
							fault.setReasonCode("K000");
							exchange.getMessage().setBody(fault);
						}
					})
				.endChoice()
			.end()
			
			.removeHeaders("revct_*")
		;
		
		
		from("direct:postcreditreversal").routeId("komi.postcrdtrev")
			.log(LoggingLevel.DEBUG, "komi.postcrdtrev", 
					"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Akan kirim reversal")

			.setProperty("bkp_hdr_request_list").header("hdr_request_list")
			.setProperty("bkp_hdr_response_list").header("hdr_response_list")

			.removeHeaders("hdr_*")
			.setHeader("hdr_request_list", exchangeProperty("bkp_hdr_request_list"))

			.doTry()
				.log("[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Request ISOAdapter: ${body}")
		
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
			            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
					}
				})
	
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("http://{{komi.url.isoadapter}}/debitreversal?"
	//					+ "socketTimeout=7000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
	
				.convertBodyTo(String.class)
				
				.log("[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Response ISOAdapter: ${body}")
	
				.unmarshal(debitReversalResponseJDF)
				
				.filter().simple("${body.status} != 'ACTC' ")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							DebitReversalResponsePojo resp = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
							FaultPojo fault = new FaultPojo();
							fault.setResponseCode(resp.getStatus());
							fault.setReasonCode(resp.getReason());
							exchange.getMessage().setBody(fault);
						}
					})
					.log(LoggingLevel.DEBUG, "komi.reverse_ct", 
							"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Fault")
				.end()

				.setHeader("hdr_request_list", exchangeProperty("bkp_hdr_request_list"))
				.setHeader("hdr_response_list", exchangeProperty("bkp_hdr_response_list"))

			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] Call Corebank Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
				.setHeader("hdr_request_list", exchangeProperty("bkp_hdr_request_list"))
				.setHeader("hdr_response_list", exchangeProperty("bkp_hdr_response_list"))
		    	.process(cbFaultProcessor)
			.end()
			
			
		;
		
		from("seda:savecbdebitrevr?concurrentConsumers=3").routeId("komi.savecbdebitrevr")
			.log(LoggingLevel.DEBUG, "komi.savecbdebitrevr", 
				"[${header.hdr_request_list.msgName}:${header.hdr_request_list.requestId}] akan save Debit Reversal ke CB-log")
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
					String komiTrnsId = rmw.getKomiTrxId();
					DebitReversalRequestPojo cbRequest = exchange.getMessage().getHeader("revct_revRequest",DebitReversalRequestPojo.class);
	
	//				DebitReversalResponsePojo cbResponse = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
					Object oCbResponse = exchange.getMessage().getBody(Object.class);
	
					String strRequest = exchange.getMessage().getHeader("revct_strRequest", String.class);
	
					CorebankTransaction cbTrns = new CorebankTransaction();
					cbTrns.setCreditAmount(new BigDecimal(cbRequest.getAmount()));
					cbTrns.setCstmAccountName(cbRequest.getDebtorName());
					cbTrns.setCstmAccountNo(cbRequest.getDebtorAccountNumber());
					cbTrns.setCstmAccountType(cbRequest.getDebtorAccountType());
					cbTrns.setDateTime(cbRequest.getDateTime());
					cbTrns.setFeeAmount(new BigDecimal(cbRequest.getFeeTransfer()));
					cbTrns.setFullTextRequest(strRequest);
					cbTrns.setKomiNoref(cbRequest.getNoRef());
					cbTrns.setKomiTrnsId(komiTrnsId);
		
					cbTrns.setOrgnlChnlNoref(cbRequest.getOriginalNoRef());
					cbTrns.setOrgnlDateTime(cbRequest.getOriginalDateTime());
					cbTrns.setTransactionType("DebitReversal");
					cbTrns.setTrnsDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
					cbTrns.setUpdateTime(LocalDateTime.now());
					
					String cbResponseClass = oCbResponse.getClass().getSimpleName();
					if (oCbResponse.getClass().getSimpleName().equals("DebitReversalResponsePojo")) {
						DebitReversalResponsePojo resp = (DebitReversalResponsePojo)oCbResponse;
						logger.debug("Response:" + resp.getStatus() + ", Reason:" + resp.getReason());
						cbTrns.setReason(resp.getReason());
						cbTrns.setResponse(resp.getStatus());
					}
					else {
						FaultPojo fault = (FaultPojo) oCbResponse;
						logger.debug("Response:" + fault.getCallStatus() + ", Reason:" + fault.getReasonCode());
						cbTrns.setReason(fault.getReasonCode());
						cbTrns.setResponse(fault.getCallStatus());
					}
					cbRepo.save(cbTrns);
				}
			})
		;
		
	}

		
		
}
