package bifast.outbound.history;

public class HistoryRetrievalRequest {

	private String msgType;
	private String chnlRefId;
	private String bizMsgIdr;
	
	public HistoryRetrievalRequest() {};
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getChnlRefId() {
		return chnlRefId;
	}
	public void setChnlRefId(String chnlRefId) {
		this.chnlRefId = chnlRefId;
	}
	public String getBizMsgIdr() {
		return bizMsgIdr;
	}
	public void setBizMsgIdr(String bizMsgIdr) {
		this.bizMsgIdr = bizMsgIdr;
	}
	
	
}
