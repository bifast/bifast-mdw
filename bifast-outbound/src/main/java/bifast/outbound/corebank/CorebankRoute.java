package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.corebank.pojo.CBDebitRequestPojo;
import bifast.outbound.corebank.pojo.CBDebitResponsePojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class CorebankRoute extends RouteBuilder{

	@Autowired
	private SaveCBTableProcessor saveCBTableProcessor;
	@Autowired
	private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {
		JacksonDataFormat chnlDebitRequestJDF = jdfService.basic(CBDebitRequestPojo.class);
		JacksonDataFormat chnlDebitResponseJDF = jdfService.basic(CBDebitResponsePojo.class);
				
		from("seda:callcb").routeId("komi.cb.corebank")
			.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChnlReq:${header.hdr_request_list.requestId}][${header.hdr_request_list.msgName}] call Corebank")

			.marshal(chnlDebitRequestJDF)
			
			.setHeader("cb_request", simple("${body}"))
			
	 		.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChReq:${header.hdr_request_list.requestId}][CT] CB Request: ${body}")
			.setHeader("HttpMethod", constant("POST"))
//			.enrich("http:{{komi.cb-url}}?"
//					+ "bridgeEndpoint=true",
//					enrichmentAggregator)
//			.convertBodyTo(String.class)
//	 		.log(LoggingLevel.DEBUG, "bifast.outbound.corebank", "[ChRefId:${header.hdr_chnlRefId}] CB Response: ${body}")
			
			.setBody(constant("{\n"
					+ "\"transactionId\" : \"000000\",\n"
					+ "\"dateTime\" : \"2021-10-26T08:45:20.201\",\n"
					+ "\"merchantType\" : \"6000\",\n"
					+ "\"terminalId\" : \"00000\",\n"
					+ "\"noRef\" : \"KOM00000000\",\n"
					+ "\"status\" : \"ACTC\",\n"
					+ "\"reason\" : \"U000\",\n"
					+ "\"additionalInfo\" : \"ga ada\",\n"
					+ "\"accountNumber\" : \"0000000\"\n"
					+ "}\n"))

	 		.log(LoggingLevel.DEBUG, "komi.cb.corebank", "[ChReq:${header.hdr_request_list.requestId}][CT] CB Response: ${body}")

	 		.unmarshal(chnlDebitResponseJDF)
	 		
	 		.log("corebank status: ${body.status}")
			.choice()
				.when().simple("${body.status} == 'ACTC'")
					.log("ACTC")
			 		.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
							CBDebitResponsePojo response = exchange.getMessage().getBody(CBDebitResponsePojo.class);
//							rmc.setCbResponse(response.getStatus());
							rmc.setDebitResponse(response);
							exchange.getMessage().setHeader("hdr_response_list", rmc);
						}
			 		})
			 	.endChoice()
			 	
			 	.otherwise()
			 		.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							CBDebitResponsePojo response = exchange.getMessage().getBody(CBDebitResponsePojo.class);
							ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);
							FaultPojo fault = new FaultPojo();
							fault.setCallStatus("REJECT-CB");
							fault.setResponseCode(response.getStatus());
							fault.setReasonCode(response.getReason());
							rmc.setDebitResponse(fault);
							rmc.setFault(fault);
							exchange.getMessage().setHeader("hdr_response_list", rmc);
							exchange.getMessage().setBody(fault);
						}
			 		})

			.end()
			
//			.to("seda:savecbtable?exchangePattern=InOnly")


			.removeHeaders("cb_*")
		;
		
		from("seda:savecbtable").routeId("komi.cb.save_logtable")
			.process(saveCBTableProcessor)
		;
	}

}
