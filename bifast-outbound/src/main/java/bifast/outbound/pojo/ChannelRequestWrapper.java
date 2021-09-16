package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo;

public class ChannelRequestWrapper {

	@JsonProperty("AccountEnquiryRequest")
	private ChnlAccountEnquiryRequestPojo accountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo creditTransferRequest;
	
	@JsonProperty("FICreditTransferRequest")
	private ChnlFICreditTransferRequestPojo fiCreditTransferRequest;
	
	@JsonProperty("PaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo paymentStatusRequest;
	
	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo proxyRegistrationReq;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionRequestPojo proxyResolutionReq;

	public ChnlAccountEnquiryRequestPojo getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}

	public void setAccountEnquiryRequest(ChnlAccountEnquiryRequestPojo accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}

	public ChnlCreditTransferRequestPojo getCreditTransferRequest() {
		return creditTransferRequest;
	}

	public void setCreditTransferRequest(ChnlCreditTransferRequestPojo creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}

	public ChnlFICreditTransferRequestPojo getFiCreditTransferRequest() {
		return fiCreditTransferRequest;
	}

	public void setFiCreditTransferRequest(ChnlFICreditTransferRequestPojo fiCreditTransferRequest) {
		this.fiCreditTransferRequest = fiCreditTransferRequest;
	}

	public ChnlPaymentStatusRequestPojo getPaymentStatusRequest() {
		return paymentStatusRequest;
	}

	public void setPaymentStatusRequest(ChnlPaymentStatusRequestPojo paymentStatusRequest) {
		this.paymentStatusRequest = paymentStatusRequest;
	}

	public ChnlProxyRegistrationRequestPojo getProxyRegistrationReq() {
		return proxyRegistrationReq;
	}

	public void setProxyRegistrationReq(ChnlProxyRegistrationRequestPojo proxyRegistrationReq) {
		this.proxyRegistrationReq = proxyRegistrationReq;
	}

	public ChnlProxyResolutionRequestPojo getProxyResolutionReq() {
		return proxyResolutionReq;
	}

	public void setProxyResolutionReq(ChnlProxyResolutionRequestPojo proxyResolutionReq) {
		this.proxyResolutionReq = proxyResolutionReq;
	}



}
