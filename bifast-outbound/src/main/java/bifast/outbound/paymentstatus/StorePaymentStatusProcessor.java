package bifast.outbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.PaymentStatus;
import bifast.outbound.repository.PaymentStatusRepository;

@Component 
public class StorePaymentStatusProcessor implements Processor{

	@Autowired
	private PaymentStatusRepository paymentStatusRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

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

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		String strPSRequestTime = exchange.getMessage().getHeader("hdr_cihubRequestTime", String.class);
		String strPSResponseTime = exchange.getMessage().getHeader("hdr_cihubResponseTime", String.class);

		LocalDateTime psRequestTime = LocalDateTime.parse(strPSRequestTime, dtf);
		LocalDateTime psResponseTime = LocalDateTime.parse(strPSResponseTime, dtf);

		ps.setRequestDt(psRequestTime);
		ps.setResponseDt(psResponseTime);
		
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
