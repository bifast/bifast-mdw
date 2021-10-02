package bifast.outbound.accountenquiry.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryResponse")
@JsonPropertyOrder({"orignReffId"
					,"status"
					,"reason"
					,"creditorAccountNumber"
					,"creditorAccountType"
					,"creditorId"
					})
public class ChnlAccountEnquiryResponsePojo {

	@JsonProperty("TransactionId")
	private String orignReffId;

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Reason")
	private String reason;

	@JsonProperty("AccountNumber")
	private String creditorAccountNumber;

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
	
//	private FaultResponse fault;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
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
	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}
	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
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
