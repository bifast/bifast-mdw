package bifast.outbound.processor;

import java.time.Duration;
import java.time.Instant;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.service.CallRouteService;

@Component
public class PreCihubRequestProc implements Processor {
	@Autowired private CallRouteService routeService;
	@Autowired private Config param;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		rmw.setCihubEncriptedRequest(routeService.encrypt_body(exchange));
		
		String msgName = exchange.getMessage().getHeader("cihubMsgName", String.class);
		if (null==msgName) {
			msgName = rmw.getMsgName();
		}

		if (!(msgName.equals("PSReq")))
			rmw.setCihubStart(Instant.now());

		long sla = 0;
		long timeElapsed = Duration.between(rmw.getKomiStart(), Instant.now()).toMillis();

		if ((msgName.equals("CTReq")) || (msgName.equals("PrxRegn")))
			sla = param.getSlaChannelTrns();
		else if (msgName.equals("PSReq")) {
			sla = param.getSlaPymtStatus();
			timeElapsed = 0;
		}
		else 
			sla = param.getSlaChannelEnqr();
		

		String sisa = Long.toString(sla - timeElapsed);

		exchange.setProperty("prop_remain_time", sisa);
	}

}
