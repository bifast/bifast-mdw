package bifast.outbound.paymentstatus.processor;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.notification.pojo.LogDataPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.paymentstatus.pojo.UndefinedCTPojo;
import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class BuildMessageForPortalProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
//		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);
		UndefinedCTPojo psReq = (UndefinedCTPojo) rmw.getChannelRequest();

		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		logMsg.setCodelog("PS");

		data.setStatus_code("SUCCESS");
		
		data.setResponse_code(psReq.getResponseCode());
		data.setReason_code(psReq.getReasonCode());
//		data.setReason_message(responseWr.getReasonMessage());
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
