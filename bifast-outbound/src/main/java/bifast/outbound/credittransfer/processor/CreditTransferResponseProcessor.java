package bifast.outbound.credittransfer.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.StatusReason;
import bifast.outbound.pojo.ChnlFailureResponsePojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
import bifast.outbound.pojo.chnlresponse.ChannelResponseWrapper;
import bifast.outbound.pojo.chnlresponse.ChnlCreditTransferResponsePojo;
import bifast.outbound.pojo.flat.FlatPacs002Pojo;
import bifast.outbound.repository.StatusReasonRepository;

@Component
public class CreditTransferResponseProcessor implements Processor {

	DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

    @Autowired
    private StatusReasonRepository statusReasonRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ChannelResponseWrapper chnlResponseWr = new ChannelResponseWrapper();
		chnlResponseWr.setResponseCode("U000");
		chnlResponseWr.setDate(LocalDateTime.now().format(dateformatter));
		chnlResponseWr.setTime(LocalDateTime.now().format(timeformatter));
		chnlResponseWr.setContent(new ArrayList<>());
		
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlCreditTransferRequestPojo chnRequest = rmw.getChnlCreditTransferRequest();

		ChnlCreditTransferResponsePojo chnResponse = new ChnlCreditTransferResponsePojo();
		chnResponse.setOrignReffId(chnRequest.getChannelRefId());
		chnResponse.setAccountNumber(chnRequest.getCrdtAccountNo());

		Object objResponse = exchange.getMessage().getBody(Object.class);
		
		if (objResponse.getClass().getSimpleName().equals("ChnlFailureResponsePojo")) {
			ChnlFailureResponsePojo fault = (ChnlFailureResponsePojo)objResponse;
			
			chnlResponseWr.setResponseCode("KSTS");
			chnlResponseWr.setReasonCode(fault.getReasonCode());
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById(fault.getReasonCode());
			if (oStatusReason.isPresent())
				chnlResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				chnlResponseWr.setResponseMessage("General Error");
		}
		
		else if (objResponse.getClass().getSimpleName().equals("FlatAdmi002Pojo")) {

			chnlResponseWr.setResponseCode("RJCT");
			chnlResponseWr.setReasonCode("U215");
			Optional<StatusReason> oStatusReason = statusReasonRepo.findById("U215");
			if (oStatusReason.isPresent())
				chnlResponseWr.setReasonMessage(oStatusReason.get().getDescription());
			else
				chnlResponseWr.setResponseMessage("General Error");

		}

		else {
			
			FlatPacs002Pojo biResp = (FlatPacs002Pojo)objResponse;

			chnlResponseWr.setResponseCode(biResp.getTransactionStatus());
			chnlResponseWr.setReasonCode(biResp.getReasonCode());
			
			Optional<StatusReason> optStatusReason = statusReasonRepo.findById(chnlResponseWr.getReasonCode());
			if (optStatusReason.isPresent()) {
				String desc = optStatusReason.get().getDescription();
				chnlResponseWr.setReasonMessage(desc);
			}	

			// from CI-HUB response						
			if (null != biResp.getCdtrNm())
				chnResponse.setCreditorName(biResp.getCdtrNm());
		}

		chnlResponseWr.getContent().add(chnResponse);
		exchange.getIn().setBody(chnlResponseWr);

	}
}
