package bifast.outbound.pojo.chnlresponse;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationResponse")
public class ChnlProxyRegistrationResponsePojo {

	private String noRef;
	private String registrationId;
	private String registrationType;
	
	public String getNoRef() {
		return noRef;
	}
	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	public String getRegistrationType() {
		return registrationType;
	}
	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}
	

}
