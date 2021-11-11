package bifast.outbound.paymentstatus.processor;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.paymentstatus.UndefinedCTPojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;

@Component
public class PaymentStatusResponseProcessor implements Processor {
	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    
	@Override
	public void process(Exchange exchange) throws Exception {

		Object objResponse = exchange.getMessage().getBody(Object.class);
		UndefinedCTPojo psReq = exchange.getMessage().getHeader("ps_request", UndefinedCTPojo.class);
		
		if (objResponse.getClass().getSimpleName().equals("FaultPojo")) {
			//TODO subject of notification
			
			FaultPojo fault = (FaultPojo) objResponse;
			psReq.setPsStatus("UNDEFINED");
			psReq.setResponseCode(fault.getResponseCode());
			psReq.setReasonCode(fault.getReasonCode());
			
		}

			
		else if (objResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {
			//TODO subject of notification
			psReq.setPsStatus("UNDEFINED");
			psReq.setResponseCode("RJCT");
			psReq.setReasonCode("U215");

		}
		
		else {
			
			FlatPacs002Pojo biResp = (FlatPacs002Pojo)objResponse;

			if (biResp.getReasonCode().equals("U900")) {
				psReq.setPsStatus("UNDEFINED");
				psReq.setResponseCode("KSTS");
				psReq.setReasonCode("K000");
			}
			
			else if (biResp.getTransactionStatus().equals("ACTC")) {
				psReq.setPsStatus("ACCEPTED");
				psReq.setResponseCode("ACTC");
				psReq.setReasonCode(biResp.getReasonCode());
			}
			
			else if ((biResp.getTransactionStatus().equals("OTHR") && 
					 (biResp.getReasonCode().equals("U106"))) ) {
				psReq.setPsStatus("NOTFOUND");
				psReq.setResponseCode("OTHR");
				psReq.setReasonCode("U106");

			}

			else {
				psReq.setPsStatus("REJECTED");
				psReq.setResponseCode(biResp.getTransactionStatus());
				psReq.setReasonCode(biResp.getReasonCode());
			}

		}
		
		exchange.getMessage().setBody(psReq);

	}
	
}