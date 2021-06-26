package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.service.SettlementProcessor;
import bifast.library.iso20022.custom.BusinessMessage;

//@Component
public class SettlementRoute extends RouteBuilder {

	@Autowired
	private SettlementProcessor settlementProcessor;
	
	JacksonDataFormat jsonBusinessMessageDataFormat = new JacksonDataFormat(BusinessMessage.class);

	private void configureJson() {
		jsonBusinessMessageDataFormat.addModule(new JaxbAnnotationModule());  //supaya nama element pake annot JAXB (uppercasecamel)
		jsonBusinessMessageDataFormat.enableFeature(DeserializationFeature.UNWRAP_ROOT_VALUE);
		jsonBusinessMessageDataFormat.setInclude("NON_NULL");
		jsonBusinessMessageDataFormat.setInclude("NON_EMPTY");
	}

	@Override
	public void configure() throws Exception {
		configureJson();

		from("sql:select id, SETTL_CONF_MSG_NAME as settlConfMesgName, "
				+ "SETTL_CONF_MSG_NAME as settlConfMesgName "
				+ "from settlement_proc where ack is null?"
					+ "outputType=SelectOne&"
					+ "maxMessagesPerPoll=1&"
					+ "outputClass=bifast.library.persist.SettlementProc")
		
			.log("${body}")
//			.process(inboundProcessor)
			.setHeader("rcv_msgid", simple("${body.bizMsgIdr}"))
			.log("MsgId: ${header.rcv_msgid}")
			.setHeader("rcv_bizSvc", simple("${body.bizSvc}"))
			.setBody(simple("${body.fullMessage}"))
			.choice()
				.when().simple("${header.rcv_bizSvc} == 'SETTLEMENTCONFIRMATION'")
					.to("seda:settlementconfirmation")
					.to("sql:update inbound_message set ack ='Y' where bizmsgid = :#${header.rcv_msgid}")
				.otherwise()
					.log("Bukan Settlement")
					.to("sql:update inbound_message set ack ='Y' where bizmsgid = :#${header.rcv_msgid}")
		;


		from("seda:settlementconfirmation")
			.unmarshal(jsonBusinessMessageDataFormat)
			.log("akan proses settlement")
			.process(settlementProcessor)
			.log("Ack msgid ${header.rcv_msgid}")
		;
		
	}

}
