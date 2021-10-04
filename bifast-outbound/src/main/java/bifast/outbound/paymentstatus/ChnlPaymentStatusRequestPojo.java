package bifast.outbound.paymentstatus;

import bifast.outbound.pojo.ChannelRequestBase;

public class ChnlPaymentStatusRequestPojo extends ChannelRequestBase {

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
