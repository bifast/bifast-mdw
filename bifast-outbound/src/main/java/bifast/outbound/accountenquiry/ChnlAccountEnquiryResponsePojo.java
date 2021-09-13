package bifast.outbound.accountenquiry;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AccountEnquiryResponse")
public class ChnlAccountEnquiryResponsePojo {

	private String orignReffId;

	private String status;
	private String reason;
	
	private String creditorAccountType;
	private String creditorName;
	private String creditorId;
	private String creditorType;
	private String creditorResidentStatus;
	private String creditorTownName;
	
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
