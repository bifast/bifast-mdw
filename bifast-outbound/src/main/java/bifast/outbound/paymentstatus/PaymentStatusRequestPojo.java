package bifast.outbound.paymentstatus;

public class PaymentStatusRequestPojo {

	private String endToEndId;
	private String msgType;
	
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	
}
