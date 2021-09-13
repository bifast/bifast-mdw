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
import bifast.outbound.model.DomainCode;
import bifast.outbound.model.OutboundMessage;
import bifast.outbound.model.ProxyMessage;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.repository.OutboundMessageRepository;
import bifast.outbound.repository.ProxyMessageRepository;

@Component
public class SavePrxyTablesProcessor implements Processor {

	@Autowired
	private OutboundMessageRepository outboundMsgRepo;
	@Autowired
	private ProxyMessageRepository proxyMessageRepo;
	@Autowired
	private DomainCodeRepository domainCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {
	
		BusinessMessage outRequest = exchange.getMessage().getHeader("req_objbi", BusinessMessage.class);

		// JANGAN LANJUT JIKA BELUM LOLOS outRequest Msg
		if (null == outRequest) 
			return;

//		ChannelProxyRegistrationReq chnlRequest = exchange.getMessage().getHeader("hdr_channelRequest", ChannelProxyRegistrationReq.class);				

		Object objChannelRequest = exchange.getMessage().getHeader("hdr_channelRequest", Object.class);
		String msgName = domainCodeRepo.findByGrpAndKey("REQUEST.CLASS", objChannelRequest.getClass().getName()).orElse(new DomainCode()).getValue();

		Method getOrignReffId = objChannelRequest.getClass().getMethod("getOrignReffId", null);
		String chnlRefId = (String) getOrignReffId.invoke(objChannelRequest, null);
		Method getChannel =  objChannelRequest.getClass().getMethod("getChannel", null);
		String channel = (String) getChannel.invoke(objChannelRequest, null);
		
		System.out.println(msgName);
		
		String encriptedMessage = exchange.getMessage().getHeader("hdr_encrMessage", String.class);
		
		OutboundMessage outboundMessage = new OutboundMessage();
	
		String bizMsgIdr = outRequest.getAppHdr().getBizMsgIdr();
		
		List<OutboundMessage> listOutboundMsg = outboundMsgRepo.findAllByBizMsgIdr(bizMsgIdr);
		
		if (listOutboundMsg.size() == 0 ) {
			System.out.println("Insert table");
			
			outboundMessage.setBizMsgIdr(bizMsgIdr);
			outboundMessage.setChannelRequestDT(LocalDateTime.now());
			outboundMessage.setFullRequestMessage(encriptedMessage);
			outboundMessage.setInternalReffId(chnlRefId);
			outboundMessage.setChannel(domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channel).get().getValue());
			outboundMessage.setMessageName(msgName);

			outboundMessage = outboundMsgRepo.save(outboundMessage);
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
													outResponse,
													encriptedMessage,
													strChnlResponseTime,
													strCiHubRequestTime,
													strCiHubResponseTime) ;
			
			if (msgName.equals("Proxy Registration"))
				saveProxyRegistration (outboundMessage.getId(), outRequest);
			// TODO save Proxy Resolution
//			else 
//				saveProxyResolution(outboundMessage.getId(), outRequest);

		}
		
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
		
		else if (!(null == response.getDocument().getMessageReject())) {
			String rjctMesg = response.getDocument().getMessageReject().getRsn().getRsnDesc();
			if (rjctMesg.length() > 400)
				rjctMesg = rjctMesg.substring(0, 400);
			
			outboundMessage.setRespStatus("FAILURE");
			outboundMessage.setErrorMessage(rjctMesg);
		}
		
		else {  
			outboundMessage.setRespBizMsgId(response.getAppHdr().getBizMsgIdr());
			if (!(null == response.getDocument().getPrxyRegnRspn())) 
				outboundMessage.setRespStatus(response.getDocument().getPrxyRegnRspn().getRegnRspn().getPrxRspnSts().value());
			else 
				outboundMessage.setRespStatus(response.getDocument().getPrxyLookUpRspn().getLkUpRspn().getRegnRspn().getPrxRspnSts().value());
		}			
		
		outboundMsgRepo.save(outboundMessage);
		return outboundMessage;		
	}

	
	public void saveProxyResolution (Long outboundMessageId, BusinessMessage reqBi) {
		ProxyMessage proxyMsg = new ProxyMessage(); 
		
		ProxyRegistrationV01 proxyData = reqBi.getDocument().getPrxyRegn();
		
		proxyMsg.setLogMessageId(outboundMessageId);
		
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

	public void saveProxyRegistration (Long outboundMessageId, BusinessMessage reqBi) {
		ProxyMessage proxyMsg = new ProxyMessage(); 
		
		ProxyRegistrationV01 proxyData = reqBi.getDocument().getPrxyRegn();
		
		proxyMsg.setLogMessageId(outboundMessageId);
		
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
