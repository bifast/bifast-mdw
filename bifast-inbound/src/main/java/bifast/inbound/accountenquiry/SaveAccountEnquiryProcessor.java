package bifast.inbound.accountenquiry;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.inbound.model.AccountEnquiry;
import bifast.inbound.repository.AccountEnquiryRepository;
import bifast.inbound.service.UtilService;
import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;

@Component
public class SaveAccountEnquiryProcessor implements Processor {

	@Autowired
	private AccountEnquiryRepository accountEnquiryRepo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		@SuppressWarnings("unchecked")
		List<MessageHistory> listHistory = exchange.getProperty(Exchange.MESSAGE_HISTORY,List.class);
		
		long routeElapsed = utilService.getRouteElapsed(listHistory, "Inbound");
		
		BusinessMessage bmRequest = exchange.getMessage().getHeader("hdr_frBIobj", BusinessMessage.class);
		BusinessMessage bmResponse = exchange.getMessage().getHeader("hdr_toBIobj", BusinessMessage.class);
		
		CreditTransferTransaction39 req = bmRequest.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		AccountEnquiry ae = new AccountEnquiry();
		
		String fullRequestMesg = exchange.getMessage().getHeader("hdr_frBI_jsonzip", String.class);
		String fullResponseMesg = exchange.getMessage().getHeader("hdr_toBI_jsonzip", String.class);
		
//		String strRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
		
		ae.setAccountNo(req.getCdtrAcct().getId().getOthr().getId());
		ae.setAmount(req.getIntrBkSttlmAmt().getValue());
		ae.setReqBizMsgIdr(bmRequest.getAppHdr().getBizMsgIdr());
		ae.setCallStatus("SUCCESS");
//		ae.setChnlTrxId(null);
		
		ae.setSubmitDt(utilService.getTimestampFromMessageHistory(listHistory, "start_route"));
		ae.setElapsedTime(routeElapsed);

//		ae.setCreDt(LocalDateTime.now());
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
