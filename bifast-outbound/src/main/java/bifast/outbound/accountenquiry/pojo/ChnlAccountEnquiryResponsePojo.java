package bifast.outbound.accountenquiry.pojo;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryResponse")
@JsonPropertyOrder({"orignReffId"
					,"creditorAccountNumber"
					,"creditorAccountType"
					,"recipientBank"
					,"proxyId"
					,"prixyType"
					,"creditorName"
					,"creditorId"
					,"creditorType"
					,"residentialStatus"
					,"townName"
					})
public class ChnlAccountEnquiryResponsePojo {

	@JsonProperty("NoRef")
	private String orignReffId;

	@JsonProperty("AccountNumber")
	private String creditorAccountNumber;
	
	@JsonProperty("RecipientBank")
	private String recipientBank;
	
	@JsonProperty("ProxyId")
	private String proxyId; 
	@JsonProperty("ProxyType")
	private String proxyType; 

	@JsonProperty("AccountType")
	private String creditorAccountType;

	@JsonProperty("CreditorName")
	private String creditorName;
	
	@JsonProperty("CreditorId")
	private String creditorId;
	
	@JsonProperty("CreditorType")
	private String creditorType;
	
	@JsonProperty("ResidentStatus")
	private String creditorResidentStatus;
	
	@JsonProperty("TownName")
	private String creditorTownName;

	public String getOrignReffId() {
		return orignReffId;
	}

	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}

	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}

	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	public String getRecipientBank() {
		return recipientBank;
	}

	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}

	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getProxyType() {
		return proxyType;
	}

	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}

	public String getCreditorAccountType() {
		return Optional.ofNullable(creditorAccountType).orElse("");
//		return creditorAccountType;
	}

	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
	}

	public String getCreditorName() {
		return Optional.ofNullable(creditorName).orElse("");
//		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorId() {
		return Optional.ofNullable(creditorId).orElse("");
//		return creditorId;
	}

	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}

	public String getCreditorType() {
		return Optional.ofNullable(creditorType).orElse("");
//		return creditorType;
	}

	public void setCreditorType(String creditorType) {
		this.creditorType = creditorType;
	}

	public String getCreditorResidentStatus() {
		return Optional.ofNullable(creditorResidentStatus).orElse("");
//		return creditorResidentStatus;
	}

	public void setCreditorResidentStatus(String creditorResidentStatus) {
		this.creditorResidentStatus = creditorResidentStatus;
	}

	public String getCreditorTownName() {
		return Optional.ofNullable(creditorTownName).orElse("");
//		return creditorTownName;
	}

	public void setCreditorTownName(String creditorTownName) {
		this.creditorTownName = creditorTownName;
	}
	
	
}
