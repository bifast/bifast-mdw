package bifast.outbound.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.accountcustmrinfo.pojo.ChnlAccountCustomerInfoRequestPojo;
import bifast.outbound.accountenquiry.pojo.ChnlAccountEnquiryRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.paymentstatus.pojo.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationInquiryRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;


public class ChnlRequestWrapper {

	@JsonProperty("AccountEnquiryRequest")
	private ChnlAccountEnquiryRequestPojo chnlAccountEnquiryRequest;
	
	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo chnlCreditTransferRequest;

	@JsonProperty("PaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest;

	@JsonProperty("AccountCustomerInfo")
	private ChnlAccountCustomerInfoRequestPojo chnlAccountCstmrInfoRequest;

	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo chnlProxyRegistrationRequest;

	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionRequestPojo chnlProxyResolutionRequest;
	
	@JsonProperty("ProxyRegistrationInquiry")
	private ChnlProxyRegistrationInquiryRequestPojo chnlProxyRegistrationInquiryRequest;
	
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

	public ChnlPaymentStatusRequestPojo getChnlPaymentStatusRequest() {
		return chnlPaymentStatusRequest;
	}

	public void setChnlPaymentStatusRequest(ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest) {
		this.chnlPaymentStatusRequest = chnlPaymentStatusRequest;
	}

	public ChnlAccountCustomerInfoRequestPojo getChnlAccountCstmrInfoRequest() {
		return chnlAccountCstmrInfoRequest;
	}

	public void setChnlAccountCstmrInfoRequest(ChnlAccountCustomerInfoRequestPojo chnlAccountCstmrInfoRequest) {
		this.chnlAccountCstmrInfoRequest = chnlAccountCstmrInfoRequest;
	}

	public ChnlProxyRegistrationRequestPojo getChnlProxyRegistrationRequest() {
		return chnlProxyRegistrationRequest;
	}

	public void setChnlProxyRegistrationRequest(ChnlProxyRegistrationRequestPojo chnlProxyRegistrationRequest) {
		this.chnlProxyRegistrationRequest = chnlProxyRegistrationRequest;
	}

	public ChnlProxyResolutionRequestPojo getChnlProxyResolutionRequest() {
		return chnlProxyResolutionRequest;
	}

	public void setChnlProxyResolutionRequest(ChnlProxyResolutionRequestPojo chnlProxyResolutionRequest) {
		this.chnlProxyResolutionRequest = chnlProxyResolutionRequest;
	}

	public ChnlProxyRegistrationInquiryRequestPojo getChnlProxyRegistrationInquiryRequest() {
		return chnlProxyRegistrationInquiryRequest;
	}

	public void setChnlProxyRegistrationInquiryRequest(
			ChnlProxyRegistrationInquiryRequestPojo chnlProxyRegistrationInquiryRequest) {
		this.chnlProxyRegistrationInquiryRequest = chnlProxyRegistrationInquiryRequest;
	}

//	@JsonProperty("ProxyRegistrationInquiry")
//	private ChnlProxyRegistrationInquiryPojo chnlProxyRegistrationInquiry;



}
