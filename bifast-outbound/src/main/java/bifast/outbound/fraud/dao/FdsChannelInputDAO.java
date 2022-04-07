package bifast.outbound.fraud.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FdsChannelInputDAO {

	@JsonProperty("Id")
	private String id;

	public FdsChannelInputDAO (String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
