package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FaultResponse")
public class ChannelFaultResponse {
	
	private String channelRefId;
	private String biReference;
	private String reason;
	private String location;
	private String description;
	private String additionalData;
	
	public String getChannelRefId() {
		return channelRefId;
	}
	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}
	public String getBiReference() {
		return biReference;
	}
	public void setBiReference(String biReference) {
		this.biReference = biReference;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}
	

}
