package bifast.inbound.corebank;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ServiceStatus;
import org.apache.camel.spi.RouteController;
import org.springframework.stereotype.Component;

@Component
public class PendingCorebankProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		CamelContext ctx = exchange.getContext();
		RouteController routeCtl = ctx.getRouteController();

		ServiceStatus serviceSts = routeCtl.getRouteStatus("komi.schd.pendingrvsl");
		
		if (serviceSts.isStarted()) 
			routeCtl.stopRoute("komi.schd.pendingrvsl");
		else if (serviceSts.isStopped())
			routeCtl.startRoute("komi.schd.pendingrvsl");
	}
}
