package bifast.outbound.processor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.exception.DuplicateIdException;
import bifast.outbound.exception.InputValidationException;
import bifast.outbound.exception.NoRefNullException;
import bifast.outbound.model.ChannelTransaction;
import bifast.outbound.model.DomainCode;
import bifast.outbound.pojo.RequestMessageWrapper;
import bifast.outbound.pojo.chnlrequest.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.pojo.chnlrequest.ChnlCreditTransferRequestPojo;
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

		String channelid = rmw.getChannelId(); 

//		// noRef tidak boleh duplikat
		List<ChannelTransaction> lChnlTrns = chnlTrnsRepo.findByChannelIdAndChannelRefId(channelid, noref); 
		if (lChnlTrns.size()>0) 
			throw new DuplicateIdException("Nomor RefId duplikat");


		//TODO nilai transaksi harian
		//TODO frekuensi transaksi harian
		//TODO kombinasi optional input parameter
		
		//TODO format data input
		
		//TODO sesuai domaincode, daftar bank
		
		Object objRequest = exchange.getIn().getBody(Object.class);
		
		Boolean validateBank = false;
		Boolean validatePurpose = false;

		String msgType = rmw.getMsgName();
		
//		if (msgType.equals("AEReq")) {
		if (rmw.getChannelRequest().getClass().getSimpleName().equals("ChnlAccountEnquiryRequestPojo")) {
			ChnlAccountEnquiryRequestPojo aeReq = (ChnlAccountEnquiryRequestPojo) rmw.getChannelRequest();
			validationService.validateAccountEnquiryRequest(aeReq);
//			validateChannel = true;
//			validateBank = true;
//			validatePurpose = true;
		}
		
		else if (msgType.equals("CTReq")) {
			ChnlCreditTransferRequestPojo req = rmw.getChnlCreditTransferRequest();
			try {
				DomainCode dm = domainCodeRepo.findByGrpAndKey("CUSTOMER.TYPE", req.getCrdtType()).orElseThrow();
				dm = domainCodeRepo.findByGrpAndKey("CUSTOMER.TYPE", req.getDbtrType()).orElseThrow();
			}
			catch(NoSuchElementException ne) {
					throw new InputValidationException ("Input value error");
			}
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
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", purpose).orElseThrow();
		}
		
			
	}

}
