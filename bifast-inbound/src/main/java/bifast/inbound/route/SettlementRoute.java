package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import bifast.inbound.processor.SettlementProcessor;
import bifast.library.iso20022.custom.BusinessMessage;

@Component
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

		from("sql:select id, SETTL_CONF_BIZMSGID as settlConfBizMsgId, "
				+ "orgnl_crdt_trn_bizmsgid as orgnlCrdtTrnReqBizMsgId, "
				+ "SETTL_CONF_MSG_NAME as settlConfMesgName, "
				+ "orign_bank as orignBank, recpt_bank as recptBank "
				+ "from settlement_proc where ack is null?"
					+ "outputType=SelectOne&"
					+ "outputHeader=rcv_qryresult&"
					+ "outputClass=bifast.library.model.SettlementProc&"
					+ "maxMessagesPerPoll=1")
		
			.log("${header.rcv_qryresult.settlConfBizMsgId}")
			.setHeader("rcv_msgid", simple("${body.settlConfBizMsgId}"))
			.process(settlementProcessor)
//			.setBody(simple("${body.fullMessage}"))
			.log("reversal : ${header.resp_reversal}")
			
			.to("sql:update settlement_proc set ack ='Y', for_reversal= :#${header.resp_reversal} "
					+ "where id = :#${header.rcv_qryresult.id}")
			.removeHeaders("rcv_*")
			.removeHeaders("resp_*")
		;


	
	}

}
