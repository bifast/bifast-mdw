package bifast.corebank.service;

import java.math.BigDecimal;

public class CorebankCreditTransferPreAdviceInput {

	private String senderBank;
	private String trfType;
	private BigDecimal amount;
	private String purpose;
	private String accountNumber;
	private String creditorName;
	private String creditorType;
	private String creditorId;
	private String creditorResidentialStatus;
	private String creditorTown;
	private String status;
	private String reason;
	private String additionalInfo;
	
	public String getSenderBank() {
		return senderBank;
	}
	public void setSenderBank(String senderBank) {
		this.senderBank = senderBank;
	}
	public String getTrfType() {
		return trfType;
	}
	public void setTrfType(String trfType) {
		this.trfType = trfType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	public String getCreditorType() {
		return creditorType;
	}
	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}
	public String getCreditorId() {
		return creditorId;
	}
	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}
	public String getCreditorResidentialStatus() {
		return creditorResidentialStatus;
	}
	public void setCreditorResidentialStatus(String creditorResidentialStatus) {
		this.creditorResidentialStatus = creditorResidentialStatus;
	}
	public String getCreditorTown() {
		return creditorTown;
	}
	public void setCreditorTown(String creditorTown) {
		this.creditorTown = creditorTown;
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
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	

	
}
