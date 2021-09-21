package bifast.outbound.processor;

import java.lang.reflect.Method;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bifast.outbound.model.BankCode;
import bifast.outbound.model.DomainCode;
import bifast.outbound.repository.BankCodeRepository;
import bifast.outbound.repository.DomainCodeRepository;

@Component
public class ValidateInputProcessor implements Processor  {

	@Autowired
	private DomainCodeRepository domainCodeRepo;
	@Autowired
	private BankCodeRepository bankCodeRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		Object objRequest = exchange.getIn().getBody(Object.class);
		
		Boolean validateChannel = false;
		Boolean validateBank = false;
		Boolean validatePurpose = false;

		String msgType = exchange.getMessage().getHeader("hdr_msgType", String.class);
		
	
		if (msgType.equals("acctenqr")) {
			validateChannel = true;
			validateBank = true;
			validatePurpose = true;
		}
		
		else if (msgType.equals("crdttrns")) {
			validateChannel = true;
			validateBank = true;
			validatePurpose = true;
		}
		else if (msgType.equals("ficrdttrns")) {
			validateChannel = true;
			validateBank = true;
		}
		
		if (validateChannel) {
			Method getChannel = objRequest.getClass().getMethod("getChannel");
			String channel = (String) getChannel.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CHANNEL.TYPE", channel).orElseThrow();

			Method setChannel = objRequest.getClass().getMethod("setChannel", String.class);
			setChannel.invoke(objRequest, channelDC.getKey());
		}
		
		if (validateBank) {
			
			Method getBank = objRequest.getClass().getMethod("getRecptBank");
			Method setBank = objRequest.getClass().getMethod("setRecptBank", String.class);
			String bankCode = (String) getBank.invoke(objRequest);
			BankCode bank = bankCodeRepo.findById(bankCode).orElseThrow();
			setBank.invoke(objRequest, bank.getBicCode());
			
			try {
				Method getDebtorBank = objRequest.getClass().getMethod("getDebtorBank");
				Method setDebtorBank = objRequest.getClass().getMethod("setDebtorBank", String.class);
				String debtorBank = (String) getDebtorBank.invoke(objRequest);
				BankCode debtorBankCode = bankCodeRepo.findById(debtorBank).orElseThrow();
				
				setDebtorBank.invoke(objRequest, debtorBankCode.getBicCode());
			}
			catch(NoSuchMethodException e) {}

			try {
				Method getCreditorBank = objRequest.getClass().getMethod("getCreditorBank");
				Method setCreditorBank = objRequest.getClass().getMethod("setCreditorBank", String.class);
				String creditorBank = (String) getCreditorBank.invoke(objRequest);
				BankCode creditorBankCode = bankCodeRepo.findById(creditorBank).orElseThrow();

				setCreditorBank.invoke(objRequest, creditorBankCode.getBicCode());
			}
			catch(NoSuchMethodException e) {}

		}
		
		if (validatePurpose) {
			Method getPurpose = objRequest.getClass().getMethod("getCategoryPurpose");
			Method setPurpose = objRequest.getClass().getMethod("setCategoryPurpose", String.class);
			String purpose = (String) getPurpose.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndValue("CATEGORY.PURPOSE", purpose).orElseThrow();
			setPurpose.invoke(objRequest, channelDC.getKey());
		}
		
			
	}

}
