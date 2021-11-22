package bifast.inbound.corebank;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PendingCorebankRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("scheduler://foo?delay=5000").routeId("komi.schd.pendingrvsl").autoStartup(false)
			.log("satu")
			
			.to("controlbus:route?routeId=komi.schd.pendingrvsl&action=stop&async=true")

//			.process(new Processor() {
//				@Override
//				public void process(Exchange exchange) throws Exception {					
//			    	RouteController contextCtl = exchange.getContext().getRouteController();
//					new Thread(new Runnable() {
//					      public void run() {
//					        try {
//					        	contextCtl.stopRoute("komi.schd.pendingrvsl");
//					        } catch (Exception e) {
//					          throw new RuntimeException(e);
//					        }
//					      }
//				    }).start();
//				}
//			})

			
			;

	}

}
