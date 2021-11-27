package bifast.outbound.accountcustmrinfo.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountCustomerInfo")
public class ChnlAccountCustomerInfoRequestPojo {

	@JsonProperty("NoRef")
	public String noRef;
	@JsonProperty("TerminalId")
	public String terminalId;
	@JsonProperty("AccountNumber")
	public String accountNumber;
	
	@JsonCreator
	public ChnlAccountCustomerInfoRequestPojo(
		@JsonProperty(value="NoRef", required=true) String noRef,
		@JsonProperty(value="TerminalId", required=true) String terminalId,
		@JsonProperty(value="AccountNumber", required=true) String accountNumber
	) {
		this.noRef = noRef;
		this.terminalId = terminalId;
		this.accountNumber = accountNumber;
	}

	public String getNoRef() {
		return noRef;
	}

	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
		
	
	
}