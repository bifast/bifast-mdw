package bifast.outbound.paymentstatus;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.PaymentStatus;
import bifast.outbound.repository.PaymentStatusRepository;
import bifast.outbound.service.UtilService;

@Component 
public class StorePaymentStatusProcessor implements Processor{

	@Autowired
	private PaymentStatusRepository paymentStatusRepo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		@SuppressWarnings("unchecked")
		List<MessageHistory> listHistory = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);

		long routeElapsed = utilService.getRouteElapsed(listHistory, "Inbound");

		PaymentStatus ps = new PaymentStatus();
		
		String chnlRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
		ps.setInternRefId(chnlRefId);

		Long chnlTrxId = exchange.getMessage().getHeader("hdr_chnlTable_id", Long.class);
		if (!(null == chnlTrxId))
			ps.setChnlTrxId(chnlTrxId);

		BusinessMessage psRequest = exchange.getMessage().getHeader("hdr_cihub_request", BusinessMessage.class);

		ps.setInternRefId(chnlRefId);
		ps.setBizMsgIdr(psRequest.getAppHdr().getBizMsgIdr());
		ps.setOrgnlEndToEndId(psRequest.getDocument().getFiToFIPmtStsReq().getTxInf().get(0).getOrgnlEndToEndId());

		String encrRequestMesg = exchange.getMessage().getHeader("cihubroute_encr_request", String.class);
		String encrResponseMesg = exchange.getMessage().getHeader("cihubroute_encr_response", String.class);

		ps.setRequestFullMessage(encrRequestMesg);
		if (!(null==encrResponseMesg))
			ps.setResponseFullMessage(encrResponseMesg);


		ps.setRequestDt(utilService.getTimestampFromMessageHistory(listHistory, "start_route"));
//		ps.setResponseDt(utilService.getTimestampFromMessageHistory(listHistory, "end_route"));
		ps.setCihubElapsedTime(routeElapsed);

		String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
    	String errorMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
    	
		if (!(null==errorStatus)) {
			ps.setStatus(errorStatus);
			if (!(null==errorMesg))
				ps.setErrorMsg(errorMesg);
		}
		else
			ps.setStatus("SUCCESS");

		paymentStatusRepo.save(ps);
		
	}

}
