package bifast.inbound.reversecrdttrns;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.IsoAERequestPrc;
import bifast.inbound.corebank.isopojo.AccountCustInfoRequest;
import bifast.inbound.reversecrdttrns.processor.CbAcctCustInfoRequestProcessor;
import bifast.inbound.reversecrdttrns.processor.CheckDebitHistoryProcessor;
import bifast.inbound.reversecrdttrns.processor.ReverseCTRejectResponseProcessor;
import bifast.inbound.reversecrdttrns.processor.SaveReversalCTProcessor;
import bifast.inbound.service.JacksonDataFormatService;


@Component
public class ReverseCTRoute extends RouteBuilder {
	@Autowired private CheckDebitHistoryProcessor checkDebitHistoryProcessor;
//	@Autowired private CbAcctCustInfoRequestProcessor cbAcctCustInfoRequestProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private SaveReversalCTProcessor saveRCTProcessor;
	@Autowired private ReverseCTRejectResponseProcessor rejectResponsePrc;
	@Autowired private IsoAERequestPrc isoAERequestPrc;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat aciJDF = jdfService.basic(AccountCustInfoRequest.class);
		
		from("direct:reverct").routeId("komi.reversect")

			.log("akan reverse Credit Transfer")
			
			// cari original transaksi 
			.process(checkDebitHistoryProcessor)
			
			.choice()
				.when().simple("${exchangeProperty.pr_revCTCheckRsl} == 'AmountMatch'")
					.log("CT asal ketemu")
					// lakukan accountCustomerInfo
//					.process(cbAcctCustInfoRequestProcessor)
					.process(isoAERequestPrc)
					.to("direct:isoadpt")

				.otherwise()
					.log("CT asal tidak ketemu")
					.process(rejectResponsePrc)
			.end()
			
			
			.log("${body.status}")
			
			.choice()
				.when(simple("${body.status} == 'ACTC'"))
					.log("karena sukses")
					// siapkan response
					// simpan ke table crdt_transfer unt diproses saf klo sudah settlement
					.process(saveRCTProcessor)
					
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
