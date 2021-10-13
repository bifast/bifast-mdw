package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class ChnlAccountEnquiryRequestPojo extends ChannelRequestBase {

	
	@JsonProperty("Amount")
	private String amount; 
	
	@JsonProperty("AccountNumber")
	private String creditorAccountNumber; 
	
	public ChnlAccountEnquiryRequestPojo() {}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}
	


	
}
