package bifast.outbound.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="KC_STATUS_REASON")
public class StatusReason {

	@Id
	@Column(length=20)
	private String statusReasonCode;
	@Column(length=200)
	private String description;
	@Column(length=20)
	private String statusCode;
	
	public String getStatusReasonCode() {
		return statusReasonCode;
	}
	public void setStatusReasonCode(String statusReasonCode) {
		this.statusReasonCode = statusReasonCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
