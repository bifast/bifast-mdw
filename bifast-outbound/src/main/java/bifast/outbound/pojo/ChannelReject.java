package bifast.outbound.pojo;

public class ChannelReject {

	private String reference;
	private String reason;
	private String location;
	private String description;
	private String additionalData;
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
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
