package komi.control.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="m_systemparam")
public class Parameter {

	@Id
	private Integer id;
	private String paramname;
	private String paramvalua;
	private String status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String getParamvalua() {
		return paramvalua;
	}
	public void setParamvalua(String paramvalua) {
		this.paramvalua = paramvalua;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
}
