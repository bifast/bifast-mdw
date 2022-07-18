package bifast.outbound.processor;

import java.time.Instant;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Channel;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.security.Users;
import bifast.outbound.service.UtilService;

@Component
public class InitRequestMessageWrapperProcessor implements Processor {
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Users user = (Users) authentication.getPrincipal();
		
		Channel channel = user.getChannel();
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		
		rmw.setChannelId(channel.getChannelId());
		rmw.setChannelType(channel.getChannelType());
		rmw.setChannelRequestStr(exchange.getMessage().getBody(String.class));
		
		rmw.setMerchantType(channel.getMerchantCode());

		String komiTrnsId = utilService.genKomiTrnsId();
		rmw.setKomiTrxId(komiTrnsId);
		rmw.setKomiStart(Instant.now());
		
		exchange.setProperty("prop_request_list", rmw);
		
		ResponseMessageCollection rmc = new ResponseMessageCollection();
		rmc.setCallStatus("SUCCESS");
		exchange.setProperty("prop_response_list" , rmc);

	}

}
