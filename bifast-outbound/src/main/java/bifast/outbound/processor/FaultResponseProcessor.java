package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.FaultClass;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.repository.FaultClassRepository;

@Component
public class FaultResponseProcessor implements Processor {

	@Autowired 
	private FaultClassRepository faultClassRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

		String chRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
		reject.setTransactionType(chRefId);
		
		String hdrLocation = exchange.getMessage().getHeader("hdr_errorlocation", String.class);
		if (null==hdrLocation)
			reject.setLocation("komi-outbound");
		else
			reject.setLocation(hdrLocation);

		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		if (null == e) {
			reject.setReason("General error");
			reject.setDescription("Check ke error log");			
		}
		
		else {

			FaultClass fault = faultClassRepo.findByExceptionClass(e.getClass().getName()).orElse(new FaultClass());
		
			if (null==fault.getId())
				reject.setReason(e.getClass().getSimpleName());
			else
				reject.setReason(fault.getReason());
					
			reject.setDescription(e.getMessage());
		}
			
		ChannelResponseWrapper channelResponse = new ChannelResponseWrapper();
		channelResponse.setFaultResponse(reject);
		
		exchange.getMessage().setBody(channelResponse, ChannelResponseWrapper.class);
	}

}
