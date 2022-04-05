package bifast.outbound.backgrndjob.processor;

import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.backgrndjob.dto.UndefinedCTPojo;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;

@Component
public class PaymentStatusResponseProcessor implements Processor {
	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

//    private static Logger logger = LoggerFactory.getLogger(PaymentStatusResponseProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objResponse = exchange.getMessage().getBody(Object.class);
		UndefinedCTPojo psReq = exchange.getProperty("pr_psrequest", UndefinedCTPojo.class);
		
		if (objResponse.getClass().getSimpleName().equals("FaultPojo")) {
			//TODO subject of notification
			
			FaultPojo fault = (FaultPojo) objResponse;
			psReq.setPsStatus("UNDEFINED");
			psReq.setResponseCode(fault.getResponseCode());
			psReq.setReasonCode(fault.getReasonCode());
			
		}

		else {
			
			FlatPacs002Pojo biResp = (FlatPacs002Pojo)objResponse;

			if (biResp.getReasonCode().equals("U900")) {
				psReq.setPsStatus("UNDEFINED");
				psReq.setResponseCode("KSTS");
				psReq.setReasonCode("K000");
			}

			else if (biResp.getReasonCode().equals("U000")) {
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
		exchange.setProperty("pr_psrequest", psReq);

	}
	
}
