package bifast.outbound.proxyregistration;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyResolutionRequest")
public class ChannelProxyResolutionReq {
	
	private String intrnRefId;
	private String channel;
	private String lookupType;
	
	private String proxyType;
	private String proxyValue;
	
	public ChannelProxyResolutionReq() {}
	
	public String getIntrnRefId() {
		return intrnRefId;
	}

	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLookupType() {
		return lookupType;
	}
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
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
