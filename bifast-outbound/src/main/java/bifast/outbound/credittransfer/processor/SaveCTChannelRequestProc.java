package bifast.outbound.credittransfer.processor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.ChannelTransactionRepository;

@Component
public class SaveCTChannelRequestProc implements Processor {
	
	@Autowired private ChannelTransactionRepository channelTransactionRepo;

    private static Logger logger = LoggerFactory.getLogger(SaveCTChannelRequestProc.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		// save channel transaction
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );

		logger.debug("[CTReq:" + rmw.getRequestId() + "] Save table channel_transaction.");

		ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);

		Optional<ChannelTransaction> optChannel = channelTransactionRepo.findById(rmw.getKomiTrxId());
		ChannelTransaction chnlTrns = optChannel.get();
				
		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
		chnlTrns.setElapsedTime(timeElapsed);

		ChnlCreditTransferRequestPojo ctReq = rmw.getChnlCreditTransferRequest();
		chnlTrns.setAmount(new BigDecimal(ctReq.getAmount()));
		chnlTrns.setRecptBank(ctReq.getRecptBank());

		if (respColl.getCallStatus().equals("SUCCESS")) {
			chnlTrns.setCallStatus("SUCCESS");
			chnlTrns.setResponseCode(responseWr.getResponseCode());
		}
		else {
			chnlTrns.setCallStatus(respColl.getCallStatus());
			chnlTrns.setErrorMsg("(" + responseWr.getReasonCode() + ") " + responseWr.getReasonMessage());
			chnlTrns.setResponseCode(responseWr.getResponseCode());
		}

		channelTransactionRepo.save(chnlTrns);
		
	}
}
