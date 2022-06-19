package bifast.outbound.processor;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.ChannelTransactionRepository;

@Component
public class SaveChannelTransactionProcessor implements Processor{
	@Autowired
	private ChannelTransactionRepository channelTransactionRepo;
	
//	private static Logger logger = LoggerFactory.getLogger(SaveChannelTransactionProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
		ResponseMessageCollection respColl = exchange.getProperty("prop_response_list",ResponseMessageCollection.class );
		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);

		Optional<ChannelTransaction> optChannel = channelTransactionRepo.findById(rmw.getKomiTrxId());
		ChannelTransaction chnlTrns = optChannel.get();
		
//		FaultPojo fault = null;
		if (responseWr.getReasonCode().equals("K000")) {
			chnlTrns.setCallStatus("TIMEOUT");
			chnlTrns.setResponseCode(responseWr.getResponseCode());
			chnlTrns.setErrorMsg("(" + responseWr.getReasonCode() + ") " + responseWr.getReasonMessage());
		}
		
		else if (!(responseWr.getResponseCode().equals("KSTS"))) {
//		if (respColl.getCallStatus().equals("SUCCESS")) {
//		if (null == respColl.getFault()) {
			chnlTrns.setCallStatus("SUCCESS");
			chnlTrns.setResponseCode(responseWr.getResponseCode());
		}
		
		else {
//			fault = respColl.getFault();
//
//			int errLength = fault.getErrorMessage().length();
//			if (errLength>250)
//				errLength = 250;
//			String errMsg = fault.getErrorMessage().substring(0, errLength);
//			chnlTrns.setCallStatus(fault.getCallStatus());
//			chnlTrns.setErrorMsg(errMsg);
//			chnlTrns.setResponseCode(fault.getResponseCode());

			int errLength = respColl.getLastError().length();
			if (errLength>250)
				errLength = 250;

//			String errMsg = respColl.getLastError().substring(0, errLength);
			chnlTrns.setCallStatus(respColl.getCallStatus());
			chnlTrns.setErrorMsg("(" + responseWr.getReasonCode() + ") " + responseWr.getReasonMessage());
			chnlTrns.setResponseCode(respColl.getResponseCode());

		}
	
		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
		chnlTrns.setElapsedTime(timeElapsed);
	
		channelTransactionRepo.save(chnlTrns);

	}

}
