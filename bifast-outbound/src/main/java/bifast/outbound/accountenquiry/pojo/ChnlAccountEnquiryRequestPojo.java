package bifast.outbound.accountenquiry.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;


@JsonRootName("AccountEnquiryRequest")
public class ChnlAccountEnquiryRequestPojo {

	private String channelRefId;
	
	private String categoryPurpose;
	
	private String senderAccountNumber;
	
	private String amount; 

	private String recptBank; 
	
	private String creditorAccountNumber; 
	
	private String proxyId; 
	private String proxyType; 

	@JsonCreator
	public ChnlAccountEnquiryRequestPojo(
			@JsonProperty(value="NoRef", required=true) String channelRefId,
			@JsonProperty(value="CategoryPurpose", required=true) String categoryPurpose,
			@JsonProperty(value="RecipientBank") String recptBank,
			@JsonProperty(value="SenderAccountNumber", required=true) String senderAccountNumber,
			@JsonProperty(value="Amount", required=true) String amount,
			@JsonProperty("RecipientAccountNumber") String creditorAccountNumber,
			@JsonProperty("ProxyId") String proxyId,
			@JsonProperty("ProxyType") String proxyType) 
	{
		this.channelRefId = channelRefId;
		this.categoryPurpose = categoryPurpose;
		this.recptBank = recptBank;
		this.senderAccountNumber = senderAccountNumber;
		this.amount = amount;
		this.creditorAccountNumber = creditorAccountNumber;
		this.proxyId = proxyId;
		this.proxyType = proxyType;
	}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	
}
