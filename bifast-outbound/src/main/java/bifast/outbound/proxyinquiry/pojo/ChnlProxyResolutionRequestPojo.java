package bifast.outbound.proxyinquiry.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyResolutionRequest")
public class ChnlProxyResolutionRequestPojo {

	private String channelRefId;
	private String senderAccountNumber;
	private String lookUpType;
	private String proxyType;
	private String proxyValue;
	
	public ChnlProxyResolutionRequestPojo () {
		
	}

	@JsonCreator
	public ChnlProxyResolutionRequestPojo (
		@JsonProperty(value="NoRef", required=true) String channelRefId,
		@JsonProperty(value="SenderAccountNumber", required=true) String senderAccountNumber,
		@JsonProperty(value="LookUpType") String lookUpType,
		@JsonProperty(value="ProxyType", required=true) String proxyType,
		@JsonProperty(value="ProxyValue", required=true) String proxyValue
		)
	{
		this.channelRefId = channelRefId;
		this.senderAccountNumber = senderAccountNumber;
		this.lookUpType = lookUpType;
		this.proxyType = proxyType;
		this.proxyValue = proxyValue;
	}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getLookUpType() {
		return lookUpType;
	}

	public void setLookUpType(String lookUpType) {
		this.lookUpType = lookUpType;
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
