package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountCustomerInfo")
public class ChnlAccountCustomerInfoRequestPojo {

	public String noRef;
	public String accountNumber;
	
	@JsonCreator
	public ChnlAccountCustomerInfoRequestPojo(
		@JsonProperty(value="NoRef", required=true) String noRef,
		@JsonProperty(value="AccountNumber", required=true) String accountNumber
	) {
		this.noRef = noRef;
		this.accountNumber = accountNumber;
	}

	public String getNoRef() {
		return noRef;
	}

	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
		
	
	
}