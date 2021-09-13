package bifast.outbound.credittransfer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.CreditTransfer;
import bifast.outbound.model.DomainCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.CreditTransferRepository;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;

@Component
public class SaveCTTablesProcessor implements Processor {

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

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("ct_objreqbi", BusinessMessage.class);
		
//		// JANGAN LANJUT JIKA BELUM LOLOS outRequest Msg
//		if (null == outRequest) 
//			return;
//		/////

		ChnlCreditTransferRequestPojo chnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChnlCreditTransferRequestPojo.class);				
		String encriptedMessage = exchange.getMessage().getHeader("ct_encrMessage", String.class);
		
		String bizMsgIdr = outRequest.getAppHdr().getBizMsgIdr();
		String chnlRefId = chnlRequest.getOrignReffId();

		List<OutboundMessage> listOutboundMsg = outboundMsgRepo.findAllByBizMsgIdr(bizMsgIdr);
		
		if (listOutboundMsg.size() == 0 ) {
			System.out.println("Insert table");
			OutboundMessage outboundMessage = new OutboundMessage();

			outboundMessage.setBizMsgIdr(bizMsgIdr);
			outboundMessage.setMessageName("Credit Transfer");
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

			BusinessMessage outResponse = exchange.getMessage().getHeader("ct_objresponsebi", BusinessMessage.class);

			String strChnlResponseTime = exchange.getMessage().getHeader("ct_channelResponseTime", String.class);
			String strCiHubRequestTime = exchange.getMessage().getHeader("ct_cihubRequestTime", String.class);
			String strCiHubResponseTime = exchange.getMessage().getHeader("ct_cihubResponseTime", String.class);

			outboundMessage = updateOutboundMessage(outboundMessage,
									chnlRequest, 
									outResponse,
									encriptedMessage,
									strChnlResponseTime,
									strCiHubRequestTime,
									strCiHubResponseTime) ;
			
			saveCreditTransferMsg (outboundMessage,outRequest);

		}
		
	}

	
	private OutboundMessage updateOutboundMessage (OutboundMessage outboundMessage,
										ChnlCreditTransferRequestPojo chnlRequest,
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

	
	private CreditTransfer saveCreditTransferMsg (OutboundMessage auditTab,
												  BusinessMessage outRequest) {
		
		FIToFICustomerCreditTransferV08 creditTransferReq = outRequest.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		CreditTransfer ct = new CreditTransfer();
		
		ct.setAmount(creditTransferReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());
		ct.setCrdtTrnRequestBizMsgIdr(auditTab.getBizMsgIdr());
		ct.setStatus(auditTab.getRespStatus());
		
		ct.setCreditorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		ct.setCreditorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getCdtrAcct().getTp().getPrtry());
		
		ct.setCreditorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getCdtr().getTp());
		
		if (ct.getCreditorType().equals("01"))
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setCreditorId(creditTransferReq.getCdtTrfTxInf().get(0).getCdtr().getId().getOrgId().getOthr().get(0).getId());
		
		// dari XMLGregorianCalender ubah ke LocalDateTime
		ct.setCreDt(creditTransferReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		
		ct.setDebtorAccountNumber(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
		ct.setDebtorAccountType(creditTransferReq.getCdtTrfTxInf().get(0).getDbtrAcct().getTp().getPrtry());
		ct.setDebtorType(creditTransferReq.getCdtTrfTxInf().get(0).getSplmtryData().get(0).getEnvlp().getDbtr().getTp());
		
		if (ct.getDebtorType().endsWith("01"))
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getPrvtId().getOthr().get(0).getId());
		else
			ct.setDebtorId(creditTransferReq.getCdtTrfTxInf().get(0).getDbtr().getId().getOrgId().getOthr().get(0).getId());		
		
		ct.setIntrRefId(auditTab.getInternalReffId());
		ct.setMsgType("Credit Transfer");
		ct.setOriginatingBank(orgnlBank);
		ct.setRecipientBank(outRequest.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId());
		ct.setLogMessageId(auditTab.getId());
		
		creditTransferRepo.save(ct);
		
		return ct;
		}

}
