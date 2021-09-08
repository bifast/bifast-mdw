package bifast.outbound.proxyregistration;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyResolutionResponse")
public class ChnlProxyResolutionResponse {

	private String orignReffId;
	private String bizMsgId;

	private String status;
	private String reason;
	
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
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}
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
	
	
}
