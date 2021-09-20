package bifast.outbound.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OutboundParameter {

	@Id
	private String code;
	private String value;
	private String notes;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
