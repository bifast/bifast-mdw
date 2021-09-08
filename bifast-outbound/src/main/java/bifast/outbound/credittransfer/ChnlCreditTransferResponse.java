package bifast.outbound.credittransfer;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferResponse")
public class ChnlCreditTransferResponse {

	private String orignReffId;
	private String bizMsgId;

	private String status;
	private String reason;
	private String addtInfo;
	
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
	public String getAddtInfo() {
		return addtInfo;
	}
	public void setAddtInfo(String addtInfo) {
		this.addtInfo = addtInfo;
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
