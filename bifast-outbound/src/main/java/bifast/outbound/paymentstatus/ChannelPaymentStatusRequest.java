package bifast.outbound.paymentstatus;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("PaymentStatusRequest")
public class ChannelPaymentStatusRequest {

	private String intrnRefId;
	private String endToEndId;
	private String recptBank;
	
	public String getIntrnRefId() {
		return intrnRefId;
	}
	public void setIntrnRefId(String intrnRefId) {
		this.intrnRefId = intrnRefId;
	}
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}



}
