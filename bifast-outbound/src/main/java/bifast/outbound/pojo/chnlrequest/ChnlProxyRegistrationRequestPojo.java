package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationRequest")
public class ChnlProxyRegistrationRequestPojo {
	
	private String channelRefId;
	private String registrationType;
	private String proxyType;
	private String proxyValue;
	private String displayName;
	private String accountNumber;
	private String accountType;
	private String accountName;
	private String secondIdType;
	private String secondIdValue;
	private String customerId;
	private String customerType;
	private String residentialStatus;
	private String townName;
	private String registrationId;
	
	@JsonCreator
	public ChnlProxyRegistrationRequestPojo (
		@JsonProperty(value="NoRef", required=true) String channelRefId,
		@JsonProperty(value="RegistrationType", required=true) String registrationType,
		@JsonProperty(value="ProxyType", required=true) String proxyType,
		@JsonProperty(value="ProxyValue", required=true) String proxyValue,
		@JsonProperty(value="DisplayName") String displayName,
		@JsonProperty(value="AccountNumber") String accountNumber,
		@JsonProperty(value="AccountType") String accountType,
		@JsonProperty(value="AccountName") String accountName,
		@JsonProperty(value="SecondaryIdType", required=true) String secondIdType,
		@JsonProperty(value="SecondaryIdValue", required=true) String secondIdValue,
		@JsonProperty(value="CustomerId") String customerId,
		@JsonProperty(value="CustomerType") String customerType,
		@JsonProperty(value="ResidentialStatus") String residentialStatus,
		@JsonProperty(value="TownName") String townName )
	{
		this.channelRefId = channelRefId;
		this.registrationType = registrationType;
		this.proxyType = proxyType;
		this.proxyValue = proxyValue;
		this.displayName = displayName;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.accountName = accountName;
		this.secondIdType = secondIdType;
		this.secondIdValue = secondIdValue;
		this.customerId = customerId;
		this.customerType = customerType;
		this.residentialStatus = residentialStatus;
		this.townName = townName;
	}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

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

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	

    
}
