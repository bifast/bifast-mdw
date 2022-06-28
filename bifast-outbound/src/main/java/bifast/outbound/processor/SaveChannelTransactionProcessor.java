package bifast.outbound.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;

@Component
public class SaveChannelTransactionProcessor implements Processor{
	@Autowired
	private ChannelTransactionRepository channelTransactionRepo;
	
	private static Logger logger = LoggerFactory.getLogger(SaveChannelTransactionProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
//		ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);

		Optional<ChannelTransaction> optChannel = channelTransactionRepo.findById(rmw.getKomiTrxId());
		ChannelTransaction chnlTrns = optChannel.get();
		
		logger.debug("Save channelTransaction dgn response " + responseWr.getResponseCode());
		
		if (responseWr.getReasonCode().equals("K000")) 
			chnlTrns.setCallStatus("TIMEOUT");
		else
			chnlTrns.setCallStatus("SUCCESS");
			
		chnlTrns.setResponseCode(responseWr.getResponseCode());

		if (!(responseWr.getResponseCode().equals("ACTC"))) 
			chnlTrns.setErrorMsg("(" + responseWr.getReasonCode() + ") " + responseWr.getReasonMessage());

		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
		chnlTrns.setElapsedTime(timeElapsed);
	
		channelTransactionRepo.save(chnlTrns);
	}
}
