package bifast.corebank.customerinfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CustomerInfoProcessor implements Processor{


	@Override
	public void process(Exchange exchange) throws Exception {

		CbCustomerInfoRequestPojo custInfoRequest = exchange.getMessage().getBody(CbCustomerInfoRequestPojo.class);

		//TODO process Account Balance
		
		CbCustomerInfoResponsePojo custInfoResponse = new CbCustomerInfoResponsePojo();
		custInfoResponse.setKomiTrnsId(custInfoRequest.getKomiTrnsId());
		
		custInfoResponse.setAccountNumber(custInfoRequest.getAccountNumber());
		custInfoResponse.setAccountType("CACC");
		custInfoResponse.setCustomerId("KTP3000040050004");
		custInfoResponse.setCustomerIdType("01");
		custInfoResponse.setCustomerName("JOHAN");
		custInfoResponse.setCustomerType("01");
		
		List<String> lEmailAddr = new ArrayList<String>();
		lEmailAddr.add("andrew.smith@gmial.com");
		lEmailAddr.add("Wahyu@gmail.com");
		custInfoResponse.setEmailAddressList(lEmailAddr);

		custInfoResponse.setKomiTrnsId(custInfoRequest.getKomiTrnsId());
		custInfoResponse.setMerchantType(custInfoRequest.getMerchantType());
		custInfoResponse.setNoRef(custInfoRequest.getNoRef());

		List<String> lPhone = new ArrayList<String>();
		lPhone.add("0811111111");
		lPhone.add("0812222222");
		custInfoResponse.setPhoneNumberList(lPhone);

		custInfoResponse.setReason("U000");
		custInfoResponse.setResidentStatus("01");
		custInfoResponse.setStatus("ACTC");
		custInfoResponse.setTerminalId(custInfoRequest.getTerminalId());
		custInfoResponse.setTownName("0200");
		
		exchange.getMessage().setBody(custInfoResponse);

	}

}
