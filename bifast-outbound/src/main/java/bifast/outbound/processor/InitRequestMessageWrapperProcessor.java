package bifast.outbound.processor;

import java.time.Instant;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.Channel;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelRepository;
import bifast.outbound.service.UtilService;

@Component
public class InitRequestMessageWrapperProcessor implements Processor {
	@Autowired
	private ChannelRepository channelRepo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {
		String clientId = exchange.getMessage().getHeader("clientId", String.class);
		String channelType = channelRepo.findById(clientId).orElse(new Channel()).getChannelType();
		String komiTrnsId = utilService.genKomiTrnsId();
		RequestMessageWrapper rmw = new RequestMessageWrapper();
		rmw.setChannelId(clientId);
		rmw.setChannelType(channelType);
		rmw.setKomiTrxId(komiTrnsId);
		rmw.setRequestTime(LocalDateTime.now());
		rmw.setStart(Instant.now());
		
		exchange.getMessage().setHeader("hdr_request_list", rmw);
	}

}
