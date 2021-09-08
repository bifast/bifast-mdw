package bifast.outbound.proxyregistration;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.model.DomainCode;
import bifast.library.model.OutboundMessage;
import bifast.library.model.ProxyMessage;
import bifast.library.repository.BankCodeRepository;
import bifast.library.repository.DomainCodeRepository;
import bifast.library.repository.OutboundMessageRepository;
import bifast.library.repository.ProxyMessageRepository;

@Component
public class SavePrxyTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private ProxyMessageRepository proxyMessageRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		System.out.println("di SavePrxyTablesProcessor");
		
		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);

		// JANGAN LANJUT JIKA BELUM LOLOS outRequest Msg
		if (null == outRequest) 
			return;

		ChannelProxyRegistrationReq chnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChannelProxyRegistrationReq.class);				
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
//									chnlRequest, 
									outResponse,
									encriptedMessage,
									strChnlResponseTime,
									strCiHubRequestTime,
									strCiHubResponseTime) ;
			
			saveProxyMessage (outboundMessage,outRequest);

		}
		
	}

	private OutboundMessage insertOutboundMessage (ChannelProxyRegistrationReq chnlRequest, 
													BusinessMessage request, 
													String encriptedMessage) {
		
		System.out.println("insert outboundMessage");
		OutboundMessage outboundMessage = new OutboundMessage();
		
		outboundMessage.setBizMsgIdr(request.getAppHdr().getBizMsgIdr());
		
		String bic = request.getAppHdr().getTo().getFIId().getFinInstnId().getOthr().getId();
//		outboundMessage.setRecipientBank(bankCodeRepo.findByBicCode(bic).get().getBankCode());

		outboundMessage.setChannelRequestDT(LocalDateTime.now());
		outboundMessage.setFullRequestMessage(encriptedMessage);
		outboundMessage.setInternalReffId(chnlRequest.getOrignReffId());

		System.out.println("Xy" + chnlRequest.getChannel());
		
		//channel kembalikan keposisi descriptive
		String chnCode = chnlRequest.getChannel();
		outboundMessage.setChannel(domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", chnCode).get().getValue());
		

		String requestClass = chnlRequest.getClass().getName();

		System.out.println("Class " + requestClass);
		String msgName = domainCodeRepo.findByGrpAndKey("REQUEST.CLASS", requestClass).orElse(new DomainCode()).getValue();
		outboundMessage.setMessageName(msgName);

		System.out.println("Xy");

		outboundMessage = outboundMsgRepo.save(outboundMessage);

		return outboundMessage;
	}
	 
	
	private OutboundMessage updateOutboundMessage (
										OutboundMessage outboundMessage,
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
		
		else if (!(null == response.getDocument().getPrxyRegnRspn())) {  
			outboundMessage.setRespBizMsgId(response.getAppHdr().getBizMsgIdr());
			outboundMessage.setRespStatus(response.getDocument().getPrxyRegnRspn().getRegnRspn().getPrxRspnSts().value());
			
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

	
	public void saveProxyMessage (OutboundMessage auditTab, BusinessMessage reqBi) {
		ProxyMessage proxyMsg = new ProxyMessage(); 
		
		ProxyRegistrationV01 proxyData = reqBi.getDocument().getPrxyRegn();
		
		proxyMsg.setLogMessageId(auditTab.getId());
		proxyMsg.setOperationType(proxyData.getRegn().getRegnTp().value());
		proxyMsg.setAccountName(proxyData.getRegn().getPrxyRegn().getAcct().getNm());
		proxyMsg.setAccountNumber(proxyData.getRegn().getPrxyRegn().getAcct().getId().getOthr().getId());
		proxyMsg.setAccountType(proxyData.getRegn().getPrxyRegn().getAcct().getTp().getPrtry());
		proxyMsg.setCustomerId(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getId());
		proxyMsg.setCustomerType(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getTp());
		
		proxyMsg.setDisplayName(proxyData.getRegn().getPrxyRegn().getDsplNm());
		proxyMsg.setProxyType(proxyData.getRegn().getPrxy().getTp());
		proxyMsg.setProxyValue(proxyData.getRegn().getPrxy().getVal());
		proxyMsg.setResidentStatus(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getRsdntSts());
		proxyMsg.setScndIdType(proxyData.getRegn().getPrxyRegn().getScndId().getTp());
		proxyMsg.setScndValue(proxyData.getRegn().getPrxyRegn().getScndId().getVal());
		proxyMsg.setTownName(proxyData.getSplmtryData().get(0).getEnvlp().getCstmr().getTwnNm());

		proxyMessageRepo.save(proxyMsg);
	}

	
}
