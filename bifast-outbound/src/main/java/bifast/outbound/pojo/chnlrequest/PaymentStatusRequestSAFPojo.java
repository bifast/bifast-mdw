package bifast.outbound.pojo.chnlrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentStatusRequestSAFPojo extends ChannelRequestBase{

	@JsonProperty("NoRef")
	private String noRef;
	
	@JsonProperty("OriginalNoRef")
	private String orgnlRefId;
	
	private String orgnlEndToEndId;
	
	private String creditorAccountNumber;

	public String getNoRef() {
		return noRef;
	}

	public void setNoRef(String noRef) {
		this.noRef = noRef;
	}

	public String getOrgnlRefId() {
		return orgnlRefId;
	}

	public void setOrgnlRefId(String orgnlRefId) {
		this.orgnlRefId = orgnlRefId;
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
