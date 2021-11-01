package bifast.outbound.pojo.chnlresponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.proxyregistration.ChnlProxyResolutionResponse;

@JsonPropertyOrder({
	"responseCode"
	,"reasonCode"
	,"reasonMessage"
	,"date"
	,"time"
	,"content"
	})
public class ChannelResponseWrapper {

	@JsonProperty("ReasonCode")
	private String reasonCode;
	@JsonProperty("ReasonMessage")
	private String reasonMessage;
	
	@JsonProperty("ResponseCode")
	private String responseCode;
	private String responseMessage;
	@JsonProperty("Date")
	private String date;
	@JsonProperty("Time")
	private String time;
	@JsonProperty("Content")
	private List<Object> responses;

	private ChnlAccountEnquiryResponsePojo accountEnquiryResponse;
	
	@JsonProperty("CreditTransferResponse")
	private ChnlCreditTransferResponsePojo chnlCreditTransferResponse;
	
	@JsonProperty("CreditTransfer")
	private BusinessMessage creditTransferResponse;
	private BusinessMessage settlementResponse;
	private BusinessMessage paymentStatusResponse;
	
	private ChnlProxyRegistrationResponsePojo proxyRegistrationResponse;
	
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionResponse proxyResolutionResponse;


	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonMessage() {
		return reasonMessage;
	}

	public void setReasonMessage(String reasonMessage) {
		this.reasonMessage = reasonMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Object> getResponses() {
		return responses;
	}

	public void setResponses(List<Object> responses) {
		this.responses = responses;
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

	public ChnlProxyRegistrationResponsePojo getProxyRegistrationResponse() {
		return proxyRegistrationResponse;
	}

	public void setProxyRegistrationResponse(ChnlProxyRegistrationResponsePojo proxyRegistrationResponse) {
		this.proxyRegistrationResponse = proxyRegistrationResponse;
	}

	public ChnlProxyResolutionResponse getProxyResolutionResponse() {
		return proxyResolutionResponse;
	}

	public void setProxyResolutionResponse(ChnlProxyResolutionResponse proxyResolutionResponse) {
		this.proxyResolutionResponse = proxyResolutionResponse;
	}





}
