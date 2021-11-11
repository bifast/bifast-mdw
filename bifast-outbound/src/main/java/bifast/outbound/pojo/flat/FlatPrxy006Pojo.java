package bifast.outbound.pojo.flat;

import java.util.List;

public class FlatPrxy006Pojo extends FlatMessageBase{
	
	private String responseCode;
	private String reasonCode;
	private List<FlatPrxy006AliasPojo> alias;
	

	public List<FlatPrxy006AliasPojo> getAlias() {
		return alias;
	}
	public void setAlias(List<FlatPrxy006AliasPojo> alias) {
		this.alias = alias;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
}
