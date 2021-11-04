package bifast.outbound.paymentstatus.processor;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import bifast.outbound.model.Channel;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.repository.ChannelRepository;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class ProcessQuerySAFProcessor implements Processor{
//    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	@Autowired
	private ChannelRepository channelRepo;
	@Autowired
	private ChannelTransactionRepository channelTrnsRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;

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
		
		ChannelTransaction chnlTrns = channelTrnsRepo.findByKomiTrnsId(ct.getKomiTrnsId()).orElse(new ChannelTransaction());
		Channel channel = channelRepo.findById(chnlTrns.getChannelId()).orElse(new Channel());
		CreditTransfer creditTransfer = creditTransferRepo.findByKomiTrnsId(ct.getKomiTrnsId()).orElse(new CreditTransfer());
		
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		
		rmw.setChannelRequest(ct);
		rmw.setMsgName("PaymentStsSAF");
		
		rmw.setRequestId(chnlTrns.getChannelRefId());
		rmw.setChannelId(channel.getChannelId());
		rmw.setChannelType(channel.getChannelType());

		rmw.setMerchantType(channel.getMerchantCode());
		rmw.setKomiTrxId(chnlTrns.getKomiTrnsId());
		rmw.setKomiStart(chnlTrns.getRequestTime().atZone(ZoneOffset.systemDefault()).toInstant());
		
		ObjectMapper map = new ObjectMapper();
		map.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		
		String chnlRequestText = chnlTrns.getTextMessage();
		
		ChnlCreditTransferRequestPojo chnlCTReq = map.readValue(chnlRequestText, ChnlCreditTransferRequestPojo.class);
		chnlCTReq.setDateTime(chnlTrns.getRequestTime());
		
		rmw.setChnlCreditTransferRequest(chnlCTReq);
		
		exchange.getMessage().setHeader("hdr_request_list", rmw);	

		ResponseMessageCollection rmc = new ResponseMessageCollection();
		exchange.getMessage().setHeader("hdr_response_list", rmc);	
		
		// request message asli untuk keperluan cari settlement harus di unzip dulu
		exchange.getMessage().setBody(creditTransfer.getFullRequestMessage());

	}

}
