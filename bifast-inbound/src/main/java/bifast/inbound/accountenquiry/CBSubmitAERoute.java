package bifast.inbound.accountenquiry;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bifast.inbound.corebank.CbCallFaultProcessor;
import bifast.inbound.corebank.pojo.CbAccountEnquiryRequestPojo;
import bifast.inbound.corebank.pojo.CbAccountEnquiryResponsePojo;
import bifast.inbound.corebank.pojo2.AccountEnquiryInboundRequest;
import bifast.inbound.corebank.pojo2.AccountEnquiryInboundResponse;
import bifast.inbound.processor.EnrichmentAggregator;
import bifast.inbound.service.JacksonDataFormatService;
import bifast.inbound.service.TransRef;

@Component
public class CBSubmitAERoute extends RouteBuilder{
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private EnrichmentAggregator enrichmentAggregator;
	@Autowired private CbCallFaultProcessor cbFaultProcessor;
	
	@Value("${komi.isoadapter.merchant}")
	String merchant;
	@Value("${komi.isoadapter.terminal}")
	String terminal;
	@Value("${komi.bankcode}")
	String corebic;
	@Value("${komi.isoadapter.txid}")
	String txid;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat adapterAccountEnquiryResponseJDF = jdfService.basic(AccountEnquiryInboundResponse.class);
		JacksonDataFormat adapterAccountEnquiryRequestJDF = jdfService.unwrapRoot(AccountEnquiryInboundRequest.class);

		from("direct:cb_ae")
			.log("Terima di corebank: ${body}")
//			.marshal(accountEnquiryJDF)
			
	 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}]"
						+ " CB Request: ${body}")
	 		
	 		.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					CbAccountEnquiryRequestPojo aeRequest = exchange.getMessage().getBody(CbAccountEnquiryRequestPojo.class);
					AccountEnquiryInboundRequest req = new AccountEnquiryInboundRequest();
					
					req.setAccountNumber(aeRequest.getAccountNumber());
					req.setAmount(aeRequest.getAmount());
					req.setCategoryPurpose(aeRequest.getCategoryPurpose());
					TransRef.Ref ref = TransRef.newRef();
					req.setDateTime(ref.getDateTime());
					req.setMerchantType(merchant);
					req.setNoRef(ref.getNoRef());
					req.setRecipientBank(corebic);
					req.setTerminalId(terminal);
					req.setTransactionId(txid);
					exchange.getMessage().setBody(req);
				}
	 			
	 		})
	 		.marshal(adapterAccountEnquiryRequestJDF)
	 		.doTry()
				.setHeader("HttpMethod", constant("POST"))
				.enrich()
					.simple("{{komi.url.corebank}}?"
						+ "socketTimeout=10000&" 
						+ "bridgeEndpoint=true")
					.aggregationStrategy(enrichmentAggregator)
				.convertBodyTo(String.class)
		 		.log("[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] CB Response: ${body}")
				.unmarshal(adapterAccountEnquiryResponseJDF)

	 		.endDoTry()
	    	.doCatch(Exception.class)
				.log(LoggingLevel.ERROR, "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] Call CB Error.")
		    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		    	.process(cbFaultProcessor)
	    	.end()

		;
		
	}

}
