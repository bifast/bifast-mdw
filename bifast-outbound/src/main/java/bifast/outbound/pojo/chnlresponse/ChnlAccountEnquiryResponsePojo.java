package bifast.outbound.pojo.chnlresponse;

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

	@JsonProperty("noRef")
	private String orignReffId;

	@JsonProperty("accountNumber")
	private String creditorAccountNumber;

	@JsonProperty("accountType")
	private String creditorAccountType;

	private String creditorName;
	
	private String creditorId;
	
	private String creditorType;
	
	@JsonProperty("residentStatus")
	private String creditorResidentStatus;
	
	@JsonProperty("townName")
	private String creditorTownName;
	
//	private FaultResponse fault;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
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
