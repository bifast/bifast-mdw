package bifast.outbound.credittransfer.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferResponsePojo;
import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChannelResponseWrapper;
import bifast.outbound.pojo.FaultPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class DebitRejectResponseProc implements Processor {
	@Autowired private StatusReasonRepository statusReasonRepo;

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);		
		ChnlCreditTransferRequestPojo chnlReq = rmw.getChnlCreditTransferRequest();
		
		ChnlCreditTransferResponsePojo resp = new ChnlCreditTransferResponsePojo();
		resp.setOrignReffId(chnlReq.getChannelRefId());
		resp.setAccountNumber(chnlReq.getCrdtAccountNo());

		FaultPojo fault = exchange.getMessage().getBody(FaultPojo.class);
				
		ChannelResponseWrapper responseWr = new ChannelResponseWrapper();
		responseWr.setResponseCode(fault.getResponseCode());
		responseWr.setReasonCode(fault.getReasonCode());
		
		String reason = (statusReasonRepo.findById(fault.getReasonCode()).orElse(new StatusReason())).getDescription();
		
		responseWr.setReasonMessage(reason);
		responseWr.setDate(LocalDateTime.now().format(dateformatter));
		responseWr.setTime(LocalDateTime.now().format(timeformatter));
		responseWr.setResponses(new ArrayList<>());
		
		responseWr.getResponses().add(resp);

		exchange.getMessage().setBody(responseWr);
	}

}
