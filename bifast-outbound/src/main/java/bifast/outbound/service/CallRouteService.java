package bifast.outbound.service;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.apache.camel.spi.RouteController;
import org.springframework.stereotype.Service;

import bifast.library.iso20022.custom.BusinessMessage;

@Service
public class CallRouteService {

//	public void sendBody (Exchange exchange, String route, Object body) {
//		CamelContext ctx = exchange.getContext();
//		@SuppressWarnings("resource")
//		ProducerTemplate template = new DefaultProducerTemplate(ctx);
//		template.start();
//		template.sendBody(route, body);
//		template.stop();
//	}
	
//	public String encryptBody (Exchange exchange) {
//		String strBody = exchange.getMessage().getBody(String.class);
//		CamelContext ctx = exchange.getContext();
//		@SuppressWarnings("resource")
//		ProducerTemplate template = new DefaultProducerTemplate(ctx);
//		template.start();
//		template.sendBody("direct:encrypbody", strBody);
//		String encrBody = exchange.getMessage().getBody(String.class);
//		template.stop();
//		return encrBody;
//	}
	
	public BusinessMessage decrypt_unmarshal (Exchange exchange) {
		String strBody = exchange.getMessage().getBody(String.class);
		FluentProducerTemplate template = exchange.getContext().createFluentProducerTemplate();
		BusinessMessage decrBody = template.withBody(strBody).to("direct:decryp_unmarshal").request(BusinessMessage.class);
		return decrBody;
	}
	
	public void resumeJobRoute (Exchange exchange, String route) throws Exception {
		RouteController routeCtl = exchange.getContext().getRouteController();
		ServiceStatus serviceSts = routeCtl.getRouteStatus(route);
		
		if (serviceSts.isStopped())
			routeCtl.startRoute(route);
		
		else if (serviceSts.isSuspended())
			routeCtl.resumeRoute(route);
	}

}
