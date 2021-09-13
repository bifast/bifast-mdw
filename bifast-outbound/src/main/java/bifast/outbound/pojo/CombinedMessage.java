package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.library.iso20022.custom.BusinessMessageWrap;
import bifast.outbound.accountenquiry.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.ChnlCreditTransferRequestPojo;
import bifast.outbound.ficredittransfer.ChnlFICreditTransferRequestPojo;
import bifast.outbound.paymentstatus.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationRequestPojo;
import bifast.outbound.reversect.ChnlReverseCTRequestPojo;

@JsonRootName("CombinedMessages")
@JsonPropertyOrder({"accountEnquiryRequest", 
	"creditTransferRequest",
	"fiCreditTransferRequest", 
	"paymentStatusRequest", 
	"reverseCreditTransferRequest",
	"proxyRegistrationRequest",
	"outboundMessage",
	"responseMessage"})
public class CombinedMessage {

	@JsonProperty("AccountEnquiryRequest")
	private ChnlAccountEnquiryRequestPojo accountEnquiryRequest;
	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo creditTransferRequest;
	@JsonProperty("FICreditTransferRequest")
	private ChnlFICreditTransferRequestPojo fiCreditTransferRequest;
	@JsonProperty("PaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo paymentStatusRequest;
	@JsonProperty("ReverseCreditTransferRequest")
	private ChnlReverseCTRequestPojo reverseCreditTransferRequest;
	
	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo proxyRegistrationRequest;
	
	@JsonProperty("OutboundMessage")
	private BusinessMessageWrap outboundMessage;
	@JsonProperty("ResponseMessage")
	private BusinessMessageWrap responseMessage;

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
	public ChnlReverseCTRequestPojo getReverseCreditTransferRequest() {
		return reverseCreditTransferRequest;
	}
	public void setReverseCreditTransferRequest(ChnlReverseCTRequestPojo reverseCreditTransferRequest) {
		this.reverseCreditTransferRequest = reverseCreditTransferRequest;
	}
	public BusinessMessageWrap getOutboundMessage() {
		return outboundMessage;
	}
	public void setOutboundMessage(BusinessMessageWrap outboundMessage) {
		this.outboundMessage = outboundMessage;
	}
	public BusinessMessageWrap getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(BusinessMessageWrap responseMessage) {
		this.responseMessage = responseMessage;
	}
	public ChnlProxyRegistrationRequestPojo getProxyRegistrationRequest() {
		return proxyRegistrationRequest;
	}
	public void setProxyRegistrationRequest(ChnlProxyRegistrationRequestPojo proxyRegistrationRequest) {
		this.proxyRegistrationRequest = proxyRegistrationRequest;
	}

	
	
}
