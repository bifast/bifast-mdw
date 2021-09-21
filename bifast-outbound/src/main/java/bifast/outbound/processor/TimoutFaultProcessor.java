package bifast.outbound.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.ChnlFailureResponsePojo;

@Component
public class TimoutFaultProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		System.out.println("start TimeoutFaultProcessor");

		ChnlFailureResponsePojo reject = new ChnlFailureResponsePojo();

		String hdrLocation = exchange.getMessage().getHeader("hdr_errorlocation", String.class);
		
		if (null==hdrLocation)
			reject.setLocation("komi-outbound");
		else
			reject.setLocation(hdrLocation);

		reject.setReason("Timeout");
		reject.setDescription("Menunggu response service call melebihi waktu ditentukan");			

		ChannelResponseWrapper channelResponse = new ChannelResponseWrapper();
		channelResponse.setFaultResponse(reject);
		
		exchange.getMessage().setBody(channelResponse, ChannelResponseWrapper.class);
				
	}

}
