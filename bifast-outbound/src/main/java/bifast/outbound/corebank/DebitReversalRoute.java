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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.corebank.pojo.DebitReversalResponsePojo;
import bifast.outbound.corebank.processor.DebitReversalFaultProcessor;
import bifast.outbound.corebank.processor.DebitReversalRequestProcessor;
import bifast.outbound.model.CorebankTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.processor.EnrichmentAggregator;
import bifast.outbound.repository.CorebankTransactionRepository;
import bifast.outbound.service.JacksonDataFormatService;
import bifast.outbound.service.UtilService;

@Component
public class DebitReversalRoute extends RouteBuilder{
//	@Autowired private CbFaultExceptionProcessor cbFaultProcessor;
	@Autowired private CorebankTransactionRepository cbRepo;
	@Autowired private DebitReversalFaultProcessor dbrevFaultProcessor;
	@Autowired private DebitReversalRequestProcessor debitReversalRequestProcessor;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private UtilService utilService;

//	private static Logger logger = LoggerFactory.getLogger(DebitReversalRoute.class);

	@Override
	public void configure() throws Exception {
		
		JacksonDataFormat debitReversalRequestJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.wrapRoot(DebitReversalResponsePojo.class);
	
		from("direct:debitreversal").routeId("komi.debit_rev")

//			.log(LoggingLevel.DEBUG, "komi.debit_rev", 
//					"[${exchangeProperty.prop_request_list.msgName}:${exchangeProperty.prop_request_list.requestId}] Akan debit-reversal")
			.setHeader("ct_progress", constant("REVERSAL"))
			.setHeader("revct_tmpbody", simple("${body}"))
				
			// build reversal message from rmw.getChnlCreditTransferRequest
			.process(debitReversalRequestProcessor)
//			.setHeader("revct_revRequest", simple("${body}"))
			.marshal(debitReversalRequestJDF)

			//submit reversal
			.log(LoggingLevel.DEBUG, "komi.debit_rev", "[${header.cihubMsgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] POST {{komi.url.isoadapter.reversal}}")
			.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Request ISOAdapter: ${body}")
			.to("direct:postcreditreversal?exchangePattern=InOptionalOut")
			
			.setExchangePattern(ExchangePattern.InOnly)
			.to("seda:savecbdebitrevr")

			.log(LoggingLevel.DEBUG, "komi.debit_rev", 
					"[${header.cihubMsgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] response class ${body.class}")

			.setBody(simple("${header.revct_tmpbody}"))
			.removeHeaders("revct_*")
		;
		
		
		from("direct:postcreditreversal").routeId("komi.postcrdtrev")

			.removeHeaders("hdr_*")

			.doTry()
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
			            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
					}
				})
	
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("{{komi.url.isoadapter.reversal}}?"
	//					+ "socketTimeout=7000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
	
				.convertBodyTo(String.class)
				
				.log("[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Response ISOAdapter: ${body}")
	
				.unmarshal(debitReversalResponseJDF)
				
			.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] Call Corebank Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(dbrevFaultProcessor)
			.end()
		;
		
		from("seda:savecbdebitrevr?concurrentConsumers=5").routeId("komi.savecbdebitrevr")
			.log(LoggingLevel.DEBUG, "komi.savecbdebitrevr", 
				"[${header.cihubMsgName}:${exchangeProperty.prop_request_list.requestId}] akan save Debit Reversal ke CB-log")
			
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
					String komiTrnsId = rmw.getKomiTrxId();
					DebitReversalRequestPojo cbRequest = rmw.getDebitReversalRequest();
//					DebitReversalRequestPojo cbRequest = exchange.getMessage().getHeader("revct_revRequest",DebitReversalRequestPojo.class);
	
//					String strRequest = exchange.getMessage().getHeader("revct_strRequest", String.class);
					CorebankTransaction cbTrns = new CorebankTransaction();
					cbTrns.setCreditAmount(new BigDecimal(cbRequest.getAmount()));
					cbTrns.setCstmAccountName(cbRequest.getDebtorName());
					cbTrns.setCstmAccountNo(cbRequest.getDebtorAccountNumber());
					cbTrns.setCstmAccountType(cbRequest.getDebtorAccountType());
					cbTrns.setDateTime(cbRequest.getDateTime());
					cbTrns.setFeeAmount(new BigDecimal(cbRequest.getFeeTransfer()));
					cbTrns.setFullTextRequest(utilService.serialize(cbRequest));
					cbTrns.setKomiNoref(cbRequest.getNoRef());
					cbTrns.setKomiTrnsId(komiTrnsId);
		
					cbTrns.setOrgnlChnlNoref(cbRequest.getOriginalNoRef());
					cbTrns.setOrgnlDateTime(cbRequest.getOriginalDateTime());
					cbTrns.setTransactionType("DebitReversal");
					cbTrns.setTrnsDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
					cbTrns.setUpdateTime(LocalDateTime.now());
					
					DebitReversalResponsePojo resp = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
					cbTrns.setReason(resp.getReason());
					cbTrns.setResponse(resp.getStatus());

					cbRepo.save(cbTrns);
				}
			})
		;
		
	}

		
		
}
