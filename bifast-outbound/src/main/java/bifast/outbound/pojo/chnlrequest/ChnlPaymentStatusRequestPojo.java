package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChnlPaymentStatusRequestPojo extends ChannelRequestBase{

	@JsonProperty("OriginalNoRef")
	private String orgnlRefId;
	private String orgnlchannelId;
	private String orgnlEndToEndId;
	private String creditorAccountNumber;
	
	public String getOrgnlRefId() {
		return orgnlRefId;
	}
	public void setOrgnlRefId(String orgnlRefId) {
		this.orgnlRefId = orgnlRefId;
	}
	public String getOrgnlchannelId() {
		return orgnlchannelId;
	}
	public void setOrgnlchannelId(String orgnlchannelId) {
		this.orgnlchannelId = orgnlchannelId;
	}
	public String getOrgnlEndToEndId() {
		return orgnlEndToEndId;
	}
	public void setOrgnlEndToEndId(String orgnlEndToEndId) {
		this.orgnlEndToEndId = orgnlEndToEndId;
	}
	public String getCreditorAccountNumber() {
		return creditorAccountNumber;
	}
	public void setCreditorAccountNumber(String creditorAccountNumber) {
		this.creditorAccountNumber = creditorAccountNumber;
	}

	
}
