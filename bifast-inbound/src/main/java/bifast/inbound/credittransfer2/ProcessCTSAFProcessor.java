package bifast.inbound.credittransfer2;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.inbound.repository.CreditTransferRepository;


@Component
public class ProcessCTSAFProcessor implements Processor{
	@Autowired private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> arr = exchange.getMessage().getBody(HashMap.class);

		CTSAFPojo ct = new CTSAFPojo();
		ct.setFullRequestMesg(String.valueOf(arr.get("full_request_msg")));
		ct.setId(String.valueOf(arr.get("id")));
		ct.setKomiTrnsId(String.valueOf(arr.get("komi_trns_id")));
		ct.setResponseCode(String.valueOf(arr.get("response_code")));
		
		Timestamp ldt = (Timestamp) arr.get("request_time");	
//		ct.setOrgnlDateTime(ldt.toLocalDateTime());
//		
//		exchange.getMessage().setHeader("ps_request", ct);
//		
//		ChannelTransaction chnlTrns = channelTrnsRepo.findById(ct.getKomiTrnsId()).orElse(new ChannelTransaction());
//		
//		Channel channel = channelRepo.findById(chnlTrns.getChannelId()).orElse(new Channel());
//		CreditTransfer creditTransfer = creditTransferRepo.findByKomiTrnsId(ct.getKomiTrnsId()).orElse(new CreditTransfer());
//		
//		RequestMessageWrapper rmw = new RequestMessageWrapper();
//		
//		rmw.setChannelRequest(ct);
//		rmw.setMsgName("PaymentStsSAF");
//		
//		rmw.setRequestId(chnlTrns.getChannelRefId());
//		rmw.setChannelId(channel.getChannelId());
//		rmw.setChannelType(channel.getChannelType());
//
//		rmw.setMerchantType(channel.getMerchantCode());
//		rmw.setKomiTrxId(ct.getKomiTrnsId());
//		rmw.setKomiStart(chnlTrns.getRequestTime().atZone(ZoneOffset.systemDefault()).toInstant());
//		
//		ObjectMapper map = new ObjectMapper();
//		map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
//	
//		String chnlRequestText = chnlTrns.getTextMessage();
//		
//		ChnlCreditTransferRequestPojo chnlCTReq = map.readValue(chnlRequestText, ChnlCreditTransferRequestPojo.class);
//		chnlCTReq.setDateTime(chnlTrns.getRequestTime());
//		
//		rmw.setChnlCreditTransferRequest(chnlCTReq);
//		
//		exchange.getMessage().setHeader("hdr_request_list", rmw);	
//
//		ResponseMessageCollection rmc = new ResponseMessageCollection();
//		exchange.getMessage().setHeader("hdr_response_list", rmc);	
//		
//		// request message asli untuk keperluan cari settlement harus di unzip dulu
//		exchange.getMessage().setBody(creditTransfer.getFullRequestMessage());


	}

}
