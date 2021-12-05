package bifast.outbound.processor;

import java.time.Duration;
import java.time.Instant;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class SetRemainTimeProcessor implements Processor {
	@Autowired
	private Config param;

	@Override
	public void process(Exchange exchange) throws Exception {
		long sla = 0;
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		if (rmw.getMsgName().equals("AEReq"))
			sla = param.getSlaChannelEnqr();
		
		else if (rmw.getMsgName().equals("CTReq"))
			sla = param.getSlaChannelTrns();
		else
			sla = 15000;
			

		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();
		
		String sisa = Long.toString(sla - timeElapsed);
		exchange.getMessage().setHeader("hdr_remain_time", sisa);
	}

}
