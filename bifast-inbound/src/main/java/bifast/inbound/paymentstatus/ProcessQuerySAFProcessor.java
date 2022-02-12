package bifast.inbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class ProcessQuerySAFProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> arr = exchange.getMessage().getBody(HashMap.class);
		
		CTQryDTO ct = new CTQryDTO();
		ct.setId(String.valueOf(arr.get("id")));
		ct.setKomiTrnsId((String)arr.get("komi_id"));
		ct.setEndToEndId((String) arr.get("e2e_id") );
		ct.setCtFullText(String.valueOf(arr.get("txtctreq")));
		ct.setRecipientBank((String)arr.get("recpt_bank"));
		ct.setReqBizmsgid((String)arr.get("req_bizmsgid"));
		ct.setCreateDt((LocalDateTime) arr.get("create_dt"));
		ct.setPsCounter((int) arr.get("ps_counter"));
		ct.setPsCounter(ct.getPsCounter()+1);
			
		ObjectMapper map = new ObjectMapper();
		map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		
		exchange.setProperty("pr_psrequest", ct);
	
		// request message asli untuk keperluan cari settlement harus di unzip dulu
		exchange.getMessage().setBody(ct.getCtFullText());


	}

}
