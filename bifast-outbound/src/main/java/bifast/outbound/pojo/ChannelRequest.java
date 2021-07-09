package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.paymentstatus.ChannelPaymentStatusRequest;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;


//@JsonProperty("AccountEnquiryRequest")
public class ChannelRequest {

	@JsonProperty("AccountEnquiryRequest")
	private ChannelAccountEnquiryReq accountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChannelCreditTransferRequest creditTransferRequest;
	
	@JsonProperty("FICreditTransferRequest")
	private ChannelFICreditTransferReq fiCreditTransferRequest;
	
	@JsonProperty("PaymentStatusRequest")
	private ChannelPaymentStatusRequest paymentStatusRequest;
	
	@JsonProperty("ReverseCreditTransferRequest")
	private ChannelReverseCreditTransferRequest reverseCreditTransferRequest;

	@JsonProperty("reverseProxyRegistrationRequest")
	private ChannelProxyRegistrationReq reverseProxyRegistrationRequest;
	
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

	public ChannelFICreditTransferReq getFiCreditTransferRequest() {
		return fiCreditTransferRequest;
	}

	public void setFiCreditTransferRequest(ChannelFICreditTransferReq fiCreditTransferRequest) {
		this.fiCreditTransferRequest = fiCreditTransferRequest;
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

	public ChannelProxyRegistrationReq getReverseProxyRegistrationRequest() {
		return reverseProxyRegistrationRequest;
	}

	public void setReverseProxyRegistrationRequest(ChannelProxyRegistrationReq reverseProxyRegistrationRequest) {
		this.reverseProxyRegistrationRequest = reverseProxyRegistrationRequest;
	}


}
