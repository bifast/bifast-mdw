package bifast.outbound.paymentstatus.processor;

import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.paymentstatus.pojo.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.ResponseMessageCollection;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class CheckSAFResultProcessor implements Processor {
	@Autowired private ChannelTransactionRepository channelRequestRepo;
	@Autowired private CreditTransferRepository creditTransferRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlPaymentStatusRequestPojo chnReq = rmw.getChnlPaymentStatusRequest();
		
//		List<ChannelTransaction> lCT = channelRequestRepo.findByChannelIdAndChannelRefId(rmw.getChannelId(), chnReq.getOrgnlRefId());	
		List<ChannelTransaction> lCT = channelRequestRepo.findByChannelRefId(chnReq.getOrgnlRefId());	
		ChannelTransaction channelTrns = null;
		CreditTransfer crdtTrnsf = null;
		if (lCT.size()>0) {
			channelTrns = lCT.get(0);
			Optional<CreditTransfer> oCreditTransfer = creditTransferRepo.findByKomiTrnsId(lCT.get(0).getKomiTrnsId());
			if (oCreditTransfer.isPresent()) {
				crdtTrnsf = oCreditTransfer.get();
			}
		}

		ResponseMessageCollection rmc = exchange.getMessage().getHeader("hdr_response_list", ResponseMessageCollection.class);

		if (null != crdtTrnsf) {
			rmc.setCallStatus(channelTrns.getCallStatus());
			rmc.setResponseCode(crdtTrnsf.getResponseCode());
			rmc.setReasonCode(crdtTrnsf.getReasonCode());
			exchange.getMessage().setHeader("hdr_response_list", rmc);
			exchange.getMessage().setBody(channelTrns.getTextMessage());
		}
		else {
			rmc.setCallStatus("ERROR");
			rmc.setResponseCode("OTHR");
			rmc.setReasonCode("U106");
			exchange.getMessage().setHeader("hdr_response_list", rmc);
		}
				
	}

}
