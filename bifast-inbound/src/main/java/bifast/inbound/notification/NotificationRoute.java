package bifast.inbound.notification;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.service.JacksonDataFormatService;

@Component
public class NotificationRoute extends RouteBuilder{
	@Autowired private EventNotificationProcessor eventNotifProcessor;
	@Autowired private ProxyNotifProcessor proxyNotifProcessor;
	@Autowired private JacksonDataFormatService jdfService;
	
	@Override
	public void configure() throws Exception {

		from("direct:eventnotif").routeId("komi.eventnotif")
			.log("Ada notifikasi")
			.process(eventNotifProcessor)
			
			//TODO info notification
		;
		
		
		from("direct:proxynotif").routeId("komi.proxynotif")
			.log("Proxy notification")

			.process(proxyNotifProcessor)
			
			//TODO notifikasi ke customer
			.setHeader("HttpMethod", constant("POST"))
			.to("http://{{komi.url.custnotif}}?"
					+ "socketTimeout=5000&" 
					+ "bridgeEndpoint=true")

			.log(LoggingLevel.DEBUG, "komi.corebank", "[${header.hdr_frBIobj.appHdr.msgDefIdr}:${header.hdr_frBIobj.appHdr.bizMsgIdr}] CB Response: ${body}")

		;

		
	}

}
