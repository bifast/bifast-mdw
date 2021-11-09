package bifast.outbound.paymentstatus.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("PaymentStatusRequest")
public class ChnlPaymentStatusRequestPojo {

	private String channelRefId;
	private String orgnlRefId;

	public ChnlPaymentStatusRequestPojo ( 
		@JsonProperty(value="NoRef", required=true) String channelRefId,
		@JsonProperty(value="OriginalNoRef", required=true) String orgnlRefId)
	{
		this.channelRefId = channelRefId;
		this.orgnlRefId = orgnlRefId;	
	}

	public String getChannelRefId() {
		return channelRefId;
	}

	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}

	public String getOrgnlRefId() {
		return orgnlRefId;
	}

	public void setOrgnlRefId(String orgnlRefId) {
		this.orgnlRefId = orgnlRefId;
	}
	
	
}
