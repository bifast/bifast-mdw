package bifast.outbound.processor;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.model.BankCode;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.ChannelTransactionRepository;

@Component
public class SaveTableChannelProcessor implements Processor {
	@Autowired
	private ChannelTransactionRepository channelTransactionRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		Object objChnReq = exchange.getMessage().getHeader("hdr_channelRequest", Object.class);
		
		RequestMessageWrapper requestWr = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		
		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);
		String chnlRefId = exchange.getMessage().getHeader("hdr_chnlRefId", String.class);
		
		ChannelTransaction chnlTable = new ChannelTransaction();
		Long tableId = exchange.getMessage().getHeader("hdr_chnlTable_id", Long.class);
		
	

	
		if (!(null==tableId) ) {
			
			String errCode = exchange.getMessage().getHeader("hdr_error_status", String.class);
			String errMesg = exchange.getMessage().getHeader("hdr_error_mesg", String.class);
			Optional<ChannelTransaction> optChannelTrx = channelTransactionRepo.findById(tableId);
			
			if (optChannelTrx.isPresent()) {
				chnlTable = optChannelTrx.get();
				optChannelTrx.get().setResponseTime(LocalDateTime.now());
				
				if (null==errCode)
					optChannelTrx.get().setStatus("SUCCESS");
				else
					optChannelTrx.get().setStatus(errCode);
				
				if (!(null==errMesg))
					optChannelTrx.get().setErrorMsg(errMesg);
					
				channelTransactionRepo.save(chnlTable);
			}

		}

		else if (msgType.equals("AEReq")) {
//			ChnlAccountEnquiryRequestPojo channelRequest = (ChnlAccountEnquiryRequestPojo) objChnReq;

			chnlTable.setMsgName("Account Enquiry");

			chnlTable.setTransactionId(requestWr.getRequestId());
			chnlTable.setKomiTrnsId(requestWr.getKomiTrxId());
			chnlTable.setChannelId(requestWr.getChannelId());
		
//			BankCode bankCode = bankCodeRepo.findByBicCode(channelRequest.getRecptBank()).orElse(new BankCode());
//			chnlTable.setRecptBank(bankCode.getBankCode());
			chnlTable.setRecptBank(requestWr.getChnlAccountEnquiryRequest().getRecptBank());
			chnlTable.setAmount(requestWr.getChnlAccountEnquiryRequest().getAmount());
//			chnlTable.setCreditorAccountName();
			chnlTable.setCreditorAccountNumber(requestWr.getChnlAccountEnquiryRequest().getCreditorAccountNumber());
//			chnlTable.setDebtorAccountName(channelRequest);
//			chnlTable.setDebtorAccountNumber(channelRequest.);
			chnlTable.setRequestTime(LocalDateTime.now());
			
//			chnlTable.setStatus(msgType);
		}
		
		else if (msgType.equals("CTReq")) {
			
			ChnlCreditTransferRequestPojo channelRequest = (ChnlCreditTransferRequestPojo) objChnReq;

			chnlTable.setMsgName("Credit Transfer");
			chnlTable.setTransactionId(requestWr.getRequestId());
			chnlTable.setKomiTrnsId(requestWr.getKomiTrxId());
			chnlTable.setChannelId(requestWr.getChannelId());

//			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channelRequest.getChannel()).orElse(new DomainCode());
//			chnlTable.setChannelCode(channelDC.getValue());

//			BankCode bankCode = bankCodeRepo.findByBicCode(channelRequest.getRecptBank()).orElse(new BankCode());
//			chnlTable.setRecptBank(bankCode.getBankCode());
			chnlTable.setRecptBank(channelRequest.getRecptBank());

			chnlTable.setAmount(channelRequest.getAmount());
		
//			chnlTable.setCreditorAccountName(channelRequest.getCrdtName());
			chnlTable.setCreditorAccountNumber(channelRequest.getCrdtAccountNo());
//			chnlTable.setDebtorAccountName(channelRequest.getDbtrName());
			chnlTable.setDebtorAccountNumber(channelRequest.getDbtrAccountNo());
			chnlTable.setRequestTime(LocalDateTime.now());
//			chnlTable.setStatus(msgType);

		}
		
//		else if (msgType.equals("ficrdttrns")) {
//			ChnlFICreditTransferRequestPojo channelRequest = (ChnlFICreditTransferRequestPojo) objChnReq;
//
//			chnlTable.setMsgName("FI Credit Transfer");
//			chnlTable.setTransactionId(chnlRefId);
//
//			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channelRequest.getChannel()).orElse(new DomainCode());
//			chnlTable.setChannelCode(channelDC.getValue());
//
//			BankCode bankCode = bankCodeRepo.findByBicCode(channelRequest.getRecptBank()).orElse(new BankCode());
//			chnlTable.setRecptBank(bankCode.getBankCode());
//
//			chnlTable.setAmount(channelRequest.getAmount());
////			chnlTable.setCreditorAccountName(channelRequest.getCrdtName());
////			chnlTable.setCreditorAccountNumber(channelRequest.getCrdtAccountNo());
////			chnlTable.setDebtorAccountName(channelRequest.getDbtrName());
////			chnlTable.setDebtorAccountNumber(channelRequest.getDbtrAccountNo());
//			chnlTable.setRequestTime(LocalDateTime.now());
////			chnlTable.setStatus(msgType);
//
//		}

		else if (msgType.equals("pymtsts")) {
			ChnlPaymentStatusRequestPojo channelRequest = (ChnlPaymentStatusRequestPojo) objChnReq;

			chnlTable.setMsgName("Payment Status");
			chnlTable.setTransactionId(requestWr.getRequestId());
			chnlTable.setKomiTrnsId(requestWr.getKomiTrxId());
			chnlTable.setChannelId(requestWr.getChannelId());

//			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channelRequest.getChannel()).orElse(new DomainCode());
//			chnlTable.setChannelCode(channelDC.getValue());

			BankCode bankCode = bankCodeRepo.findByBicCode(channelRequest.getRecptBank()).orElse(new BankCode());
			chnlTable.setRecptBank(bankCode.getBankCode());

//			chnlTable.setAmount(channelRequest.getAmount());
//			chnlTable.setCreditorAccountName(channelRequest.getCrdtName());
//			chnlTable.setCreditorAccountNumber(channelRequest.getCrdtAccountNo());
//			chnlTable.setDebtorAccountName(channelRequest.getDbtrName());
//			chnlTable.setDebtorAccountNumber(channelRequest.getDbtrAccountNo());
			chnlTable.setRequestTime(LocalDateTime.now());
//			chnlTable.setStatus(msgType);

		}

		else if (msgType.equals("prxyrgst")) {
			ChnlProxyRegistrationRequestPojo channelRequest = (ChnlProxyRegistrationRequestPojo) objChnReq;

			chnlTable.setMsgName("Proxy Registration");
			chnlTable.setTransactionId(requestWr.getRequestId());
			chnlTable.setKomiTrnsId(requestWr.getKomiTrxId());
			chnlTable.setChannelId(requestWr.getChannelId());

//			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channelRequest.getChannel()).orElse(new DomainCode());
//			chnlTable.setChannelCode(channelDC.getValue());

//			chnlTable.setAmount(channelRequest.getAmount());
//			chnlTable.setCreditorAccountName(channelRequest.getCrdtName());
//			chnlTable.setCreditorAccountNumber(channelRequest.getCrdtAccountNo());
			chnlTable.setDebtorAccountName(channelRequest.getRegisterAccountName());
			chnlTable.setDebtorAccountNumber(channelRequest.getRegisterAccountNumber());
//			chnlTable.setRecptBank(channelRequest.getRecptBank());
			chnlTable.setRequestTime(LocalDateTime.now());
//			chnlTable.setStatus(msgType);

		}

		else if (msgType.equals("prxyrslt")) {
			ChnlProxyResolutionRequestPojo channelRequest = (ChnlProxyResolutionRequestPojo) objChnReq;

			chnlTable.setMsgName("Proxy Resolution");
			chnlTable.setTransactionId(requestWr.getRequestId());
			chnlTable.setKomiTrnsId(requestWr.getKomiTrxId());
			chnlTable.setChannelId(requestWr.getChannelId());

//			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channelRequest.getChannel()).orElse(new DomainCode());
//			chnlTable.setChannelCode(channelDC.getValue());

//			chnlTable.setAmount(channelRequest.getAmount());
//			chnlTable.setCreditorAccountName(channelRequest.getCrdtName());
//			chnlTable.setCreditorAccountNumber(channelRequest.getCrdtAccountNo());
//			chnlTable.setDebtorAccountName(channelRequest.getgetAccName());
//			chnlTable.setDebtorAccountNumber(channelRequest.getAccNumber());
//			chnlTable.setRecptBank(channelRequest.getRecptBank());
			chnlTable.setRequestTime(LocalDateTime.now());
//			chnlTable.setStatus(msgType);		
		}

		chnlTable = channelTransactionRepo.save(chnlTable);

		requestWr.setChnlTrnsIdTable(chnlTable.getId());
		exchange.getMessage().setHeader("hdr_request_list", requestWr);

		exchange.getMessage().setHeader("hdr_chnlTable_id", chnlTable.getId());
	}

	
}
