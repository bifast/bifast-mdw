package bifast.mock.route;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.SerializeExceptFilter;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.mock.inbound.BuildAERequestProcessor;
import bifast.mock.inbound.BuildCTRequestProcessor;
import bifast.mock.inbound.BuildSttlProcessor;
import bifast.mock.inbound.CTRequestPojo;
import bifast.mock.inbound.CTResponsePojo;

@Component
public class InboundRoute extends RouteBuilder {
	
	@Autowired BuildAERequestProcessor buildAERequest;
	@Autowired BuildCTRequestProcessor buildCTRequest;
	@Autowired BuildSttlProcessor buildSettlement;

	JacksonDataFormat ctRequestJDF = new JacksonDataFormat(CTRequestPojo.class);
	JacksonDataFormat ctResponseJDF = new JacksonDataFormat(CTResponsePojo.class);
	JacksonDataFormat busMesgJDF = new JacksonDataFormat(BusinessMessage.class);

	private void configureJson() {
		ctRequestJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		ctRequestJDF.setInclude("NON_NULL");
		ctRequestJDF.setInclude("NON_EMPTY");
		
		ctResponseJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		ctResponseJDF.setInclude("NON_NULL");
		ctResponseJDF.setInclude("NON_EMPTY");
		
		busMesgJDF.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		busMesgJDF.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		busMesgJDF.enableFeature(SerializationFeature.WRAP_ROOT_VALUE);
		busMesgJDF.setInclude("NON_NULL");
		busMesgJDF.setInclude("NON_EMPTY");

	}

	
	@Override
	public void configure() throws Exception {

		onException(Exception.class)
			.log("Inbound error.")
	    	.log(LoggingLevel.ERROR, "${exception.stacktrace}")
		;
		
		
		configureJson();
		
		restConfiguration()
			.component("servlet")
		;
		
		rest("/")
			.post("/inbound")
				.consumes("application/json")
				.to("direct:inbound")
		;
	

		from("direct:inbound").routeId("InboundMsg")
			.convertBodyTo(String.class)
			.log("Terima di mock")
			.log("${body}")
			
			.unmarshal(ctRequestJDF)
			.setHeader("inb_request", simple("${body}"))
			.process(buildAERequest)
			.marshal(busMesgJDF)
			.log("AE Request: ${body}")	

			.to("rest:post:?host={{komi.inbound-url}}"
				+ "&exchangePattern=InOnly"
					+ "&bridgeEndpoint=true"
				)
			.convertBodyTo(String.class)
			.log("AE Response: ${body}")
			.unmarshal(busMesgJDF)
			
			.setHeader("inb_aeresponse", simple("${body}"))
			.setHeader("inb_respCode", simple("${body.document.fiToFIPmtStsRpt.txInfAndSts[0].txSts}"))
			.log("${header.inb_respCode}")

//			.process(new Processor() {
//				public void process(Exchange exchange) throws Exception {
//					BusinessMessage msg = exchange.getMessage().getBody(BusinessMessage.class);
//					String responseCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts();
//					exchange.getMessage().setHeader("inb_responseCd", responseCode);
//				}
//			})

			.choice()
				.when().simple("${header.inb_respCode} == 'ACTC'")
					.log("lanjut dengan CT")
					.to("direct:inb_ct")
				.otherwise()
					.log("kita sudahi sampai disini")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {
							BusinessMessage msg = exchange.getMessage().getBody(BusinessMessage.class);
							String responseCode = exchange.getMessage().getHeader("inb_aeresponse", String.class);
							String reasonCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry();
							CTResponsePojo inbResponse = new CTResponsePojo();
							inbResponse.setResponseCode(responseCode);
							inbResponse.setReasonCode(reasonCode);
							exchange.getMessage().setBody(inbResponse);
						}
					})

			.end()
			
			.log("milestone 37")

			.filter().simple("${body.responseCode} == 'ACTC' ")
				.log("Akan kirim settlement")
				.setExchangePattern(ExchangePattern.InOnly)
				.to("seda:settlement")
//				.to("seda:settlement&exchangePattern=InOnly")
				.log("selesai kirim settlement")
			.end()

			.marshal(ctResponseJDF)  // remark bila rejection

			// .process(proxyResolutionResponseProcessor)
			.log("Response mock: ${body}")
			.removeHeaders("*")
		;

		from("direct:inb_ct").routeId("inbound_ct")
			.log("start kirim ct")
			.process(buildCTRequest)
			.marshal(busMesgJDF)
			.log("CT Request: ${body}")
			
			.to("rest:post:?host={{komi.inbound-url}}"
					+ "&exchangePattern=InOnly"
						+ "&bridgeEndpoint=true"
					)
			.convertBodyTo(String.class)
			.log("CT Response: ${body}")
			.unmarshal(busMesgJDF)
			.setHeader("inb_ctResponse", simple("${body}"))

			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					BusinessMessage msg = exchange.getMessage().getBody(BusinessMessage.class);
					String responseCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts();
					String reasonCode = msg.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry();
					CTResponsePojo inbResponse = new CTResponsePojo();
					inbResponse.setResponseCode(responseCode);
					inbResponse.setReasonCode(reasonCode);
					exchange.getMessage().setBody(inbResponse);
				}
			})
			
		;
		
		from("seda:settlement").routeId("inbound_sttl")
			.delay(7000)
			.process(buildSettlement)
			.marshal(busMesgJDF)
			.log("Settlement: ${body}")	

			.to("rest:post:?host={{komi.inbound-url}}"
//				+ "&exchangePattern=InOnly"
					+ "&bridgeEndpoint=true"
				)
			.convertBodyTo(String.class)
		;
		
	}

}
