package bifast.outbound.processor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.exception.DuplicateIdException;
import bifast.outbound.exception.InputValidationException;
import bifast.outbound.exception.NoRefNullException;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.DomainCode;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.repository.ChannelTransactionRepository;
import bifast.outbound.repository.DomainCodeRepository;
import bifast.outbound.service.ValidationService;

@Component
public class ValidateProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private ChannelTransactionRepository chnlTrnsRepo;
	@Autowired
	private ValidationService validationService;

	@Override
	public void process(Exchange exchange) throws Exception {

		RequestMessageWrapper rmw = exchange.getMessage().getHeader("hdr_request_list", RequestMessageWrapper.class);
		// yg mesti divalidas
		String noref = rmw.getRequestId();
		
		if ((noref.isBlank()) || (noref.isEmpty()))
			throw new NoRefNullException("Nomor RefId kosong");
		if (noref.length()>20)
			throw new NoRefNullException("RefId tidak boleh lebih dari 20 char.");

		String channelid = rmw.getChannelId(); 
		String msgType = rmw.getMsgName();

//		// noRef tidak boleh duplikat
		List<ChannelTransaction> lChnlTrns = chnlTrnsRepo.findByChannelIdAndChannelRefId(channelid, noref); 
		if ((msgType.equals("AEReq")) ||
		 	(msgType.equals("CTReq")) || 
		 	(msgType.equals("PrxRegn")) ) 
		{
			if (lChnlTrns.size()>0) 
				throw new DuplicateIdException("Nomor RefId duplikat");
		}

		//TODO nilai transaksi harian
		//TODO frekuensi transaksi harian
		//TODO kombinasi optional input parameter
		
		//TODO format data input
		
		//TODO sesuai domaincode, daftar bank
		
		Object objRequest = exchange.getIn().getBody(Object.class);
		
		Boolean validatePurpose = false;

		
//		if (msgType.equals("AEReq")) {
		if (rmw.getChannelRequest().getClass().getSimpleName().equals("ChnlAccountEnquiryRequestPojo")) {
			ChnlAccountEnquiryRequestPojo aeReq = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();
			validationService.validateAccountEnquiryRequest(aeReq);
		}
		
//		else if (msgType.equals("CTReq")) {
		else if (rmw.getChannelRequest().getClass().getSimpleName().equals("ChnlCreditTransferRequestPojo")) {
			ChnlCreditTransferRequestPojo req = rmw.getChnlCreditTransferRequest();
			validationService.validateCreditTransferRequest(req);
		}

		else if (msgType.equals("PrxRegn")) {
			ChnlProxyRegistrationRequestPojo proxyReq = rmw.getChnlProxyRegistrationRequest();
			try {
				@SuppressWarnings("unused")
				DomainCode dm = domainCodeRepo.findByGrpAndKey("PRXYOPER.TYPE", proxyReq.getRegistrationType()).orElseThrow();
			}
			catch(NoSuchElementException ne) {
					throw new InputValidationException ("Proxy Operation type error");
			}
			validationService.validateProxyRegistration(proxyReq);
		}

		else if (msgType.equals("PSReq")) {
//			validateChannel = true;
		}
		
		else if (rmw.getChannelRequest().getClass().getSimpleName().equals("ChnlProxyRegistrationRequest")) {
//			validateChannel = true;
		}

		else if (msgType.equals("PrxReslReq")) {
//			validateChannel = true;
		}

		
		if (validatePurpose) {
			Method getPurpose = objRequest.getClass().getMethod("getCategoryPurpose");
			String purpose = (String) getPurpose.invoke(objRequest);
			@SuppressWarnings("unused")
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", purpose).orElseThrow();
		}
		
			
	}

}
