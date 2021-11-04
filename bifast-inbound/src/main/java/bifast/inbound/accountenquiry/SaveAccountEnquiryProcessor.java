package bifast.inbound.accountenquiry;

import java.time.Duration;
import java.time.Instant;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.AccountEnquiry;
import bifast.inbound.pojo.ProcessDataPojo;
import bifast.inbound.repository.AccountEnquiryRepository;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private AccountEnquiryRepository accountEnquiryRepo;

	@SuppressWarnings("static-access")
	@Override
	public void process(Exchange exchange) throws Exception {
	
//		BusinessMessage bmRequest = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
//		BusinessMessage bmResponse = exchange.getMessage().getHeader("hdr_toBIobj", BusinessMessage.class);
		ProcessDataPojo processData = exchange.getMessage().getHeader("hdr_process_data", ProcessDataPojo.class);
		BusinessMessage bmRequest = processData.getBiRequestMsg();
		BusinessMessage bmResponse = processData.getBiResponseMsg();

		CreditTransferTransaction39 req = bmRequest.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		AccountEnquiry ae = new AccountEnquiry();
		
		String fullRequestMesg = exchange.getMessage().getHeader("hdr_frBI_jsonzip", String.class);
		String fullResponseMesg = exchange.getMessage().getHeader("hdr_toBI_jsonzip", String.class);
			
		ae.setAccountNo(req.getCdtrAcct().getId().getOthr().getId());
		ae.setAmount(req.getIntrBkSttlmAmt().getValue());
		ae.setReqBizMsgIdr(bmRequest.getAppHdr().getBizMsgIdr());
		ae.setCallStatus("SUCCESS");
//		ae.setChnlTrxId(null);
		
		ae.setKomiTrnsId(processData.getKomiTrnsId());
		
		long timeElapsed = Duration.between(processData.getStartTime(), Instant.now()).toMillis();
		ae.setElapsedTime(timeElapsed);

		ae.setSubmitDt(processData.getReceivedDt());
		
//		ae.setErrorMessage(null);
		
		ae.setFullRequestMessage(fullRequestMesg);
		ae.setFullResponseMsg(fullResponseMesg);
		
		ae.setOriginatingBank(bmRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		ae.setRecipientBank(bmRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());

		ae.setRespBizMsgIdr(bmResponse.getAppHdr().getBizMsgIdr());
		ae.setResponseCode(bmResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
		ae.setReasonCode(bmResponse.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getStsRsnInf().get(0).getRsn().getPrtry());
		
		accountEnquiryRepo.save(ae);
		
	}

}
