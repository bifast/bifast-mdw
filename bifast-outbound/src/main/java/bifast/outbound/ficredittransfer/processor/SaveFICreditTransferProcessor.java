package bifast.outbound.ficredittransfer.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs009.CreditTransferTransaction44;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.model.FICreditTransfer;
import bifast.outbound.repository.FICreditTransferRepository;
import bifast.outbound.service.UtilService;

@Component
public class SaveFICreditTransferProcessor implements Processor {
	@Autowired
	private FICreditTransferRepository fiCreditTransferRepo;
	@Autowired
	private UtilService utilService;

	@Override
	public void process(Exchange exchange) throws Exception {

		List<MessageHistory> listHistory = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);

		long routeElapsed = utilService.getRouteElapsed(listHistory, "komi.call-cihub");
		
		ChnlFICreditTransferRequestPojo chnRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlFICreditTransferRequestPojo.class);
	
		BusinessMessage biReqBM = exchange.getMessage().getHeader("fict_objreqbi", BusinessMessage.class);
		CreditTransferTransaction44 ctRequest = biReqBM.getDocument().getFiCdtTrf().getCdtTrfTxInf().get(0);

		FICreditTransfer ct = new FICreditTransfer();
		
		ct.setIntrRefId(chnRequest.getIntrnRefId());

		ct.setAmount(ctRequest.getIntrBkSttlmAmt().getValue());
		
		Long chnlTrxId = exchange.getMessage().getHeader("hdr_chnlTable_id", Long.class);
		if (!(null == chnlTrxId))
			ct.setChnlTrxId(chnlTrxId);

		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

//		String strCihubRequestTime = exchange.getMessage().getHeader("hdr_cihubRequestTime", String.class);
//		String strCihubResponseTime = exchange.getMessage().getHeader("hdr_cihubResponseTime", String.class);
//
//		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCihubRequestTime, dtf);
//		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCihubResponseTime, dtf);
//
//		ct.setCihubRequestDT(cihubRequestTime);
//		ct.setCihubResponseDT(cihubResponseTime);
		
		ct.setCihubRequestDT(utilService.getTimestampFromMessageHistory(listHistory, "start_route"));
		ct.setCihubResponseDT(utilService.getTimestampFromMessageHistory(listHistory, "end_route"));
		ct.setCihubElapsedTime(routeElapsed);

		ct.setCreditBic(ctRequest.getCdtr().getFinInstnId().getOthr().getId());
		
		ct.setCreDt(biReqBM.getDocument().getFiCdtTrf().getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());

		ct.setDebtorBic(ctRequest.getDbtr().getFinInstnId().getOthr().getId());
		
		String encrRequestMesg = exchange.getMessage().getHeader("cihubroute_encr_request", String.class);
		String encrResponseMesg = exchange.getMessage().getHeader("cihubroute_encr_response", String.class);

		ct.setFullRequestMessage(encrRequestMesg);
		ct.setFullResponseMsg(encrResponseMesg);
		
		ct.setOriginatingBank(biReqBM.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId());
		ct.setRequestBizMsgIdr(biReqBM.getAppHdr().getBizMsgIdr());
		
//		BusinessMessage biResponseBM = exchange.getMessage().getHeader("fict_objresponsebi", BusinessMessage.class);
		BusinessMessage biResponseBM = exchange.getMessage().getHeader("hdr_cihub_response", BusinessMessage.class);

		if (!(null== biResponseBM)) {
			ct.setResponseBizMsgIdr(biResponseBM.getAppHdr().getBizMsgIdr());
			ct.setResponseStatus(biResponseBM.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			ct.setCallStatus("SUCCESS");
		}
		
		String errorStatus = exchange.getMessage().getHeader("hdr_error_status", String.class);
		if (!(null==errorStatus))
			ct.setCallStatus(errorStatus);
		
		fiCreditTransferRepo.save(ct);
		
	}

}
