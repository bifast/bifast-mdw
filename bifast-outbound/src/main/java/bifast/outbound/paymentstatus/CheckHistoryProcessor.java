package bifast.outbound.paymentstatus;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.model.OutboundMessage;
import bifast.library.model.Settlement;
import bifast.library.repository.OutboundMessageRepository;
import bifast.library.repository.SettlementRepository;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;

@Component
public class CheckHistoryProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMessageRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

//		ChnlPaymentStatusRequest reqMesg = exchange.getMessage().getHeader("hdr_channelRequest", ChnlPaymentStatusRequest.class);
		ChnlPaymentStatusRequest reqMesg = exchange.getIn().getHeader("hdr_channelRequest",ChnlPaymentStatusRequest.class);
System.out.println("dalam history");

		ChnlPaymentStatusResponse respMesg = new ChnlPaymentStatusResponse();
		System.out.println("yang diminta: " + reqMesg.getRequestReffId());
		respMesg.setRequestRefId(reqMesg.getRequestReffId());
		
		// check dulu di ct outbound
		List<OutboundMessage> out = outboundMessageRepo.findAllByInternalReffId(reqMesg.getRequestReffId());
		System.out.println("jumlah: " + out.size());
		if (out.size() >0 ) {
			if (null==out.get(0).getRespStatus())
				respMesg.setStatus("PENDING");
			else
				respMesg.setStatus(out.get(0).getRespStatus());
			
				exchange.getMessage().setHeader("hdr_resp_bi", out.get(0).getFullResponseMsg());
		}
		
		else {
			respMesg.setStatus("Kosong");
		}

		exchange.getMessage().setBody(respMesg);

	}
	

}
 