package bifast.corebank.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryRequest")
public class AccountEnquiryResponse {

	private String transactionId;
	private String creditorStatus;  // ACTIVE, INACTIVE, PENDING
	private String accountNumber;
	private String accountType;
	private String creditorName;
	private String creditorId;
	private String creditorType;
	private String residentStatus;
	private String townName;
	private String additionalInfo;
	
	
	public AccountEnquiryResponse () {}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCreditorStatus() {
		return creditorStatus;
	}

	public void setCreditorStatus(String creditorStatus) {
		this.creditorStatus = creditorStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorId() {
		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorType() {
		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	
}
