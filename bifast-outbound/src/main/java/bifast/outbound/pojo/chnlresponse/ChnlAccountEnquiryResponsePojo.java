package bifast.outbound.pojo.chnlresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryResponse")
@JsonPropertyOrder({"orignReffId"
					,"creditorAccountNumber"
					,"creditorAccountType"
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
		return creditorAccountType;
	}

	public void setCreditorAccountType(String creditorAccountType) {
		this.creditorAccountType = creditorAccountType;
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
