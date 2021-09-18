package bifast.outbound.paymentstatus;

public class ChnlPaymentStatusRequestPojo {

	private String channelRefId;
	private String channel;
	private String endToEndId;
	private String msgType;
	private String recptBank;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelRefId() {
		return channelRefId;
	}
	public void setChannelRefId(String channelRefId) {
		this.channelRefId = channelRefId;
	}
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
	public String getRecptBank() {
		return recptBank;
	}
	public void setRecptBank(String recptBank) {
		this.recptBank = recptBank;
	}
	
}
