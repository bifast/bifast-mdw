package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;

@JsonRootName("OutboundMessage")
@JsonPropertyOrder({"accountEnquiryRequest", 
					"creditTransferRequest",
					"fICreditTransferRequest", 
					"paymentStatusRequest", 
					"reverseCreditTransferRequest",
					"reverseProxyRegistrationRequest",
					"response",
					"rejection"})
public class ChannelResponseMessage {

	@JsonProperty("AccountEnquiryRequest")
	private ChannelAccountEnquiryReq accountEnquiryRequest;
	@JsonProperty("CreditTransferRequest")
	private ChannelCreditTransferRequest creditTransferRequest ;
	@JsonProperty("FICreditTransferRequest")
	private ChannelFICreditTransferReq fICreditTransferRequest ;
	@JsonProperty("PaymentStatusRequest")
	private ChannelPaymentStatusRequest paymentStatusRequest;
	@JsonProperty("ReverseCreditTransferRequest")
	private ChannelReverseCreditTransferRequest reverseCreditTransferRequest;
	@JsonProperty("ReverseProxyRegistrationRequest")
	private ChannelProxyRegistrationReq reverseProxyRegistrationRequest;
	
	@JsonProperty("Response")
	private ChannelResponse response;
	
	@JsonProperty("Rejection")
	private ChannelReject rejection;
	
	public ChannelAccountEnquiryReq getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}
	public void setAccountEnquiryRequest(ChannelAccountEnquiryReq accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}
	public ChannelCreditTransferRequest getCreditTransferRequest() {
		return creditTransferRequest;
	}
	public void setCreditTransferRequest(ChannelCreditTransferRequest creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}
	public ChannelFICreditTransferReq getfICreditTransferRequest() {
		return fICreditTransferRequest;
	}
	public void setfICreditTransferRequest(ChannelFICreditTransferReq fICreditTransferRequest) {
		this.fICreditTransferRequest = fICreditTransferRequest;
	}
	public ChannelPaymentStatusRequest getPaymentStatusRequest() {
		return paymentStatusRequest;
	}
	public void setPaymentStatusRequest(ChannelPaymentStatusRequest paymentStatusRequest) {
		this.paymentStatusRequest = paymentStatusRequest;
	}
	public ChannelReverseCreditTransferRequest getReverseCreditTransferRequest() {
		return reverseCreditTransferRequest;
	}
	public void setReverseCreditTransferRequest(ChannelReverseCreditTransferRequest reverseCreditTransferRequest) {
		this.reverseCreditTransferRequest = reverseCreditTransferRequest;
	}
	public ChannelResponse getResponse() {
		return response;
	}
	public void setResponse(ChannelResponse response) {
		this.response = response;
	}
	public ChannelReject getRejection() {
		return rejection;
	}
	public void setRejection(ChannelReject rejection) {
		this.rejection = rejection;
	}
	public ChannelProxyRegistrationReq getReverseProxyRegistrationRequest() {
		return reverseProxyRegistrationRequest;
	}
	public void setReverseProxyRegistrationRequest(ChannelProxyRegistrationReq reverseProxyRegistrationRequest) {
		this.reverseProxyRegistrationRequest = reverseProxyRegistrationRequest;
	}

}
