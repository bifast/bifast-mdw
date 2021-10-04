package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.corebank.CBDebitInstructionRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyResolutionRequestPojo;

public class RequestMessageWrapper {

	@JsonProperty("DebitRequest")
	private CBDebitInstructionRequestPojo debitRequest;
	
	@JsonProperty("AccountEnquiryRequest")
	private ChnlAccountEnquiryRequestPojo chnlAccountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo chnlCreditTransferRequest;

	@JsonProperty("BICreditTransferRequest")
	private BusinessMessage creditTransferRequest;

	@JsonProperty("ChnlPaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest;

	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo proxyRegistrationReq;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionRequestPojo proxyResolutionReq;

	public CBDebitInstructionRequestPojo getDebitRequest() {
		return debitRequest;
	}

	public void setDebitRequest(CBDebitInstructionRequestPojo debitRequest) {
		this.debitRequest = debitRequest;
	}

	public ChnlAccountEnquiryRequestPojo getChnlAccountEnquiryRequest() {
		return chnlAccountEnquiryRequest;
	}

	public void setChnlAccountEnquiryRequest(ChnlAccountEnquiryRequestPojo chnlAccountEnquiryRequest) {
		this.chnlAccountEnquiryRequest = chnlAccountEnquiryRequest;
	}

	public ChnlCreditTransferRequestPojo getChnlCreditTransferRequest() {
		return chnlCreditTransferRequest;
	}

	public void setChnlCreditTransferRequest(ChnlCreditTransferRequestPojo chnlCreditTransferRequest) {
		this.chnlCreditTransferRequest = chnlCreditTransferRequest;
	}

	public BusinessMessage getCreditTransferRequest() {
		return creditTransferRequest;
	}

	public void setCreditTransferRequest(BusinessMessage creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}

	public ChnlPaymentStatusRequestPojo getChnlPaymentStatusRequest() {
		return chnlPaymentStatusRequest;
	}

	public void setChnlPaymentStatusRequest(ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest) {
		this.chnlPaymentStatusRequest = chnlPaymentStatusRequest;
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
