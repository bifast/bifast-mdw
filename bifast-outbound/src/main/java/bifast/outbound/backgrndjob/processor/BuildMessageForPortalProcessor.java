package bifast.outbound.backgrndjob.processor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.notification.pojo.LogDataPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class BuildMessageForPortalProcessor implements Processor {
	@Autowired private StatusReasonRepository statusReasonRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
//		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);
		UndefinedCTPojo psReq = (UndefinedCTPojo) rmw.getChannelRequest();

		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		logMsg.setCodelog("PS");

		data.setStatus_code("SUCCESS");
		
		data.setResponse_code(psReq.getResponseCode());
		data.setReason_code(psReq.getReasonCode());
		
		Optional<StatusReason> oStatusReason = statusReasonRepo.findById(psReq.getReasonCode());
		String reasonMsg = "";
		if (oStatusReason.isEmpty())
			reasonMsg = "General Error";
		else
			reasonMsg = oStatusReason.get().getDescription();
		
		data.setReason_message(reasonMsg);
		LocalDateTime.now();
		data.setBifast_trx_no(psReq.getReqBizmsgid());
		data.setKomi_unique_id(psReq.getChannelNoref());
	
		Duration dur = Duration.between(psReq.getOrgnlDateTime().toLocalTime(), LocalDateTime.now().toLocalTime());
		long timeElapsed = dur.toMillis();
		data.setTrx_duration(String.valueOf(timeElapsed));

		logMsg.setData(data);
		exchange.getMessage().setBody(logMsg);
		
	}

}
