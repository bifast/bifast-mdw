package bifast.outbound.pojo.flat;

import java.time.LocalDateTime;

public class FlatAdmi002Pojo {
	
	public String refId;
	public String reason;
	public LocalDateTime dateTime;
	public String errorLocation;
	public String reasonDesc;
	public String addtData;
	
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getErrorLocation() {
		return errorLocation;
	}
	public void setErrorLocation(String errorLocation) {
		this.errorLocation = errorLocation;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getAddtData() {
		return addtData;
	}
	public void setAddtData(String addtData) {
		this.addtData = addtData;
	}
	
	
	
}
