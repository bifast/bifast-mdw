package bifast.inbound.reversecrdttrns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.reversecrdttrns.pojo.AccountCustInfoRequestPojo;
import bifast.inbound.reversecrdttrns.processor.CbAcctCustInfoRequestProcessor;
import bifast.inbound.service.JacksonDataFormatService;


@Component
public class ReverseCTRoute extends RouteBuilder {
	@Autowired private ReverseCTProcessor reverseCTProcessor;
	@Autowired private ReverseCTResponseProcessor responseProcessor;
	@Autowired private CbAcctCustInfoRequestProcessor cbAcctCustInfoRequestProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	
	@Override
	public void configure() throws Exception {

		JacksonDataFormat aciJDF = jdfService.basic(AccountCustInfoRequestPojo.class);
		
		from("direct:reverct").routeId("reversect")

			.log("akan reverse Credit Transfer")
			
			// lakukan accountCustomerInfo
			.process(cbAcctCustInfoRequestProcessor)
//			.log("ACI: ${body}")
			.to("seda:isoadpt")
			
			.log("${body.status}")
			// jika sukses siapkan response ACTC
			
			.choice()
				.when(simple("${body.status} == 'ACTC'"))
					.log("karena sukses")
				.otherwise()
					.log("tidak sukses")
			.end()

			.stop()

			// lalu save ke table credit transfer unt diproses setelah ada settlement
			
			// jika gagal siapkan repsonse REJECT, 
			//TODO lalu lapor ke admin
			
//			.process(reverseCTProcessor)
			
//			.log("${body.class}")
//			.filter().simple("${body.class} endsWith 'DebitReversalRequestPojo' ")
//				.log("akan call cb debit_reversal")
//				.to("direct:cbdebitreversal")
//				.log("selesai call cb debit_reversal ${body}")
//				
//			.end()
//			
//			.to("seda:save_ct")
//
//			.process(responseProcessor)		
			
		;

	
	}

}
