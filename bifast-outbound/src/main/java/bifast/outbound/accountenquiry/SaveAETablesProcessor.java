package bifast.outbound.accountenquiry;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.model.AccountEnquiry;
import bifast.library.model.DomainCode;
import bifast.library.model.OutboundMessage;
import bifast.library.repository.AccountEnquiryRepository;
import bifast.library.repository.BankCodeRepository;
import bifast.library.repository.DomainCodeRepository;
import bifast.library.repository.OutboundMessageRepository;
import bifast.outbound.pojo.ChannelFaultResponse;

@Component
public class SaveAETablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private AccountEnquiryRepository accountEnqrRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);
		
		// JANGAN LANJUT JIKA BELUM LOLOS outRequest Msg
		if (null == outRequest) 
			return;

		ChannelAccountEnquiryReq chnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChannelAccountEnquiryReq.class);				
		String encriptedMessage = exchange.getMessage().getHeader("hdr_encrMessage", String.class);
		
		OutboundMessage outboundMessage = new OutboundMessage();
		
		String bizMsgIdr = outRequest.getAppHdr().getBizMsgIdr();
		
		List<OutboundMessage> listOutboundMsg = outboundMsgRepo.findAllByBizMsgIdr(bizMsgIdr);
		
		if (listOutboundMsg.size() == 0 ) {
			System.out.println("Insert table");
			outboundMessage = insertOutboundMessage(chnlRequest, outRequest, encriptedMessage);
			exchange.getMessage().setHeader("hdr_idtable", outboundMessage.getId());

		}
		
		// DISINI UNTUK UPDATE TABLE

		else {
			System.out.println("Update table");

			BusinessMessage outResponse = exchange.getMessage().getHeader("resp_objbi", BusinessMessage.class);

			String strChnlResponseTime = exchange.getMessage().getHeader("req_channelResponseTime", String.class);
			String strCiHubRequestTime = exchange.getMessage().getHeader("req_cihubRequestTime", String.class);
			String strCiHubResponseTime = exchange.getMessage().getHeader("req_cihubResponseTime", String.class);

			outboundMessage = updateOutboundMessage(listOutboundMsg.get(0), 
									chnlRequest, 
									outResponse,
									encriptedMessage,
									strChnlResponseTime,
									strCiHubRequestTime,
									strCiHubResponseTime) ;
			
			saveAccountEnquiryMsg (outboundMessage,outRequest);

		}
		
	}

	private OutboundMessage insertOutboundMessage (ChannelAccountEnquiryReq chnlRequest, 
													BusinessMessage request, 
													String encriptedMessage) {
		OutboundMessage outboundMessage = new OutboundMessage();
		
		outboundMessage.setBizMsgIdr(request.getAppHdr().getBizMsgIdr());
		
		String bic = request.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
		outboundMessage.setRecipientBank(bankCodeRepo.findByBicCode(bic).get().getBankCode());
		
		outboundMessage.setChannelRequestDT(LocalDateTime.now());
		outboundMessage.setFullRequestMessage(encriptedMessage);
		outboundMessage.setInternalReffId(chnlRequest.getChannelRefId());

		//channel kembalikan keposisi descriptive
		String chnCode = chnlRequest.getChannel();
		outboundMessage.setChannel(domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", chnCode).get().getValue());
		
		String requestClass = chnlRequest.getClass().getName();
		String msgName = domainCodeRepo.findByGrpAndKey("REQUEST.CLASS", requestClass).orElse(new DomainCode()).getValue();
		outboundMessage.setMessageName(msgName);
		
		outboundMessage = outboundMsgRepo.save(outboundMessage);

		return outboundMessage;
	}
	
	
	private OutboundMessage updateOutboundMessage (OutboundMessage outboundMessage,
										ChannelAccountEnquiryReq chnlRequest,
										BusinessMessage response, 
										String encriptedMessage,
										String strChnlResponseTime,
										String strCiHubRequestTime,
										String strCiHubResponseTime) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		LocalDateTime ChnlResponseTime = LocalDateTime.parse(strChnlResponseTime, dtf);
		outboundMessage.setChannelResponseDT(ChnlResponseTime);

		LocalDateTime cihubRequestTime = LocalDateTime.parse(strCiHubRequestTime, dtf);
		outboundMessage.setCihubRequestDT(cihubRequestTime);

		LocalDateTime cihubResponseTime = LocalDateTime.parse(strCiHubResponseTime, dtf);
		outboundMessage.setCihubResponseDT(cihubResponseTime);
		
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

	
	private void saveAccountEnquiryMsg (OutboundMessage auditTab,
										BusinessMessage outRequest) 
	{
		FIToFICustomerCreditTransferV08 accountEnqReq = outRequest.getDocument().getFiToFICstmrCdtTrf();
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		AccountEnquiry accountEnquiry = new AccountEnquiry();
		
		accountEnquiry.setAccountNo(accountEnqReq.getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
		accountEnquiry.setAmount(accountEnqReq.getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue());

		// dari XMLGregorianCalender ubah ke LocalDateTime
		accountEnquiry.setCreDt(accountEnqReq.getGrpHdr().getCreDtTm().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
		accountEnquiry.setIntrRefId(auditTab.getInternalReffId());
		accountEnquiry.setLogMessageId(auditTab.getId());
		accountEnquiry.setOriginatingBank(orgnlBank);
		accountEnquiry.setRecipientBank(auditTab.getRecipientBank());
		
		accountEnqrRepo.save(accountEnquiry);
		
	} 

//	private void saveError (OutboundMessage outboundMessage, String errorCode) {
//		outboundMessage.setRespStatus("ERROR");
//		outboundMessage.setErrorMessage(errorCode);
//		outboundMsgRepo.save(outboundMessage);
//	}
	
}
