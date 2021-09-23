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
public class ValidateProcessor implements Processor  {

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

		else if (msgType.equals("pymtsts")) {
			validateChannel = true;
		}
		
		else if (msgType.equals("prxyrgst")) {
			validateChannel = true;
		}

		else if (msgType.equals("prxyrslt")) {
			validateChannel = true;
		}

		if (validateChannel) {
			Method getChannel = objRequest.getClass().getMethod("getChannel");
			String channel = (String) getChannel.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CHANNEL.TYPE", channel).orElseThrow();
		}
		
		if (validateBank) {
			
			Method getBank = objRequest.getClass().getMethod("getRecptBank");
			String bankCode = (String) getBank.invoke(objRequest);
			BankCode bank = bankCodeRepo.findByBicCode(bankCode).orElseThrow();
			
			try {
				Method getDebtorBank = objRequest.getClass().getMethod("getDebtorBank");
				String debtorBank = (String) getDebtorBank.invoke(objRequest);
				BankCode debtorBankCode = bankCodeRepo.findById(debtorBank).orElseThrow();
				
			}
			catch(NoSuchMethodException e) {}

			try {
				Method getCreditorBank = objRequest.getClass().getMethod("getCreditorBank");
				String creditorBank = (String) getCreditorBank.invoke(objRequest);
				BankCode creditorBankCode = bankCodeRepo.findById(creditorBank).orElseThrow();

			}
			catch(NoSuchMethodException e) {}

		}
		
		if (validatePurpose) {
			Method getPurpose = objRequest.getClass().getMethod("getCategoryPurpose");
			String purpose = (String) getPurpose.invoke(objRequest);
			DomainCode channelDC = domainCodeRepo.findByGrpAndKey("CATEGORY.PURPOSE", purpose).orElseThrow();
		}
		
			
	}

}
