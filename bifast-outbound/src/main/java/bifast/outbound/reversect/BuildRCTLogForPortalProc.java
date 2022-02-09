package bifast.outbound.reversect;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.StatusReason;
import bifast.outbound.notification.pojo.LogDataPojo;
import bifast.outbound.notification.pojo.PortalApiPojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class BuildRCTLogForPortalProc implements Processor {
	@Autowired private StatusReasonRepository statusReasonRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list",RequestMessageWrapper.class );
		BusinessMessage biRequest = rmw.getCreditTransferRequest();

		String status = "";
		String response = "";
		String reason = "";
		
		Object oBiResponse = exchange.getMessage().getBody(Object.class);
		if (oBiResponse.getClass().getSimpleName().equals("FaultPojo")) {
			FaultPojo fault = (FaultPojo) oBiResponse;
			status = fault.getCallStatus();
			response = fault.getResponseCode();
			reason = fault.getReasonCode();
		}
		else if (oBiResponse.getClass().getSimpleName().equals("FlatPacs002Pojo")) {
			FlatPacs002Pojo flat = (FlatPacs002Pojo) oBiResponse;
			status = "SUCCESS";
			response = flat.getTransactionStatus();
			reason = flat.getReasonCode();
		}

		PortalApiPojo logMsg = new PortalApiPojo();
		LogDataPojo data = new LogDataPojo();

		logMsg.setCodelog("CT");

		data.setStatus_code(status);
		
		data.setResponse_code(response);
		data.setReason_code(reason);
		
		Optional<StatusReason> oStatusReason = statusReasonRepo.findById(reason);
		String reasonMsg = "";
		if (oStatusReason.isEmpty())
			reasonMsg = "General Error";
		else
			reasonMsg = oStatusReason.get().getDescription();
		
		data.setReason_message(reasonMsg);
		LocalDateTime.now();
		data.setBifast_trx_no(biRequest.getAppHdr().getBizMsgIdr());
		data.setKomi_unique_id(rmw.getKomiTrxId());
	
		Duration dur = Duration.between(rmw.getKomiStart(), Instant.now());
		long timeElapsed = dur.toMillis();
		data.setTrx_duration(String.valueOf(timeElapsed));

		logMsg.setData(data);
		exchange.getMessage().setBody(logMsg);
		
	}

}
