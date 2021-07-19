package bifast.inbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SettlementRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("sql:select st.id as settlId, "
				+ "st.settl_conf_bizmsgid as settBizMsgId, "
				+ "ct.id as crdtId, "
				+ "ct.crdttrn_resp_status as crdtStatus "
				+ "from SETTLEMENT st "
				+ "join CREDIT_TRANSFER ct on ct.crdttrn_req_bizmsgid  = st.orgnl_crdt_trn_bizmsgid "
				+ "where st.ack is null?"
					+ "outputType=SelectList&"
					+ "outputHeader=rcv_qryresult&"
					+ "maxMessagesPerPoll=3").routeId("QuerySttlement")

			.log("${header.rcv_qryresult}")
			
			.choice()
				.when().simple("${header.rcv_qryresult[crdtStatus]} == 'ACTC'")
					.log("${header.rcv_qryresult[settBizMsgId]} Accepted")
					.to("sql:update CREDIT_TRANSFER set "
							+ "settlconf_bizmsgid = :#${header.rcv_qryresult[settBizMsgId]} "
							+ "where id = :#${header.rcv_qryresult[crdtId]}")
					.to("sql:update SETTLEMENT set ack = 'Y' "
							+ "where id = :#${header.rcv_qryresult[settlId]}")

				.otherwise()
					.log("${header.rcv_qryresult[settBizMsgId]} Rejected")
					.to("sql:update SETTLEMENT set ack = 'Y', for_reversal = 'Y' "
							+ "where id = :#${header.rcv_qryresult[settlId]}")
			.end()

			.removeHeaders("rcv_*")
			.removeHeaders("resp_*")
		;


	
	}

}
