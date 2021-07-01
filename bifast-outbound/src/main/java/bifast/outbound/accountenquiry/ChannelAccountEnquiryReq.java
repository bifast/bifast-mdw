package bifast.outbound.accountenquiry;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class ChannelAccountEnquiryReq {

	private String intrnRefId;
	private String channelType;  //M
	private String receivingParticipant; //M
	private BigDecimal amount;  //M
	private String categoryPurpose;  //M
	private String creditorAccountNumber; //M
	
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
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
