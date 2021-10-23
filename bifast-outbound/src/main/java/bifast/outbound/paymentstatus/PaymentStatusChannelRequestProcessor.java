package bifast.outbound.paymentstatus;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlPaymentStatusRequestPojo;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;

@Component
public class PaymentStatusChannelRequestProcessor implements Processor {

	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private ChannelTransactionRepository channelTrnsRepo;

    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");

	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		ChnlPaymentStatusRequestPojo psReq = rmw.getChnlPaymentStatusRequest();

		List<ChannelTransaction> lCT = channelTrnsRepo.findByChannelIdAndChannelRefId(rmw.getChannelId(), 
																						psReq.getOrgnlRefId());
		
		if (lCT.size() > 0) {
			String komiTrnsId = lCT.get(0).getKomiTrnsId();
			Optional<CreditTransfer> oCrdtTrnsf = creditTransferRepo.findByKomiTrnsId(komiTrnsId);

			if (oCrdtTrnsf.isPresent()) {
				System.out.println("CT is present:");

				CreditTransfer ct = oCrdtTrnsf.get();
				
				exchange.getMessage().setBody(lCT.get(0).getTextMessage());
				
			}
				
		}

//		else {
//			channelResponseWr.setResponseCode("KSTS");
//			channelResponseWr.setReasonCode("K004");
//			StatusReason statusReason = statusReasonRepo.findById("K004").orElse(new StatusReason());
//			channelResponseWr.setReasonMessage(statusReason.getDescription());
//		}
//		
//		channelResponseWr.getContent().add(chnResponse);
//		exchange.getMessage().setBody(channelResponseWr);
//
	}

}
