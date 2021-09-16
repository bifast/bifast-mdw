package bifast.outbound.report;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("MessageRequest")
public class RequestPojo {

	private String msgType;
	private String bizMsgIdr;
	private String orignBank;
	private String endToEndId;
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getBizMsgIdr() {
		return bizMsgIdr;
	}
	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}
	public String getOrignBank() {
		return orignBank;
	}
	public void setOrignBank(String orignBank) {
		this.orignBank = orignBank;
	}
	public String getEndToEndId() {
		return endToEndId;
	}
	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}
	
	
}
