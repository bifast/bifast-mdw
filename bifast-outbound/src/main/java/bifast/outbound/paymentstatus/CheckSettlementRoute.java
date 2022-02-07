package bifast.outbound.paymentstatus;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.backgrndjob.processor.FindingSettlementProcessor;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.service.FlattenIsoMessageService;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CheckSettlementRoute extends RouteBuilder{
	@Autowired private FindingSettlementProcessor findingSettlementProcessor;
	@Autowired private FlattenIsoMessageService flattenIsoMessage;
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		
	    JacksonDataFormat businessMessageJDF = jdfService.wrapUnwrapRoot(BusinessMessage.class);

		from("direct:caristtl").routeId("komi.findsttl")
			.log(LoggingLevel.DEBUG, "komi.findsttl", "[ChnlReq:${exchangeProperty.prop_request_list.requestId}][CTReq] sedang cari settlement.")
			.setHeader("tmp_body", simple("${body}"))
	
			.process(findingSettlementProcessor)
	
			.filter().simple("${body} != null")
				.unmarshal().base64().unmarshal().zipDeflater()
				
				.log(LoggingLevel.DEBUG, "komi.findsttl", "[PyStsSAF:${exchangeProperty.pr_psrequest.channelNoref}] Ketemu settlement: ${body}")
	
				.unmarshal(businessMessageJDF)
				
				.process(new Processor() {
					public void process(Exchange exchange) throws Exception {
						BusinessMessage bm = exchange.getMessage().getBody(BusinessMessage.class);
						FlatPacs002Pojo pacs002 = flattenIsoMessage.flatteningPacs002(bm);
						exchange.getMessage().setBody(pacs002);
						ResponseMessageCollection rmc = exchange.getProperty("prop_response_list",ResponseMessageCollection.class);
						rmc.setFault(null);
						rmc.setSettlement(pacs002);
						exchange.setProperty("prop_response_list", rmc);
					}
				})
			.end()
			
//			.setHeader("hdr_settlement", constant("FOUND"))
			.setProperty("pr_settlement", constant("FOUND"))
			
			.filter().simple("${body} == null")
				.log(LoggingLevel.DEBUG, "komi.findsttl", 
						"[ChnlReq:${exchangeProperty.prop_request_list.requestId}][CTReq] Tidak ketemu settlement.")
//				.setHeader("hdr_settlement", constant("NOTFOUND"))
				.setProperty("pr_settlement", constant("NOTFOUND"))
				.setBody(simple("${header.tmp_body}"))
			.end()
			
			.removeHeader("tmp_body")
		;		

	}

}
