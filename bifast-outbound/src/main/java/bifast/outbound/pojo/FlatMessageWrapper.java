package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlatMessageWrapper {

	@JsonProperty("Pacs002")
	private FlatPacs002Pojo flatPacs002;

	@JsonProperty("Admi002")
	private FlatAdmi002Pojo flatAdmi002;

	@JsonProperty("Prxy004")
	private FlatPrxy004Pojo flatPrxy004;

	@JsonProperty("Prxy006")
	private FlatPrxy006Pojo flatPrxy006;

	@JsonProperty("Pacs008")
	private FlatPacs008Pojo flatPacs008;

	@JsonProperty("FailureResponse")
	private ChnlFailureResponsePojo faultResponse;

	public FlatPacs002Pojo getFlatPacs002() {
		return flatPacs002;
	}

	public void setFlatPacs002(FlatPacs002Pojo flatPacs002) {
		this.flatPacs002 = flatPacs002;
	}

	public FlatAdmi002Pojo getFlatAdmi002() {
		return flatAdmi002;
	}

	public void setFlatAdmi002(FlatAdmi002Pojo flatAdmi002) {
		this.flatAdmi002 = flatAdmi002;
	}

	public FlatPrxy004Pojo getFlatPrxy004() {
		return flatPrxy004;
	}

	public void setFlatPrxy004(FlatPrxy004Pojo flatPrxy004) {
		this.flatPrxy004 = flatPrxy004;
	}

	public FlatPrxy006Pojo getFlatPrxy006() {
		return flatPrxy006;
	}

	public void setFlatPrxy006(FlatPrxy006Pojo flatPrxy006) {
		this.flatPrxy006 = flatPrxy006;
	}

	public FlatPacs008Pojo getFlatPacs008() {
		return flatPacs008;
	}

	public void setFlatPacs008(FlatPacs008Pojo flatPacs008) {
		this.flatPacs008 = flatPacs008;
	}

	public ChnlFailureResponsePojo getFaultResponse() {
		return faultResponse;
	}

	public void setFaultResponse(ChnlFailureResponsePojo faultResponse) {
		this.faultResponse = faultResponse;
	}

	
	
}
