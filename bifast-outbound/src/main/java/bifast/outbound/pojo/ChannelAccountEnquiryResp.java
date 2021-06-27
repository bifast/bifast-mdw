package bifast.outbound.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryResponse")
public class ChannelAccountEnquiryResp {

	private String channelType;  
	private String receivingParticipant; 
	private BigDecimal amount;  
	private String categoryPurpose;  
	private String creditorAccountNumber; 
	
	private String status;
	private String reason;
	
	private String creditorName;
	private String creditorAccountType;
	private String creditorType;
	private String creditorIdNumber;
	private String creditorResidentStatus;
	private String creditorTownName;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getCreditorAccountType() {
		return creditorAccountType;
	}
	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
	}
	public String getCreditorType() {
		return creditorType;
	}
	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}
	public String getCreditorIdNumber() {
		return creditorIdNumber;
	}
	public void setCreditorIdNumber(String creditorIdNumber) {
		this.creditorIdNumber = creditorIdNumber;
	}
	public String getCreditorResidentStatus() {
		return creditorResidentStatus;
	}
	public void setCreditorResidentStatus(String creditorResidentStatus) {
		this.creditorResidentStatus = creditorResidentStatus;
	}
	public String getCreditorTownName() {
		return creditorTownName;
	}
	public void setCreditorTownName(String creditorTownName) {
		this.creditorTownName = creditorTownName;
	}
	
	
}
