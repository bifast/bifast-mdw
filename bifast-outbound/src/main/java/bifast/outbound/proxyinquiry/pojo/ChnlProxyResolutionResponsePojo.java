package bifast.outbound.proxyinquiry.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
	"noRef"
	,"proxyType"
	,"proxyValue"
	,"registrationId"
	,"displayName"
	,"registerBank"
	,"displayName"
	,"accountNumber"
	,"accountType"
	,"accountName"
	,"customerType"
	,"customerId"
	,"residentialStatus"
	,"townName"
	})
public class ChnlProxyResolutionResponsePojo  {

	@JsonProperty("NoRef")
	private String noRef;
	@JsonProperty("ProxyType")
	private String proxyType;
	@JsonProperty("ProxyValue")
	private String proxyValue;
	@JsonProperty("RegistrationId")
	private String registrationId;
	@JsonProperty("DisplayName")
	private String displayName;
	@JsonProperty("RegisterBank")
	private String registerBank;
	
	@JsonProperty("AccountNumber")
	private String accountNumber;
	@JsonProperty("AccountType")
	private String accountType;
	@JsonProperty("AccountName")
	private String accountName;
	@JsonProperty("CustomerType")
	private String customerType;
	@JsonProperty("CustomerId")
	private String customerId;
	@JsonProperty("ResidentStatus")
	private String residentStatus;
	@JsonProperty("TownName")
	private String townName;
	
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
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
