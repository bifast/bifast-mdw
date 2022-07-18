package bifast.outbound.pojo;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.library.iso20022.custom.BusinessMessage;
import bifast.outbound.corebank.pojo.DebitRequestDTO;
import bifast.outbound.corebank.pojo.DebitReversalRequestPojo;
import bifast.outbound.credittransfer.pojo.ChnlCreditTransferRequestPojo;
import bifast.outbound.paymentstatus.pojo.ChnlPaymentStatusRequestPojo;
import bifast.outbound.proxyinquiry.pojo.ChnlProxyResolutionRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationInquiryRequestPojo;
import bifast.outbound.proxyregistration.pojo.ChnlProxyRegistrationRequestPojo;

public class RequestMessageWrapper {

	private String requestId;  // no internal reference dari channel
	private String msgName;
	private String channelId;   // nomor id channel = clientId
	private String channelType;
	private String merchantType;
	private String terminalId;
	private String senderAccountNumber;
	private Instant komiStart;
	private Instant cihubStart;
	private String cihubEncriptedRequest;
	private String cihubEncriptedResponse;
	
	private String komiTrxId;   // internal tranaction id dari komi
								// digenerate dari utilService.getkomiid;

	@JsonProperty("DebitRequest")
	private DebitRequestDTO debitAccountRequest;
	private DebitReversalRequestPojo debitReversalRequest;

	// Channel Attributes
	private Object channelRequest;
	private String channelRequestStr;

	@JsonProperty("CreditTransferRequest")
	private ChnlCreditTransferRequestPojo chnlCreditTransferRequest;
	@JsonProperty("PaymentStatusRequest")
	private ChnlPaymentStatusRequestPojo chnlPaymentStatusRequest;
	@JsonProperty("ProxyRegistrationRequest")
	private ChnlProxyRegistrationRequestPojo chnlProxyRegistrationRequest;
	@JsonProperty("ProxyResolutionRequest")
	private ChnlProxyResolutionRequestPojo chnlProxyResolutionRequest;
	@JsonProperty("ProxyRegistrationInquiry")
	private ChnlProxyRegistrationInquiryRequestPojo chnlProxyRegistrationInquiryRequest;

	// CIHUB Attributes
	private BusinessMessage accountEnquiryRequest;
	private BusinessMessage creditTransferRequest;
	private BusinessMessage proxyRegistrationRequest;
	private BusinessMessage proxyResolutionRequest;
	private BusinessMessage proxyRegistrationInquiry;

	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getMsgName() {
		return msgName;
	}
	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public Instant getKomiStart() {
		return komiStart;
	}
	public void setKomiStart(Instant komiStart) {
		this.komiStart = komiStart;
	}
	public Instant getCihubStart() {
		return cihubStart;
	}
	public void setCihubStart(Instant cihubStart) {
		this.cihubStart = cihubStart;
	}
	public String getCihubEncriptedRequest() {
		return cihubEncriptedRequest;
	}
	public void setCihubEncriptedRequest(String cihubEncriptedRequest) {
		this.cihubEncriptedRequest = cihubEncriptedRequest;
	}
	public String getCihubEncriptedResponse() {
		return cihubEncriptedResponse;
	}
	public void setCihubEncriptedResponse(String cihubEncriptedResponse) {
		this.cihubEncriptedResponse = cihubEncriptedResponse;
	}
	public String getKomiTrxId() {
		return komiTrxId;
	}
	public void setKomiTrxId(String komiTrxId) {
		this.komiTrxId = komiTrxId;
	}
	public DebitRequestDTO getDebitAccountRequest() {
		return debitAccountRequest;
	}
	public void setDebitAccountRequest(DebitRequestDTO debitAccountRequest) {
		this.debitAccountRequest = debitAccountRequest;
	}
	public DebitReversalRequestPojo getDebitReversalRequest() {
		return debitReversalRequest;
	}
	public void setDebitReversalRequest(DebitReversalRequestPojo debitReversalRequest) {
		this.debitReversalRequest = debitReversalRequest;
	}
	public Object getChannelRequest() {
		return channelRequest;
	}
	public void setChannelRequest(Object channelRequest) {
		this.channelRequest = channelRequest;
	}
	public String getChannelRequestStr() {
		return channelRequestStr;
	}
	public void setChannelRequestStr(String channelRequestStr) {
		this.channelRequestStr = channelRequestStr;
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
	public BusinessMessage getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}
	public void setAccountEnquiryRequest(BusinessMessage accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}
	public BusinessMessage getCreditTransferRequest() {
		return creditTransferRequest;
	}
	public void setCreditTransferRequest(BusinessMessage creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}
	public BusinessMessage getProxyRegistrationRequest() {
		return proxyRegistrationRequest;
	}
	public void setProxyRegistrationRequest(BusinessMessage proxyRegistrationRequest) {
		this.proxyRegistrationRequest = proxyRegistrationRequest;
	}
	public BusinessMessage getProxyResolutionRequest() {
		return proxyResolutionRequest;
	}
	public void setProxyResolutionRequest(BusinessMessage proxyResolutionRequest) {
		this.proxyResolutionRequest = proxyResolutionRequest;
	}
	public BusinessMessage getProxyRegistrationInquiry() {
		return proxyRegistrationInquiry;
	}
	public void setProxyRegistrationInquiry(BusinessMessage proxyRegistrationInquiry) {
		this.proxyRegistrationInquiry = proxyRegistrationInquiry;
	}
	


}
