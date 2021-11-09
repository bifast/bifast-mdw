package bifast.outbound.credittransfer.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferResponse")
@JsonPropertyOrder({
	"orignReffId"
	,"accountNumber"
	,"creditorName"
	})
public class ChnlCreditTransferResponsePojo {

	@JsonProperty("NoRef")
	private String orignReffId;

	@JsonProperty("AccountNumber")
	private String accountNumber;
	@JsonProperty("CreditorName")
	private String creditorName;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	
	

	
}
