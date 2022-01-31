package bifast.outbound.notification;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import bifast.outbound.notification.pojo.CustomerNotificationPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.service.JacksonDataFormatService;

@Component
public class NotificationRoute extends RouteBuilder {
	@Autowired private BuildLogMessageForPortalProcessor buildLogMessage;
	@Autowired private JacksonDataFormatService jdfService;


	@Override
	public void configure() throws Exception {

		JacksonDataFormat portalLogJDF = jdfService.wrapRoot(PortalApiPojo.class);
		JacksonDataFormat custNotifJDF = jdfService.wrapRoot(CustomerNotificationPojo.class);
		
		from("seda:logportal?concurrentConsumers=10").routeId("komi.notif.portal")
			.process(buildLogMessage)
			.marshal(portalLogJDF)

			.removeHeaders("*")
		    
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
		            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
				}
			})
			
			.doTry()

				.to("rest:post:?host={{komi.url.portalapi}}")
//				.to("{{komi.url.portalapi}}?"
//						+ "socketTimeout=1000&" 
//						+ "bridgeEndpoint=true")

			.endDoTry()
	    	.doCatch(Exception.class)
	    		.log(LoggingLevel.ERROR, "komi.notif.portal", "Error Log-notif ${body}")
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
