package bifast.outbound.proxyregistration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.DomainCode;
import bifast.library.repository.DomainCodeRepository;

@Component
public class ValidatePrxyInputProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		ChannelProxyRegistrationReq chnRequest = exchange.getIn().getBody(ChannelProxyRegistrationReq.class);

		System.out.println(chnRequest.getChannel());
		// periksa channel
		exchange.getMessage().setHeader("hdr_errorlocation", "ProxyRequest/channel");
		DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", chnRequest.getChannel()).orElseThrow();
		chnRequest.setChannel(channelDC.getKey());

	}

}
