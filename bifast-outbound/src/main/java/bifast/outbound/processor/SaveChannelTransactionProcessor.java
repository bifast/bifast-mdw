package bifast.outbound.processor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.repository.ChannelTransactionRepository;

@Component
public class SaveChannelTransactionProcessor implements Processor{
	@Autowired
	private ChannelTransactionRepository channelTransactionRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );

		Optional<ChannelTransaction> optChannel = channelTransactionRepo.findByKomiTrnsId(rmw.getKomiTrxId());
		
		// jika belum pernah di save
		if (optChannel.isEmpty()) {
			ChannelTransaction chnlTrns = new ChannelTransaction();
			chnlTrns.setKomiTrnsId(rmw.getKomiTrxId());
			chnlTrns.setChannelId(rmw.getChannelId());
			chnlTrns.setRequestTime(LocalDateTime.now());
			chnlTrns.setMsgName(rmw.getMsgName());
			
			channelTransactionRepo.save(chnlTrns);
		
		}

		else if (rmw.getMsgName().equals("AEReq")) {
			ChannelTransaction chnlTrns = optChannel.get();
			
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(rmw.getStart(), finish).toMillis();
			chnlTrns.setElapsedTime(timeElapsed);

			ChnlAccountEnquiryRequestPojo aeReq = rmw.getChnlAccountEnquiryRequest();

			chnlTrns.setAmount(new BigDecimal(aeReq.getAmount()));
			chnlTrns.setChannelRefId(aeReq.getChannelRefId());
			chnlTrns.setRecptBank(aeReq.getChannelRefId());
			chnlTrns.setStatus("SUCCESS");
			channelTransactionRepo.save(chnlTrns);
			
		}

		else if (rmw.getMsgName().equals("CTReq")) {
			ChannelTransaction chnlTrns = optChannel.get();

			Instant finish = Instant.now();
			long timeElapsed = Duration.between(rmw.getStart(), finish).toMillis();
			chnlTrns.setElapsedTime(timeElapsed);

			ChnlCreditTransferRequestPojo ctReq = rmw.getChnlCreditTransferRequest();
			chnlTrns.setAmount(ctReq.getAmount());
			chnlTrns.setChannelRefId(ctReq.getChannelRefId());
			chnlTrns.setRecptBank(ctReq.getRecptBank());

			chnlTrns.setStatus("SUCCESS");
			channelTransactionRepo.save(chnlTrns);

		}
		
		else if (rmw.getMsgName().equals("PSReq")) {
			ChannelTransaction chnlTrns = optChannel.get();

			Instant finish = Instant.now();
			long timeElapsed = Duration.between(rmw.getStart(), finish).toMillis();
			chnlTrns.setElapsedTime(timeElapsed);

			ChnlCreditTransferRequestPojo ctReq = rmw.getChnlCreditTransferRequest();
//			chnlTrns.setAmount(ctReq.getAmount());
			chnlTrns.setChannelRefId(ctReq.getChannelRefId());
//			chnlTrns.setRecptBank(ctReq.getRecptBank());

			chnlTrns.setStatus("SUCCESS");
			channelTransactionRepo.save(chnlTrns);

		}

	
	}

}
