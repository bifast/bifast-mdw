package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.credittransfer.ChnlCreditTransferResponsePojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferResponsePojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationResponse;
import bifast.outbound.proxyregistration.ChnlProxyResolutionResponse;

public class ChannelResponseWrapper {

	@JsonProperty("AccountEnquiryResponse")
	private ChnlAccountEnquiryResponsePojo accountEnquiryResponse;
	
	@JsonProperty("CreditTransferResponse")
	private ChnlCreditTransferResponsePojo creditTransferResponse;
	
	@JsonProperty("FICreditTransferResponse")
	private ChnlFICreditTransferResponsePojo fiCreditTransferResponse;
	
//	@JsonProperty("PaymentStatusRequest")
//	private ChnlPaymentStatusRequestPojo paymentStatusRequest;
	
	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationResponse proxyRegistrationResponse;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionResponse proxyResolutionResponse;

	@JsonProperty("FailureResponse")
	private ChnlFailureResponsePojo faultResponse;

	public ChnlAccountEnquiryResponsePojo getAccountEnquiryResponse() {
		return accountEnquiryResponse;
	}

	public void setAccountEnquiryResponse(ChnlAccountEnquiryResponsePojo accountEnquiryResponse) {
		this.accountEnquiryResponse = accountEnquiryResponse;
	}

	public ChnlCreditTransferResponsePojo getCreditTransferResponse() {
		return creditTransferResponse;
	}

	public void setCreditTransferResponse(ChnlCreditTransferResponsePojo creditTransferResponse) {
		this.creditTransferResponse = creditTransferResponse;
	}

	public ChnlFICreditTransferResponsePojo getFiCreditTransferResponse() {
		return fiCreditTransferResponse;
	}

	public void setFiCreditTransferResponse(ChnlFICreditTransferResponsePojo fiCreditTransferResponse) {
		this.fiCreditTransferResponse = fiCreditTransferResponse;
	}

	public ChnlProxyRegistrationResponse getProxyRegistrationResponse() {
		return proxyRegistrationResponse;
	}

	public void setProxyRegistrationResponse(ChnlProxyRegistrationResponse proxyRegistrationResponse) {
		this.proxyRegistrationResponse = proxyRegistrationResponse;
	}

	public ChnlProxyResolutionResponse getProxyResolutionResponse() {
		return proxyResolutionResponse;
	}

	public void setProxyResolutionResponse(ChnlProxyResolutionResponse proxyResolutionResponse) {
		this.proxyResolutionResponse = proxyResolutionResponse;
	}

	public ChnlFailureResponsePojo getFaultResponse() {
		return faultResponse;
	}

	public void setFaultResponse(ChnlFailureResponsePojo faultResponse) {
		this.faultResponse = faultResponse;
	}
	



}
