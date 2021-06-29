package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("CreditTransferMessage")
public class ChannelCreditTransferMessage {

	private ChannelCreditTransferRequest creditTransferRequest;
	private ChannelCreditTransferResponse creditTransferResponse;
	
	public ChannelCreditTransferRequest getCreditTransferRequest() {
		return creditTransferRequest;
	}
	public void setCreditTransferRequest(ChannelCreditTransferRequest creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}
	public ChannelCreditTransferResponse getCreditTransferResponse() {
		return creditTransferResponse;
	}
	public void setCreditTransferResponse(ChannelCreditTransferResponse creditTransferResponse) {
		this.creditTransferResponse = creditTransferResponse;
	}
	
	
}
