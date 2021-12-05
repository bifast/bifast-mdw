package bifast.outbound.route;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.config.Config;
import bifast.outbound.model.Parameter;
import bifast.outbound.repository.ParameterRepository;

@Component
public class ConfigRoute extends RouteBuilder {
	@Autowired ParameterRepository paramRepo;
	
	@Override
	public void configure() throws Exception {
		
		from("timer://runOnce?repeatCount=1&delay=2000").routeId("configRoute")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					Config config = new Config();
					
					Optional<Parameter> oSlaChannelTrns = paramRepo.findByParamName("KOMI_CORE_SLA_CT");
					config.setSlaChannelTrns(Integer.valueOf(oSlaChannelTrns.get().getParamValue()));
	
					Optional<Parameter> oSlaChannelEnqr =	paramRepo.findByParamName("KOMI_CORE_SLA_AE");
					config.setSlaChannelEnqr(Integer.valueOf(oSlaChannelEnqr.get().getParamValue()));
	
					Optional<Parameter> oMaxRetry =	paramRepo.findByParamName("KOMI_CORE_MAX_RETRY_BEFORE_NOTIF");
					config.setMaxRetryBeforeNotif(Integer.valueOf(oMaxRetry.get().getParamValue()));

					exchange.getMessage().setHeader("hdr_config", config);
				}
			})
		;

	}

}
