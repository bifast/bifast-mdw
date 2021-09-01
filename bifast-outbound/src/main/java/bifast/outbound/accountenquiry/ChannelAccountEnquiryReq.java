package bifast.outbound.accountenquiry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class ChannelAccountEnquiryReq {

	private String intrnRefId;
	private String channelName;  //M
	private String receivingParticipant; //M
	private BigDecimal amount;  //M
	private String categoryPurpose;  //M
	private String creditorAccountNumber; //M
	
	public ChannelAccountEnquiryReq() {}
	
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getReceivingParticipant() {
		return receivingParticipant;
	}
	public void setReceivingParticipant(String receivingParticipant) {
		this.receivingParticipant = receivingParticipant;
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
