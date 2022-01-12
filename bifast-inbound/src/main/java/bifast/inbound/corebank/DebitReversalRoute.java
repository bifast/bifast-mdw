package bifast.inbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.repository.CorebankTransactionRepository;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalResponsePojo;
import bifast.inbound.service.JacksonDataFormatService;

@Component
public class DebitReversalRoute extends RouteBuilder {
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private CorebankTransactionRepository cbRepo;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat debitReversalReqJDF = jdfService.basic(DebitReversalRequestPojo.class);
		JacksonDataFormat debitReversalResponseJDF = jdfService.basic(DebitReversalResponsePojo.class);

		from("direct:cbdebitreversal").routeId("komi.cb.debitreversal")
			.setHeader("cbrev_objRequest", simple("${body}"))
			.marshal(debitReversalReqJDF)
			.setHeader("cbrev_strRequest", simple("${body}"))
			.log("CB DebitRev request: ${body}")
		
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.url.isoadapter}}/debitreversal?"
		//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.log("CB DebitRev response: ${body}")
				
				.unmarshal(debitReversalResponseJDF)

				.setExchangePattern(ExchangePattern.InOnly)
				.to("seda:savecbtrns")

			.doCatch(Exception.class)
				// TODO bagaimana kalau error waktu call corebanking
				.log("Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
//				.setBody(constant(null))
			.end()
	
		;
	
		from("seda:savecbtrns?concurrentConsumers=5")
			.log("akan save CB transaksi")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
					String komiTrnsId = processData.getKomiTrnsId();
					DebitReversalResponsePojo cbResponse = exchange.getMessage().getBody(DebitReversalResponsePojo.class);
					DebitReversalRequestPojo cbRequest = exchange.getMessage().getHeader("cbrev_objRequest", DebitReversalRequestPojo.class);
					String strRequest = exchange.getMessage().getHeader("cbrev_strRequest", String.class);

					CorebankTransaction cbTrns = new CorebankTransaction();
					cbTrns.setCreditAmount(new BigDecimal(cbRequest.getAmount()));
					cbTrns.setCstmAccountName(cbRequest.getCreditorName());
					cbTrns.setCstmAccountNo(cbRequest.getCreditorAccountNumber());
					cbTrns.setCstmAccountType(cbRequest.getCreditorAccountType());
					cbTrns.setDateTime(cbRequest.getDateTime());
//					cbTrns.setDebitAmount(cbRequest.getAmount());
					cbTrns.setFeeAmount(new BigDecimal(cbRequest.getFeeTransfer()));
					cbTrns.setFullTextRequest(strRequest);
					cbTrns.setKomiNoref(cbRequest.getNoRef());
					cbTrns.setKomiTrnsId(komiTrnsId);
		
					cbTrns.setOrgnlChnlNoref(cbRequest.getOriginalNoRef());
					cbTrns.setOrgnlDateTime(cbRequest.getOriginalDateTime());
					cbTrns.setReason(cbResponse.getReason());
					cbTrns.setResponse(cbResponse.getStatus());
					cbTrns.setTransactionType("DebitReversal");
					cbTrns.setTrnsDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")));
					cbTrns.setUpdateTime(LocalDateTime.now());
					cbRepo.save(cbTrns);
				}
				
			})
		;
		
	}

}
