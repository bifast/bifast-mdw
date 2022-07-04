package bifast.outbound.reversect;

import java.time.Instant;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.service.UtilService;

@Component
public class InitRevCTProc implements Processor {
	@Autowired	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> arr = exchange.getMessage().getBody(HashMap.class);
		
		RCTQueryResultDTO qryRes = new RCTQueryResultDTO();
		qryRes.setEndToEndId((String)arr.get("e2e_id"));
		qryRes.setErrorMsg((String)arr.get("error_message"));
		qryRes.setFullTextMsg((String)arr.get("txt_req_msg"));
		qryRes.setId((Long)arr.get("id"));
		qryRes.setKomiTrnsId((String)arr.get("komi_id"));
		
		exchange.setProperty("pr_rctrequest", qryRes);
		
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		
		String komiTrnsId = utilService.genKomiTrnsId();		

		rmw.setKomiTrxId(komiTrnsId);
		rmw.setKomiStart(Instant.now());
//		rmw.setMsgName("RevCT");
		rmw.setRequestId(qryRes.getEndToEndId());
		
		exchange.setProperty("prop_request_list", rmw);

		ResponseMessageCollection responseCol = new ResponseMessageCollection();
		exchange.setProperty("prop_response_list", responseCol);
		
	}

}
