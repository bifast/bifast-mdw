package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryResponsePojo;
import bifast.outbound.corebank.CBDebitInstructionResponsePojo;
import bifast.outbound.credittransfer.ChnlCreditTransferResponsePojo;
import bifast.outbound.proxyregistration.ChnlProxyRegistrationResponse;
import bifast.outbound.proxyregistration.ChnlProxyResolutionResponse;

public class ChannelResponseWrapper {

	@JsonProperty("DebitResponse")
	private CBDebitInstructionResponsePojo cbDebitInstructionResponse;

	@JsonProperty("AccountEnquiryResponse")
	private ChnlAccountEnquiryResponsePojo accountEnquiryResponse;
	
	@JsonProperty("CreditTransferResponse")
	private ChnlCreditTransferResponsePojo chnlCreditTransferResponse;
	
	@JsonProperty("CreditTransfer")
	private BusinessMessage creditTransferResponse;
	private BusinessMessage settlementResponse;
	private BusinessMessage paymentStatusResponse;
	
//	@JsonProperty("PaymentStatusRequest")
//	private ChnlPaymentStatusRequestPojo paymentStatusRequest;
	
	@JsonProperty("ProxyRegistrationResponse")
	private ChnlProxyRegistrationResponse proxyRegistrationResponse;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionResponse proxyResolutionResponse;

	@JsonProperty("FailureResponse")
	private ChnlFailureResponsePojo faultResponse;

	public CBDebitInstructionResponsePojo getCbDebitInstructionResponse() {
		return cbDebitInstructionResponse;
	}

	public void setCbDebitInstructionResponse(CBDebitInstructionResponsePojo cbDebitInstructionResponse) {
		this.cbDebitInstructionResponse = cbDebitInstructionResponse;
	}

	public ChnlAccountEnquiryResponsePojo getAccountEnquiryResponse() {
		return accountEnquiryResponse;
	}

	public void setAccountEnquiryResponse(ChnlAccountEnquiryResponsePojo accountEnquiryResponse) {
		this.accountEnquiryResponse = accountEnquiryResponse;
	}

	public ChnlCreditTransferResponsePojo getChnlCreditTransferResponse() {
		return chnlCreditTransferResponse;
	}

	public void setChnlCreditTransferResponse(ChnlCreditTransferResponsePojo chnlCreditTransferResponse) {
		this.chnlCreditTransferResponse = chnlCreditTransferResponse;
	}

	public BusinessMessage getCreditTransferResponse() {
		return creditTransferResponse;
	}

	public void setCreditTransferResponse(BusinessMessage creditTransferResponse) {
		this.creditTransferResponse = creditTransferResponse;
	}

	public BusinessMessage getSettlementResponse() {
		return settlementResponse;
	}

	public void setSettlementResponse(BusinessMessage settlementResponse) {
		this.settlementResponse = settlementResponse;
	}

	public BusinessMessage getPaymentStatusResponse() {
		return paymentStatusResponse;
	}

	public void setPaymentStatusResponse(BusinessMessage paymentStatusResponse) {
		this.paymentStatusResponse = paymentStatusResponse;
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
