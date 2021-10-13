package bifast.outbound.pojo.chnlresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferResponse")
@JsonPropertyOrder({"orignReffId"
	,"bizMsgId"
	,"creditorName"
	,"creditorAccountType"
	,"creditorId"
	})
public class ChnlCreditTransferResponsePojo {

	@JsonProperty("noRef")
	private String orignReffId;

	@JsonProperty("bizMsgId")
	private String bizMsgId;

	private String accountNumber;
	private String creditorName;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}
	public String getBizMsgId() {
		return bizMsgId;
	}
	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
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
