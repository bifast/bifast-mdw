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
		
		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();

		if ((rmw.getMsgName().equals("CTReq")) || (rmw.getMsgName().equals("PrxRegn")))
			sla = param.getSlaChannelTrns();
		else 
			sla = param.getSlaChannelEnqr();
		
		if (rmw.getMsgName().equals("PaymentStsSAF"))
			timeElapsed = 0;

		String sisa = Long.toString(sla - timeElapsed);

		exchange.getMessage().setHeader("hdr_remain_time", sisa);
	}

}
