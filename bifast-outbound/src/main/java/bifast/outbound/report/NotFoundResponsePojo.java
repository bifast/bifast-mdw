package bifast.outbound.report;

public class NotFoundResponsePojo {

	private String msgType;
	private String bizMsgIdr;
	private String orignBank;
	private String endToEndId;

	public NotFoundResponsePojo () {}

	public NotFoundResponsePojo (RequestPojo messageRequest) {
		this.msgType = messageRequest.getMsgType();
		this.bizMsgIdr = messageRequest.getBizMsgIdr();
		this.orignBank = messageRequest.getOrignBank();
		this.endToEndId = messageRequest.getEndToEndId();
	}

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
