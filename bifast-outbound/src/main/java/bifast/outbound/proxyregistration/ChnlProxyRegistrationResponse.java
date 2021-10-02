package bifast.outbound.proxyregistration;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationResponse")
public class ChnlProxyRegistrationResponse {

	private String intrnRefId;
	private String registrationId;

	private String status;
	private String reason;
	
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
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
	


}
