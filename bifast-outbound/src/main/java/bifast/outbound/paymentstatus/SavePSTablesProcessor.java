package bifast.outbound.paymentstatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.model.PaymentStatus;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;
import bifast.outbound.repository.PaymentStatusRepository;

@Component
public class SavePSTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private PaymentStatusRepository paymentStatusRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		
		BusinessMessage outRequest = exchange.getMessage().getHeader("ps_objreqbi", BusinessMessage.class);
		
		ChnlPaymentStatusRequestPojo chnlRequest = exchange.getMessage().getHeader("ps_pymtstsreq", ChnlPaymentStatusRequestPojo.class);
		
		String encriptedMessage = exchange.getMessage().getHeader("ps_encrMessage", String.class);
		
		String psStatus = exchange.getMessage().getHeader("ps_status", String.class);
		String bizMsgIdr = outRequest.getAppHdr().getBizMsgIdr();
		
		if (psStatus.equals("New")) {
			System.out.println("Insert table PS");
			OutboundMessage outboundMessage = new OutboundMessage();

			outboundMessage.setBizMsgIdr(bizMsgIdr);	
			outboundMessage.setMessageName("Payment Status");
				
			outboundMessage.setChannelRequestDT(LocalDateTime.now());
			outboundMessage.setFullRequestMessage(encriptedMessage);
			
			outboundMessage.setInternalReffId(chnlRequest.getEndToEndId());
			outboundMessage.setChannel("Other");
			
			BankCode bankCode = bankCodeRepo.findByBicCode(chnlRequest.getRecptBank()).orElse(new BankCode());
			outboundMessage.setRecipientBank(bankCode.getBankCode());

			outboundMessage = outboundMsgRepo.save(outboundMessage);
			
		}
		
		// DISINI UNTUK UPDATE TABLE
		else  {

			System.out.println("Update table PS");

			List<OutboundMessage> listOutboundMsg = outboundMsgRepo.findAllByBizMsgIdr(bizMsgIdr);

			OutboundMessage outboundMessage = listOutboundMsg.get(0);

			BusinessMessage outResponse = exchange.getMessage().getHeader("ps_objresponsebi", BusinessMessage.class);

			String strChnlResponseTime = exchange.getMessage().getHeader("ps_channelResponseTime", String.class);
			String strCiHubRequestTime = exchange.getMessage().getHeader("ps_cihubRequestTime", String.class);
			String strCiHubResponseTime = exchange.getMessage().getHeader("ps_cihubResponseTime", String.class);
			
			outboundMessage = updateOutboundMessage(outboundMessage,
									chnlRequest, 
									outResponse,
									encriptedMessage,
									strChnlResponseTime,
									strCiHubRequestTime,
									strCiHubResponseTime) ;
			
			savePaymentStatusMsg (outboundMessage,outRequest, psStatus);

		}
		
		
	}

	
	private OutboundMessage updateOutboundMessage (OutboundMessage outboundMessage,
										ChnlPaymentStatusRequestPojo chnlRequest,
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

	
	private PaymentStatus savePaymentStatusMsg (OutboundMessage auditTab,
												  BusinessMessage outRequest,
												  String status) {
		
		String orgnlBank = outRequest.getAppHdr().getFr().getFIId().getFinInstnId().getOthr().getId();
		
		PaymentStatus ps = new PaymentStatus();
		
		ps.setBizMsgIdr(outRequest.getAppHdr().getBizMsgIdr());
		ps.setOrgnlEndToEndId(outRequest.getDocument().getFiToFIPmtStsReq().getTxInf().get(0).getOrgnlEndToEndId());
		ps.setStatus(orgnlBank);
		ps.setUpdateDt(LocalDateTime.now());
		
		ps.setStatus(status);
		if (status.equals("Timeout")) {
			ps.setSaf("True");
			ps.setRetryCount(0);
		}
		
		ps = paymentStatusRepo.save(ps);
		
		return ps;
		}

}
