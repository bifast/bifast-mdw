package bifast.outbound.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name = "KC_MST_REJECT")
public class RejectCode {
	
	@Id
	private String rejectCode;
	private String description;
	
	public String getRejectCode() {
		return rejectCode;
	}
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
