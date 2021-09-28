package bifast.outbound.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.paymentstatus.PaymentStatusRequestProcessor;
import bifast.outbound.paymentstatus.PaymentStatusResponseProcessor;
import bifast.outbound.paymentstatus.StorePaymentStatusProcessor;

@Component
public class SimpleRoute extends RouteBuilder {

	@Autowired
	private PaymentStatusRequestProcessor paymentStatusRequestProcessor;
	@Autowired
	private PaymentStatusResponseProcessor paymentStatusResponseProcessor;


	@Override
	public void configure() throws Exception {


		from("direct:ps4chnl").routeId("komi.ps.chnl")
			.setHeader("ps_channelrequest", simple("${body}"))

			.process(paymentStatusRequestProcessor)
			.setHeader("ps_objreq_bi", simple("${body}"))
	
			.to("direct:call-cihub")
				
			.process(paymentStatusResponseProcessor)

			.removeHeaders("ps*")
			
		;

		from("direct:ps").routeId("komi.ps")
			.setHeader("ps_channelrequest", simple("${body}"))
			
			.process(paymentStatusRequestProcessor)
			.setHeader("ps_objreq_bi", simple("${body}"))
	
			.to("direct:call-cihub")
				

			.removeHeaders("ps*")
		;
		
	}

}
