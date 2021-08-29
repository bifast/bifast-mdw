package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Response")
public class ChannelResponse {

	private String bizMsgId;
	private String responseType;

	private String status;
	private String reason;
	private String addtInfo;
	
	private String proxyType;
	private String proxyValue;
	private String proxyRegistrationId;
	private String proxyDisplayName;
	private String proxyBic;
	
	private String customerAccountNumber;
	private String customerAccountType;
	private String customerAccountName;
	private String customerType;
	private String customerIdNumber;
	private String customerResidentStatus;
	private String customerTownName;
	
	private String creditorAccountType;
	private String creditorName;
	private String creditorId;
	private String creditorType;
	private String creditorResidentStatus;
	private String creditorTownName;
	

	public ChannelResponse () {}


	public String getBizMsgId() {
		return bizMsgId;
	}


	public void setBizMsgId(String bizMsgId) {
		this.bizMsgId = bizMsgId;
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


	public String getResponseType() {
		return responseType;
	}


	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}


	public String getAddtInfo() {
		return addtInfo;
	}


	public void setAddtInfo(String addtInfo) {
		this.addtInfo = addtInfo;
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


	public String getProxyRegistrationId() {
		return proxyRegistrationId;
	}


	public void setProxyRegistrationId(String proxyRegistrationId) {
		this.proxyRegistrationId = proxyRegistrationId;
	}


	public String getProxyDisplayName() {
		return proxyDisplayName;
	}


	public void setProxyDisplayName(String proxyDisplayName) {
		this.proxyDisplayName = proxyDisplayName;
	}


	public String getProxyBic() {
		return proxyBic;
	}


	public void setProxyBic(String proxyBic) {
		this.proxyBic = proxyBic;
	}


	public String getCustomerAccountNumber() {
		return customerAccountNumber;
	}


	public void setCustomerAccountNumber(String customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}


	public String getCustomerAccountType() {
		return customerAccountType;
	}


	public void setCustomerAccountType(String customerAccountType) {
		this.customerAccountType = customerAccountType;
	}


	public String getCustomerAccountName() {
		return customerAccountName;
	}


	public void setCustomerAccountName(String customerAccountName) {
		this.customerAccountName = customerAccountName;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public String getCustomerIdNumber() {
		return customerIdNumber;
	}


	public void setCustomerIdNumber(String customerIdNumber) {
		this.customerIdNumber = customerIdNumber;
	}


	public String getCustomerResidentStatus() {
		return customerResidentStatus;
	}


	public void setCustomerResidentStatus(String customerResidentStatus) {
		this.customerResidentStatus = customerResidentStatus;
	}


	public String getCustomerTownName() {
		return customerTownName;
	}


	public void setCustomerTownName(String customerTownName) {
		this.customerTownName = customerTownName;
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


	public String getCreditorAccountType() {
		return creditorAccountType;
	}


	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
	}


	public String getCreditorId() {
		return creditorId;
	}


	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
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
