package bifast.outbound.paymentstatus;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("PaymentStatusRequest")
public class ChnlPaymentStatusRequest {

	private String orignReffId;
	private String requestReffId;
	private String recptBank;
	
	public String getOrignReffId() {
		return orignReffId;
	}
	public void setOrignReffId(String orignReffId) {
		this.orignReffId = orignReffId;
	}
	public String getRequestReffId() {
		return requestReffId;
	}
	public void setRequestReffId(String requestReffId) {
		this.requestReffId = requestReffId;
	}
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	



}
