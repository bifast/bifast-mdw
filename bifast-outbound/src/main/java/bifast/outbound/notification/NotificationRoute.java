package bifast.outbound.notification;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.notification.pojo.CustomerNotificationPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class NotificationRoute extends RouteBuilder {
	@Autowired private JacksonDataFormatService jdfService;
	@Autowired private BuildLogMessageForPortalProcessor buildLogMessage;


	@Override
	public void configure() throws Exception {

		JacksonDataFormat portalLogJDF = jdfService.wrapRoot(PortalApiPojo.class);
		JacksonDataFormat custNotifJDF = jdfService.wrapRoot(CustomerNotificationPojo.class);
		
		from("seda:logportal").routeId("komi.notif.portal")
			.process(buildLogMessage)
			.marshal(portalLogJDF)
//			.log(LoggingLevel.DEBUG, "komi.notif.portal", "Log-notif ${body}")

		    .removeHeaders("*")
		    
			.doTry()
				.to("rest:post:?host={{komi.url.portalapi}}")
			.endDoTry()
	    	.doCatch(Exception.class)
	    		.log(LoggingLevel.DEBUG, "komi.notif.portal", "Log-notif ${body}")
    			.log(LoggingLevel.ERROR, "Error send notification ke Portal")
//	    		.log(LoggingLevel.ERROR, "${exception.stacktrace}")
			.end()

		;

		from("seda:notifcustomer").routeId("komi.notif.customer")
			.marshal(custNotifJDF)
			.log(LoggingLevel.DEBUG, "komi.notif.customer", "send cust-notif ${body}")
		    .removeHeaders("CamelHttp*")
//			.to("rest:post:custnotif?host={{komi.url.custnotif}}")
//			.log("setelah notif customer")

		;

		
	}

}
