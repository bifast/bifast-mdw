package bifast.inbound.notification;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.accountenquiry.AEPortalLogProcessor;
import bifast.inbound.service.JacksonDataFormatService;

@Component
public class NotificationRoute extends RouteBuilder{
	@Autowired private AEPortalLogProcessor portalLogProcessor;
	@Autowired private EventNotificationProcessor eventNotifProcessor;
	@Autowired private JacksonDataFormatService jdfService;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat portalJdf = jdfService.wrapRoot(PortalApiPojo.class);
		
		from("direct:eventnotif").routeId("komi.eventnotif")
			.log("Ada notifikasi")
			.process(eventNotifProcessor)
			
			//TODO info notification
		;
		
		
		from("seda:portalnotif").routeId("komi.portalnotif")
			.log(LoggingLevel.DEBUG, "komi.portalnotif", "Portal notification")

			.process(portalLogProcessor)
			.marshal(portalJdf)
			.log(LoggingLevel.DEBUG, "komi.portalnotif", "Notif ke portal: ${body}")
			//TODO notifikasi ke customer
			.setHeader("HttpMethod", constant("POST"))
			.to("http://{{komi.url.portalapi}}?"
					+ "socketTimeout=5000&" 
					+ "bridgeEndpoint=true")

		;

		
	}

}
