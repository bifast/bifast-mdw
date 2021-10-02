package bifast.outbound.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {

	@Id
	private String clientId;
	
	private String clientName;
	
	private String secretKey;
	
	private LocalDateTime createDt;
	
	private LocalDateTime modifDt;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public LocalDateTime getCreateDt() {
		return createDt;
	}

	public void setCreateDt(LocalDateTime createDt) {
		this.createDt = createDt;
	}

	public LocalDateTime getModifDt() {
		return modifDt;
	}

	public void setModifDt(LocalDateTime modifDt) {
		this.modifDt = modifDt;
	}
	
	
	
}
