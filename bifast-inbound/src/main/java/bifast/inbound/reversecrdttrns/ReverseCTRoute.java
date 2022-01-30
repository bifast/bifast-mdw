package bifast.inbound.reversecrdttrns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.reversecrdttrns.pojo.AccountCustInfoRequestPojo;
import bifast.inbound.reversecrdttrns.processor.CbAcctCustInfoRequestProcessor;
import bifast.inbound.reversecrdttrns.processor.SaveReversalCTProcessor;
import bifast.inbound.service.JacksonDataFormatService;


@Component
public class ReverseCTRoute extends RouteBuilder {
	@Autowired private ReverseCTProcessor reverseCTProcessor;
	@Autowired private ReverseCTResponseProcessor responseProcessor;
	@Autowired private CbAcctCustInfoRequestProcessor cbAcctCustInfoRequestProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveReversalCTProcessor saveRCTProcessor;
	
	@Override
	public void configure() throws Exception {

		JacksonDataFormat aciJDF = jdfService.basic(AccountCustInfoRequestPojo.class);
		
		from("direct:reverct").routeId("reversect")

			.log("akan reverse Credit Transfer")
			
			// lakukan accountCustomerInfo
			.process(cbAcctCustInfoRequestProcessor)
//			.log("ACI: ${body}")
			.to("direct:isoadpt")
			
			.log("${body.status}")
			
			.choice()
				.when(simple("${body.status} == 'ACTC'"))
					.log("karena sukses")
					// jika sukses siapkan simpan ke table crdt_transfer unt diproses saf klo sudah settlement
					.process(saveRCTProcessor)
					// lalu siapan response
				.otherwise()
					.log("tidak sukses")
					// jika gagal siapkan repsonse REJECT, 
					//TODO lalu lapor ke admin
			.end()

			.stop()

			
			
//			.process(reverseCTProcessor)
			
//			
//			.to("seda:save_ct")
//
//			.process(responseProcessor)		
			
		;

	
	}

}
