package bifast.outbound.pojo.flat;

import java.time.LocalDateTime;

public class FlatPrxy004Pojo extends FlatMessageBase{
	
	private String msgId;
	private LocalDateTime creDtTm;
	
	private String OrgnlMsgId;
	private String OrgnlMsgNmId;
	private LocalDateTime OrgnlCreDtTm;
	private String orgnlId;
	
	private String proxyType;
	private String proxyValue;
	
	private String responseCode;
	private String reasonCode;
	
	private String registrationId;
	private String displayName;
	private String registerBank;
	private String accountNumber;
	private String accountType;
	private String accountName;
	private String customerType;
	private String customerId;
	private String residentialStatus;
	private String townName;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public LocalDateTime getCreDtTm() {
		return creDtTm;
	}
	public void setCreDtTm(LocalDateTime creDtTm) {
		this.creDtTm = creDtTm;
	}
	public String getOrgnlMsgId() {
		return OrgnlMsgId;
	}
	public void setOrgnlMsgId(String orgnlMsgId) {
		OrgnlMsgId = orgnlMsgId;
	}
	public String getOrgnlMsgNmId() {
		return OrgnlMsgNmId;
	}
	public void setOrgnlMsgNmId(String orgnlMsgNmId) {
		OrgnlMsgNmId = orgnlMsgNmId;
	}
	public LocalDateTime getOrgnlCreDtTm() {
		return OrgnlCreDtTm;
	}
	public void setOrgnlCreDtTm(LocalDateTime orgnlCreDtTm) {
		OrgnlCreDtTm = orgnlCreDtTm;
	}
	public String getOrgnlId() {
		return orgnlId;
	}
	public void setOrgnlId(String orgnlId) {
		this.orgnlId = orgnlId;
	}
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getProxyValue() {
		return proxyValue;
	}
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getRegisterBank() {
		return registerBank;
	}
	public void setRegisterBank(String registerBank) {
		this.registerBank = registerBank;
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public String getResidentialStatus() {
		return residentialStatus;
	}
	public void setResidentialStatus(String residentialStatus) {
		this.residentialStatus = residentialStatus;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	

	

}
