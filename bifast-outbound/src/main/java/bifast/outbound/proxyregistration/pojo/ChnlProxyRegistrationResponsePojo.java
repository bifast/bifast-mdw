package bifast.outbound.proxyregistration.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationResponse")
@JsonPropertyOrder({
	"noRef"
	,"registrationType"
	,"registrationId"
	})
public class ChnlProxyRegistrationResponsePojo {

	@JsonProperty("NoRef")
	private String noRef;
	@JsonProperty("RegistrationId")
	private String registrationId;
	@JsonProperty("RegistrationType")
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
