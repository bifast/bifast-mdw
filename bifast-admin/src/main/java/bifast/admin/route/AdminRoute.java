package bifast.admin.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.OutMessageList;
import bifast.admin.pojo.TransxHistoryRequestParam;
import bifast.admin.processor.TransactionRecordProcessor;
import bifast.library.model.OutboundMessage;

@Component
public class AdminRoute extends RouteBuilder {

	@Autowired
	private TransactionRecordProcessor transactionProcessor;
	
	JacksonDataFormat jsonChnlAccountEnqrReqFormat = new JacksonDataFormat(TransxHistoryRequestParam.class);

	@Override
	public void configure() throws Exception {

		restConfiguration()
			.component("servlet")
		;
		
		rest("/transmonitoring")
			.get("/getTransaction")
	//			.outType(OutMessageList.class)
				.consumes("application/json")
				.produces("application/json")
					.to("direct:history")
					
			.post("/filterdata")
				.consumes("application/json")
				.produces("application/json")
				.type(TransxHistoryRequestParam.class)
				.to("direct:filterhistory")
			;
		
		from("direct:history").routeId("history")
			.process(transactionProcessor)
			.marshal().json(JsonLibrary.Jackson)
		;

		from("direct:filterhistory").routeId("filterhistory")
			.convertBodyTo(String.class)
			.unmarshal(jsonChnlAccountEnqrReqFormat)
			.process(transactionProcessor)
			.marshal(jsonChnlAccountEnqrReqFormat)
		;

	}

}
