package bifast.outbound.credittransfer.processor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.custom.Document;
import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;
import bifast.library.iso20022.pacs008.CreditTransferTransaction39;
import bifast.library.iso20022.service.AppHeaderService;
import bifast.library.iso20022.service.Pacs028MessageService;
import bifast.library.iso20022.service.Pacs028Seed;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.Settlement;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.SettlementRepository;
import bifast.outbound.service.UtilService;

@Component
public class PrePaymentStatusProc implements Processor{
	@Autowired private AppHeaderService appHeaderService;
	@Autowired private ChannelTransactionRepository chnlTrnsRepo;
	@Autowired private CreditTransferRepository ctRepo;
	@Autowired private Pacs028MessageService pacs028MessageService;
	@Autowired private SettlementRepository settlementRepo;
	@Autowired private UtilService utilService;

	private static Logger logger = LoggerFactory.getLogger(PrePaymentStatusProc.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		BusinessMessage ctReq = rmw.getCreditTransferRequest();
		
		String end2EndId = ctReq.getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getEndToEndId();
		Optional<Settlement> oSettlement = settlementRepo.findByOrgnlEndToEndId(end2EndId);
		if (oSettlement.isPresent()) {
			updateRequestTableLog(rmw, "ACCEPTED", "ACTC", "U000");
			logger.debug("[PSReq:" + rmw.getRequestId() + "] Settlement sudah diterima");
			exchange.setProperty("prop_sttlfound", true);
		}
		else {
			logger.debug("[PSReq:" + rmw.getRequestId() + "] Settlement belum diterima");
			BusinessMessage psr = buildPSR(rmw);
			exchange.getMessage().setBody(psr);
			exchange.setProperty("prop_sttlfound", false);

		}
		
	}
	
	private void updateRequestTableLog (RequestMessageWrapper rmw, String status, String responseCode, String reasonCode) {
		CreditTransfer ct = ctRepo.findByKomiTrnsId(rmw.getKomiTrxId()).orElse(new CreditTransfer());
		ct.setCallStatus("SUCCESS");
		ct.setResponseCode(responseCode);
		ct.setReasonCode(reasonCode);
		ct.setLastUpdateDt(LocalDateTime.now());
		Long dur = ChronoUnit.MILLIS.between(ct.getCreateDt(), LocalDateTime.now());
		ct.setCihubElapsedTime(dur);

		ctRepo.save(ct);

		ChannelTransaction ch = chnlTrnsRepo.findById(rmw.getKomiTrxId()).orElse(new ChannelTransaction());
		ch.setCallStatus("SUCCESS");
		ch.setResponseCode(responseCode);
//		if (ct.getPsCounter()== config.getMaxRetryBeforeNotif()) 
//			exchange.setProperty("pr_notif", "yes");
//		else
//			exchange.setProperty("pr_notif", "no");
		chnlTrnsRepo.save(ch);

	}

	private BusinessMessage buildPSR (RequestMessageWrapper rmw) throws Exception{

		CreditTransferTransaction39 ctReq = rmw.getCreditTransferRequest().getDocument().getFiToFICstmrCdtTrf().getCdtTrfTxInf().get(0);
		
		String bizMsgId = utilService.genBusMsgId("000", rmw.getKomiTrxId(), "99");
		String msgId = utilService.genMessageId("000", rmw.getKomiTrxId());
		
		Pacs028Seed seed = new Pacs028Seed();
		seed.setMsgId(msgId);
		seed.setOrgnlEndToEnd(ctReq.getPmtId().getEndToEndId());

		BusinessApplicationHeaderV01 hdr = new BusinessApplicationHeaderV01();
		hdr = appHeaderService.getAppHdr(ctReq.getCdtrAgt().getFinInstnId().getOthr().getId(), "pacs.028.001.04", bizMsgId);
		
		Document doc = new Document();
		doc.setFiToFIPmtStsReq(pacs028MessageService.paymentStatusRequest(seed));
		
		BusinessMessage busMsg = new BusinessMessage();
		busMsg.setAppHdr(hdr);
		busMsg.setDocument(doc);

		return busMsg;
	}
}
