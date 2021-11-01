package bifast.outbound.paymentstatus.processor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class ProcessQuerySAFProcessor implements Processor{
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	@Override
	public void process(Exchange exchange) throws Exception {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> arr = exchange.getMessage().getBody(HashMap.class);
		UndefinedCTPojo ct = new UndefinedCTPojo();
		ct.setId(String.valueOf(arr.get("id")));
		ct.setKomiTrnsId((String)arr.get("komi_id"));
		ct.setRecipientBank((String)arr.get("recpt_bank"));
		ct.setReqBizmsgid((String)arr.get("req_bizmsgid"));
		ct.setChannelType((String)arr.get("channel_type"));
		
		Timestamp ldt = (Timestamp) arr.get("request_time");	
		ct.setOrgnlDateTime(ldt.toLocalDateTime());
		
		exchange.getMessage().setHeader("ps_request", ct);
		
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		rmw.setChannelRequest(ct);
		rmw.setKomiStart(Instant.now());
		rmw.setMsgName("PaymentStsSAF");
		exchange.getMessage().setHeader("hdr_request_list", rmw);		
	}

}
