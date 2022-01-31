package bifast.outbound.corebank;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bifast.outbound.pojo.RequestMessageWrapper;

@Component
public class MockCbResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		RequestMessageWrapper rmw = exchange.getProperty("prop_request_list", RequestMessageWrapper.class);
		
		Object oCbReq = rmw.getCorebankRequest();
		
		String strResponse = "";
		
		if (oCbReq.getClass().getSimpleName().equals("CbDebitRequestPojo")) {
			
			strResponse = "{ "
					+ "\"transactionId\" : \"000000\","
					+ "\"dateTime\" : \"2021-10-26T08:45:20.201\",\n"
					+ "\"merchantType\" : \"6000\",\n"
					+ "\"terminalId\" : \"00000\",\n"
					+ "\"noRef\" : \"KOM00000000\",\n"
					+ "\"status\" : \"ACTC\",\n"
					+ "\"reason\" : \"U000\",\n"
					+ "\"additionalInfo\" : \"ga ada\",\n"
					+ "\"accountNumber\" : \"0000000\"\n"
					+ "} " ; 
		}

		else if (oCbReq.getClass().getSimpleName().equals("CbAccountCustomerInfoRequestPojo")) {

			strResponse = "{ "
					+ "\"transactionId\" : \"000000\",\n"
					+ "\"dateTime\" : \"2021-10-26T08:45:20.201\",\n"
					+ "\"merchantType\" : \"6000\",\n"
					+ "\"terminalId\" : \"00000\",\n"
					+ "\"noRef\" : \"KOM00000000\",\n"
					+ "\"status\" : \"ACTC\",\n"
					+ "\"reason\" : \"U000\",\n"
					+ "\"emailAddressList\" : [\"ada@glodok.com\",\"yogi@mii.com\"],\n"
					+ "\"phoneNumberList\" : [\"081111\",\"08333333\"],\n"
					+ "\"accountNumber\" : \"522222320\",\n"
					+ "\"accountType\" : \"CACC\",\n"
					+ "\"customerName\" : \"Abang Andre\",\n"
					+ "\"customerType\" : \"01\",\n"
					+ "\"customerId\" : \"222233333\",\n"
					+ "\"customerIdType\" : \"01\",\n"
					+ "\"residentStatus\" : \"01\",\n"
					+ "\"townName\" : \"0040\"\n"
					+ "} ";
		}
		
		else if (oCbReq.getClass().getSimpleName().equals("CbDebitReversalPojo")) {
			strResponse = "{ "
					+ "\"transactionId\" : \"000000\",\n"
					+ "\"dateTime\" : \"2021-10-26T08:45:20.201\",\n"
					+ "\"merchantType\" : \"6000\",\n"
					+ "\"terminalId\" : \"00000\",\n"
					+ "\"noRef\" : \"KOM00000000\",\n"
					+ "\"status\" : \"ACTC\",\n"
					+ "\"reason\" : \"U000\",\n"
					+ "\"additionalInfo\" : \"ga ada\",\n"
					+ "\"accountNumber\" : \"0000000\"\n"
					+ "} " ; 

		}


		exchange.getMessage().setBody(strResponse);
		
	}

}
