package bifast.admin.pojo;

import java.util.List;

public class CbCustomerInfoResponsePojo {
	
	private String transactionId;
	private String dateTime;
	private String merchantType;
	private String terminalId;
	private String noRef;
	private String status;
	private String reason;

	public List<String> emailAddressList;
	public List<String> phoneNumberList;
	private String accountNumber;
	public String accountType;

	public String customerName;
	public String customerType;
	public String customerId;
	public String customerIdType;
	public String residentStatus;
	public String townName;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
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
	public List<String> getEmailAddressList() {
		return emailAddressList;
	}
	public void setEmailAddressList(List<String> emailAddressList) {
		this.emailAddressList = emailAddressList;
	}
	public List<String> getPhoneNumberList() {
		return phoneNumberList;
	}
	public void setPhoneNumberList(List<String> phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerIdType() {
		return customerIdType;
	}
	public void setCustomerIdType(String customerIdType) {
		this.customerIdType = customerIdType;
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
	
	

}
