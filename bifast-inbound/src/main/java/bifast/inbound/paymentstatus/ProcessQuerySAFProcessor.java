package bifast.inbound.paymentstatus;

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
	@Autowired private ChannelRepository channelRepo;
	@Autowired private ChannelTransactionRepository channelTrnsRepo;

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
		ct.setPsCounter((int) arr.get("ps_counter"));
		ct.setPsCounter(ct.getPsCounter()+1);
			
		ChannelTransaction chnlTrns = channelTrnsRepo.findById(ct.getKomiTrnsId()).orElse(new ChannelTransaction());
		ct.setOrgnlDateTime(chnlTrns.getRequestTime());
		ct.setChannelNoref(chnlTrns.getChannelRefId());
		
		Channel channel = channelRepo.findById(chnlTrns.getChannelId()).orElse(new Channel());
		ct.setChannelType(channel.getChannelType());
	
		
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		
		rmw.setChannelRequest(ct);
		rmw.setMsgName("PyStsSAF");
		
		rmw.setRequestId(chnlTrns.getChannelRefId());
		rmw.setChannelId(channel.getChannelId());
		rmw.setChannelType(channel.getChannelType());

		rmw.setMerchantType(channel.getMerchantCode());
		rmw.setKomiTrxId(ct.getKomiTrnsId());
		rmw.setKomiStart(chnlTrns.getRequestTime().atZone(ZoneOffset.systemDefault()).toInstant());
		
		ObjectMapper map = new ObjectMapper();
		map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
	
		String chnlRequestText = chnlTrns.getTextMessage();
		
		ChnlCreditTransferRequestPojo chnlCTReq = map.readValue(chnlRequestText, ChnlCreditTransferRequestPojo.class);
		chnlCTReq.setDateTime(chnlTrns.getRequestTime());
		
		rmw.setChnlCreditTransferRequest(chnlCTReq);
		
		exchange.setProperty("pr_psrequest", ct);

		exchange.setProperty("prop_request_list", rmw);	

		ResponseMessageCollection rmc = new ResponseMessageCollection();
		exchange.setProperty("prop_response_list", rmc);	
		
		// request message asli untuk keperluan cari settlement harus di unzip dulu
		exchange.getMessage().setBody(ct.getCtFullText());


	}

}
