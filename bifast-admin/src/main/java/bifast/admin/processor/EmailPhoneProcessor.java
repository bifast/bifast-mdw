package bifast.admin.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.admin.pojo.CbCustomerInfoRequestPojo;
import bifast.admin.pojo.CbCustomerInfoResponsePojo;

@Component
public class EmailPhoneProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		CbCustomerInfoRequestPojo request = exchange.getMessage().getBody(CbCustomerInfoRequestPojo.class);
		
		CbCustomerInfoResponsePojo response = new CbCustomerInfoResponsePojo();

		response.setTransactionId("000000");
		response.setDateTime("2021-01-06T09:09:01.000");
		response.setMerchantType("0202");
		response.setTerminalId("00000");
		response.setNoRef("4005000505");
		response.setStatus("ACTC");
		response.setReason("U000");
		
		List<String> emailAddrList = new ArrayList<String>();
		emailAddrList.add("frans.mazhar@gmail.com");
		emailAddrList.add("yosef@gmial.com");
		response.setEmailAddressList(emailAddrList);
		
		List<String> phoneNoList = new ArrayList<String>();
		phoneNoList.add("0822-008844");
		phoneNoList.add("0822-111114");
		response.setPhoneNumberList(phoneNoList);
		
		response.setAccountNumber(request.getAccountNumber());
		response.setAccountType("CACC");
		response.setCustomerName("Anton");
		response.setCustomerType("01");
		response.setCustomerId("4899588802");
		response.setCustomerIdType("02");
		response.setResidentStatus("01");
		response.setTownName("0300");
		
		exchange.getMessage().setBody(response);
	}

}
