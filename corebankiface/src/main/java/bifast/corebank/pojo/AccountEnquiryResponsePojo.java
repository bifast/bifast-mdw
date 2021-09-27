package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

public class AccountEnquiryResponsePojo {
	
	@JsonProperty("AccountEnquiryResponse")
	AccountEnquiryResponse accountEnquiryResponse;

	public AccountEnquiryResponse getAccountEnquiryResponse() {
		return accountEnquiryResponse;
	}

	public void setAccountEnquiryResponse(AccountEnquiryResponse accountEnquiryResponse) {
		this.accountEnquiryResponse = accountEnquiryResponse;
	}

	
}
