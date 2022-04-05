package bifast.outbound.proxyregistration.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Channel;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.security.Users;

@Component
public class PrxRegValidation implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users user = (Users) authentication.getPrincipal();
		
		Channel channel = user.getChannel();
		
		if (channel.getChannelId().equals("MB")) { 
			FaultPojo fault = new FaultPojo();
			fault.setResponseCode("KSTS");
			fault.setReasonCode("K008");
			exchange.getMessage().setBody(fault);
		}
			
	}

}
