package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ProxyRegistrationInquiry")
public class ChnlProxyRegistrationInquiryRequestPojo {

	private String channelRefId;
	private String senderAccountNumber;
	private String scndIdType;
	private String scndIdValue;
	
	public ChnlProxyRegistrationInquiryRequestPojo () {
		
	}

	@JsonCreator
	public ChnlProxyRegistrationInquiryRequestPojo (
		@JsonProperty(value="NoRef", required=true) String channelRefId,
		@JsonProperty(value="SenderAccountNumber", required=true) String senderAccountNumber,
		@JsonProperty(value="ScndIdType", required=true) String scndIdType,
		@JsonProperty(value="ScndIdValue", required=true) String scndIdValue
		)
	{
		this.channelRefId = channelRefId;
		this.senderAccountNumber = senderAccountNumber;
		this.scndIdType = scndIdType;
		this.scndIdValue = scndIdValue;
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

	public String getScndIdType() {
		return scndIdType;
	}

	public void setScndIdType(String scndIdType) {
		this.scndIdType = scndIdType;
	}

	public String getScndIdValue() {
		return scndIdValue;
	}

	public void setScndIdValue(String scndIdValue) {
		this.scndIdValue = scndIdValue;
	}

}
