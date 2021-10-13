package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationRequest")
public class ChnlProxyRegistrationRequestPojo extends ChannelRequestBase{
	
	@JsonProperty("RegistrationType")
	private String registrationType;

	@JsonProperty("ProxyType")
	private String proxyType;
	@JsonProperty("ProxyValue")
	private String proxyValue;
	
	private String registrationId;
	     
	@JsonProperty("DisplayName")
	private String displayName;
	@JsonProperty("AccountNumber")
	private String accountNumber;
	@JsonProperty("AccountType")
	private String accountType;
	@JsonProperty("AccountName")
	private String accountName;
	
	@JsonProperty("SecondaryIdType")
	private String secondIdType;
	@JsonProperty("SecondaryIdValue")
	private String secondIdValue;
    
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("CustomerType")
	private String customerType;
	@JsonProperty("ResidentialStatus")
	private String residentialStatus;
	@JsonProperty("TownName")
	private String townName;
	
	public String getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
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
	public String getSecondIdType() {
		return secondIdType;
	}
	public void setSecondIdType(String secondIdType) {
		this.secondIdType = secondIdType;
	}
	public String getSecondIdValue() {
		return secondIdValue;
	}
	public void setSecondIdValue(String secondIdValue) {
		this.secondIdValue = secondIdValue;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
