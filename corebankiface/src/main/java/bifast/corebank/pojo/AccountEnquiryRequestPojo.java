package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountEnquiryRequestPojo {
	
	private String test;
	
	@JsonProperty("AccountEnquiryRequest")
	private AccountEnquiryRequest accountEnquiryRequest;
	
	public AccountEnquiryRequestPojo () {}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public AccountEnquiryRequest getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}
	public void setAccountEnquiryRequest(AccountEnquiryRequest accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}
	
	
	
}
