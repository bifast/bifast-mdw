package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FICreditTransferMessage")
public class ChannelFICreditTransferMessage {

	private ChannelFICreditTransferReq FICreditTransferRequest;
	private ChannelFICreditTransferResponse FICreditTransferResponse;
	
	public ChannelFICreditTransferReq getFICreditTransferRequest() {
		return FICreditTransferRequest;
	}
	public void setFICreditTransferRequest(ChannelFICreditTransferReq fICreditTransferRequest) {
		FICreditTransferRequest = fICreditTransferRequest;
	}
	public ChannelFICreditTransferResponse getFICreditTransferResponse() {
		return FICreditTransferResponse;
	}
	public void setFICreditTransferResponse(ChannelFICreditTransferResponse fICreditTransferResponse) {
		FICreditTransferResponse = fICreditTransferResponse;
	}
	
	
}
