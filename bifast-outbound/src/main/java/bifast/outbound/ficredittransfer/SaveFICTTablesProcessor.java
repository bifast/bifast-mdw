package bifast.outbound.ficredittransfer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.DomainCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;

@Component
public class SaveFICTTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private CreditTransferRepository creditTransferRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("fict_objreqbi", BusinessMessage.class);
		
		ChnlFICreditTransferRequestPojo chnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlFICreditTransferRequestPojo.class);				
		String encriptedMessage = exchange.getMessage().getHeader("fict_encrMessage", String.class);
		
		String bizMsgIdr = outRequest.getAppHdr().getBizMsgIdr();
		String chnlRefId = chnlRequest.getOrignReffId();

		List<OutboundMessage> listOutboundMsg = outboundMsgRepo.findAllByBizMsgIdr(bizMsgIdr);
		
		if (listOutboundMsg.size() == 0 ) {
			System.out.println("Insert table");
			OutboundMessage outboundMessage = new OutboundMessage();

			outboundMessage.setBizMsgIdr(bizMsgIdr);
			outboundMessage.setMessageName("FI Credit Transfer");
			outboundMessage.setChannelRequestDT(LocalDateTime.now());
			outboundMessage.setFullRequestMessage(encriptedMessage);
			outboundMessage.setInternalReffId(chnlRefId);
			
			DomainCode channelCode = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", chnlRequest.getChannel()).orElse(new DomainCode());
			outboundMessage.setChannel(channelCode.getValue());
			
			BankCode bankCode = bankCodeRepo.findByBicCode(chnlRequest.getRecptBank()).orElse(new BankCode());
			outboundMessage.setRecipientBank(bankCode.getBankCode());

			outboundMessage = outboundMsgRepo.save(outboundMessage);
			
			exchange.getMessage().setHeader("hdr_idtable", outboundMessage.getId());

		}
		
		// DISINI UNTUK UPDATE TABLE

		else {
			System.out.println("Update table");

			OutboundMessage outboundMessage = listOutboundMsg.get(0);

			BusinessMessage outResponse = exchange.getMessage().getHeader("fict_objresponsebi", BusinessMessage.class);

			String strChnlResponseTime = exchange.getMessage().getHeader("fict_channelResponseTime", String.class);
			String strCiHubRequestTime = exchange.getMessage().getHeader("fict_cihubRequestTime", String.class);
			String strCiHubResponseTime = exchange.getMessage().getHeader("fict_cihubResponseTime", String.class);

			outboundMessage = updateOutboundMessage(outboundMessage,
									chnlRequest, 
									outResponse,
									encriptedMessage,
									strChnlResponseTime,
									strCiHubRequestTime,
									strCiHubResponseTime) ;
			
			saveFICreditTransferMsg (outboundMessage,outRequest);

		}
		
	}

	
	private OutboundMessage updateOutboundMessage (OutboundMessage outboundMessage,
										ChnlFICreditTransferRequestPojo chnlRequest,
										BusinessMessage response, 
										String encriptedMessage,
										String strChnlResponseTime,
										String strCiHubRequestTime,
										String strCiHubResponseTime) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		LocalDateTime ChnlResponseTime = LocalDateTime.parse(strChnlResponseTime, dtf);
		outboundMessage.setChannelResponseDT(ChnlResponseTime);

		if (!(null==strCiHubRequestTime)) {
			LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
			outboundMessage.setCihubRequestDT(cihubRequestTime);
		}
		
		if (!(null==strCiHubResponseTime)) {
			LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
			outboundMessage.setCihubResponseDT(cihubResponseTime);
		}
		
		outboundMessage.setFullResponseMsg(encriptedMessage);


		if (null == response) {  // tidak terima response dari BI berarti timeout
			outboundMessage.setRespStatus("TIMEOUT");
			outboundMessage.setErrorMessage("Timeout terima message dari CI-HUB");
		}
		
		else if (!(null == response.getDocument().getFiToFIPmtStsRpt())) {  // response ct pacs002
			outboundMessage.setRespBizMsgId(response.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(response.getDocument().getFiToFIPmtStsRpt().getTxInfAndSts().get(0).getTxSts());
			
		}			
	
		else {  // msg Reject
					
			String rjctMesg = response.getDocument().getMessageReject().getRsn().getRsnDesc();
			if (rjctMesg.length() > 400)
				rjctMesg = rjctMesg.substring(0, 400);
			
			outboundMessage.setRespStatus("FAILURE");
			outboundMessage.setErrorMessage(rjctMesg);
		}
		
		outboundMsgRepo.save(outboundMessage);
		return outboundMessage;
		
	}

	
	private CreditTransfer saveFICreditTransferMsg (OutboundMessage auditTab,
												  BusinessMessage outRequest) {
		
		FinancialInstitutionCreditTransferV09 creditTransferReq = outRequest.getDocument().getFiCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		CreditTransfer ct = new CreditTransfer();
		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(auditTab.getBizMsgIdr());
		ct.setStatus(auditTab.getRespStatus());
		
		
		// dari XMLGregorianCalender ubah ke LocalDateTime
		ct.setCreDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
				
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("FI Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		ct.setLogMessageId(auditTab.getId());
		
		creditTransferRepo.save(ct);
		
		return ct;
		}

}
