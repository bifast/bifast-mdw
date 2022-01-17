package bifast.outbound.paymentstatus;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class SettlementSAFRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("timer:sttlsaf?fixedRate=true&period=60000")
			.log("Akan cek settlement in waiting....")
		;
		
		from("sql:select * from kc_credit_transfer where sttl_bizmsgid = 'WAITING'")
			.log("Akan cek settlement ....")

		;
	}


}
