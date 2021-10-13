package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyResolutionRequest")
public class ChnlProxyResolutionRequestPojo extends ChannelRequestBase {

	@JsonProperty("AccountNumber")
	private String senderAccountNumber;
	@JsonProperty("ProxyType")
	private String proxyType;
	@JsonProperty("ProxyValue")
	private String proxyValue;
	
	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public String getProxyType() {
		return proxyType;
	}
	public void setProxyType(String proxyType) {
		this.proxyType = proxyType;
	}
	public String getProxyValue() {
		return proxyValue;
	}
	public void setProxyValue(String proxyValue) {
		this.proxyValue = proxyValue;
	}
	
	
}
