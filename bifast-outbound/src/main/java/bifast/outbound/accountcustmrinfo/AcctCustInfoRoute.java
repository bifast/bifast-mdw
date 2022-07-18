package bifast.outbound.accountcustmrinfo;

import java.time.format.DateTimeFormatter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcctCustInfoRoute extends RouteBuilder{
    @Autowired private ACIResponseProc aciResponsePrc;
    @Autowired private PrepACIRequestProcessor prepACIPrc;
    
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void configure() throws Exception {
//		.validate(acctCustInfoValidate)
		
		from("direct:acctcustmrinfo").routeId("komi.acctcustinfo")
			
			// build request msg
			.process(prepACIPrc)
			// call ke corebank
			.to("direct:accinfo")
	
			.log(LoggingLevel.DEBUG, "komi.acctcustinfo", "[${exchangeProperty.prop_request_list.msgName}:"
					+ "${exchangeProperty.prop_request_list.requestId}] prepare response ${exchangeProperty.pr_response}")
			
			// prepare output for client
			.process(aciResponsePrc) 
			
		;
	}

}
