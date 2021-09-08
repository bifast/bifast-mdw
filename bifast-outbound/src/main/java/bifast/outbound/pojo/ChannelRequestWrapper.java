package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.accountenquiry.ChannelAccountEnquiryReq;
import bifast.outbound.credittransfer.ChannelCreditTransferRequest;
import bifast.outbound.ficredittransfer.ChannelFICreditTransferReq;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequest;
import bifast.outbound.proxyregistration.ChannelProxyRegistrationReq;
import bifast.outbound.proxyregistration.ChannelProxyResolutionReq;
import bifast.outbound.reversect.ChannelReverseCreditTransferRequest;

public class ChannelRequestWrapper {

	@JsonProperty("AccountEnquiryRequest")
	private ChannelAccountEnquiryReq accountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChannelCreditTransferRequest creditTransferRequest;
	
	@JsonProperty("FICreditTransferRequest")
	private ChannelFICreditTransferReq fiCreditTransferRequest;
	
	@JsonProperty("PaymentStatusRequest")
	private ChnlPaymentStatusRequest paymentStatusRequest;
	
	@JsonProperty("ReverseCreditTransferRequest")
	private ChannelReverseCreditTransferRequest reverseCreditTransferRequest;

	@JsonProperty("ProxyRegistrationRequest")
	private ChannelProxyRegistrationReq proxyRegistrationReq;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChannelProxyResolutionReq proxyResolutionReq;

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

	public ChnlPaymentStatusRequest getPaymentStatusRequest() {
		return paymentStatusRequest;
	}

	public void setPaymentStatusRequest(ChnlPaymentStatusRequest paymentStatusRequest) {
		this.paymentStatusRequest = paymentStatusRequest;
	}

	public ChannelReverseCreditTransferRequest getReverseCreditTransferRequest() {
		return reverseCreditTransferRequest;
	}

	public void setReverseCreditTransferRequest(ChannelReverseCreditTransferRequest reverseCreditTransferRequest) {
		this.reverseCreditTransferRequest = reverseCreditTransferRequest;
	}

	public ChannelProxyRegistrationReq getProxyRegistrationReq() {
		return proxyRegistrationReq;
	}

	public void setProxyRegistrationReq(ChannelProxyRegistrationReq proxyRegistrationReq) {
		this.proxyRegistrationReq = proxyRegistrationReq;
	}

	public ChannelProxyResolutionReq getProxyResolutionReq() {
		return proxyResolutionReq;
	}

	public void setProxyResolutionReq(ChannelProxyResolutionReq proxyResolutionReq) {
		this.proxyResolutionReq = proxyResolutionReq;
	}



}
