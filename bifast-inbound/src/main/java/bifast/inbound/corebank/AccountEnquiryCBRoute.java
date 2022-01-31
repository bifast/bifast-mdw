package bifast.inbound.corebank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.isopojo.AccountEnquiryInboundRequest;
import bifast.inbound.corebank.isopojo.AccountEnquiryInboundResponse;
import bifast.inbound.model.CorebankTransaction;
import bifast.inbound.pojo.FaultPojo;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.repository.CorebankTransactionRepository;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalRequestPojo;
import bifast.inbound.reversecrdttrns.pojo.DebitReversalResponsePojo;
import bifast.inbound.service.JacksonDataFormatService;

@Component
public class AccountEnquiryCBRoute extends RouteBuilder {
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	@Autowired private CorebankTransactionRepository cbRepo;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat aeRequestJDF = jdfService.basic(AccountEnquiryInboundRequest.class);
		JacksonDataFormat aeResponseJDF = jdfService.basic(AccountEnquiryInboundResponse.class);

		from("direct:cbacctenq").routeId("komi.cb.acctenq")
			.setHeader("cbae_objRequest", simple("${body}"))
			.marshal(aeRequestJDF)
			.setHeader("cbae_strRequest", simple("${body}"))
			.log("CB AcctEnquiry request: ${body}")
		
			.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich("http:{{komi.url.isoadapter}}/accountenquiry?"
		//						+ "socketTimeout={{komi.timeout}}&" 
						+ "bridgeEndpoint=true",
						enrichmentAggregator)
				.convertBodyTo(String.class)
				.log("CB DebitRev response: ${body}")
				
				.unmarshal(aeResponseJDF)

//				.setExchangePattern(ExchangePattern.InOnly)
//				.to("seda:savecbaccenqr")

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
				.end()
				
			.endDoTry()
			
			.doCatch(Exception.class)
				// TODO bagaimana kalau error waktu call corebanking
				.log("Gagal ke corebank")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
//				.setBody(constant(null))
			.end()
	
		;
	
		from("seda:savecbaccenqr?concurrentConsumers=3")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					ProcessDataPojo processData = exchange.getProperty("prop_process_data", ProcessDataPojo.class);
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
