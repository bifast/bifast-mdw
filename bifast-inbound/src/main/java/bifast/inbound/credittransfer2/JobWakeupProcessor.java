package bifast.inbound.credittransfer2;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ServiceStatus;
import org.apache.camel.spi.RouteController;
import org.springframework.stereotype.Component;

@Component
public class JobWakeupProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
			RouteController routeCtl = exchange.getContext().getRouteController();
			ServiceStatus serviceSts = routeCtl.getRouteStatus("komi.ct.saf");
			
			if (serviceSts.isStopped())
				routeCtl.startRoute("komi.ct.saf");
	}

}
