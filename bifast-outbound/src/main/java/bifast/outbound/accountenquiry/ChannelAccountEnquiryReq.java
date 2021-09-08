package bifast.outbound.accountenquiry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class ChannelAccountEnquiryReq {

	private String channelRefId;
	private String channel;  //M
	private String recipientBank; //M
	private BigDecimal amount;  //M
	private String categoryPurpose;  //M
	private String creditorAccountNumber; //M
	
	public ChannelAccountEnquiryReq() {}
	
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
	public String getRecipientBank() {
		return recipientBank;
	}
	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
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
