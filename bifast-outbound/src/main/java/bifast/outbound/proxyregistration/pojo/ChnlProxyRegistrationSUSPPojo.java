package bifast.outbound.proxyregistration.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxySuspend")
public class ChnlProxyRegistrationSUSPPojo {
	
	private String channelRefId;
	private String proxyType;
	private String proxyValue;
	private String registrationId;
	private String accountNumber;
	private String accountType;
	private String secondIdType;
	private String secondIdValue;
	
	@JsonCreator
	public ChnlProxyRegistrationSUSPPojo (
		@JsonProperty(value="NoRef", required=true) String channelRefId,
		@JsonProperty(value="ProxyType", required=true) String proxyType,
		@JsonProperty(value="ProxyValue", required=true) String proxyValue,
		@JsonProperty(value="RegistrationId") String registrationId,
		@JsonProperty(value="AccountNumber") String accountNumber,
		@JsonProperty(value="AccountType") String accountType,
		@JsonProperty(value="SecondaryIdType", required=true) String secondIdType,
		@JsonProperty(value="SecondaryIdValue", required=true) String secondIdValue )
	{
		this.channelRefId = channelRefId;
		this.proxyType = proxyType;
		this.proxyValue = proxyValue;
		this.registrationId = registrationId;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.secondIdType = secondIdType;
		this.secondIdValue = secondIdValue;
	}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
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

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

    
}
