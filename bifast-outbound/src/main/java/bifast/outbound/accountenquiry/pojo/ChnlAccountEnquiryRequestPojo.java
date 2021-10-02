package bifast.outbound.accountenquiry.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class ChnlAccountEnquiryRequestPojo {

	@JsonProperty("TransactionId")
	private String channelRefId;

	@JsonProperty("Channel")
	private String channel;  //M
	
	@JsonProperty("RecipientBank")
	private String recptBank; //M
	
	@JsonProperty("Amount")
	private BigDecimal amount;  //M
	
	@JsonProperty("CategoryPurpose")
	private String categoryPurpose;  //M
	@JsonProperty("AccountNumber")
	private String creditorAccountNumber; //M
	
	public ChnlAccountEnquiryRequestPojo() {}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRecptBank() {
		return recptBank;
	}

	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCategoryPurpose() {
		return categoryPurpose;
	}

	public void setCategoryPurpose(String categoryPurpose) {
		this.categoryPurpose = categoryPurpose;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}
	


	
}
