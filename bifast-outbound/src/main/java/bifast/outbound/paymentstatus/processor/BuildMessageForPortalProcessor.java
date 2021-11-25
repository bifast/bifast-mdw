package bifast.outbound.paymentstatus.processor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.config.Config;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.notification.pojo.LogDataPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

@Component
public class BuildMessageForPortalProcessor implements Processor {
	@Autowired private Config config;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list",RequestMessageWrapper.class );
//		ChannelResponseWrapper responseWr = exchange.getMessage().getBody(ChannelResponseWrapper.class);
		ResponseMessageCollection respColl = exchange.getMessage().getHeader("hdr_response_list",ResponseMessageCollection.class );
		UndefinedCTPojo psReq = (UndefinedCTPojo) rmw.getChannelRequest();

		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		logMsg.setCodelog("PymtSts");

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
