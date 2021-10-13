package bifast.outbound.pojo.flat;

import com.fasterxml.jackson.annotation.JsonProperty;

import bifast.outbound.pojo.ChnlFailureResponsePojo;

public class FlatMessageWrapper {

	@JsonProperty("AccountEnquiryRequest")
	private FlatPacs008Pojo accountEnquiryRequest;

	@JsonProperty("AccountEnquiryResponse")
	private FlatPacs002Pojo accountEnquiryResponse;

	@JsonProperty("CreditTransferRequest")
	private FlatPacs008Pojo creditTransferRequest;

	@JsonProperty("CreditTransferResponse")
	private FlatPacs002Pojo creditTransferResponse;

//	@JsonProperty("FICreditTransferRequest")
//	private FlatPacs009Pojo fiCreditTransferRequest;

	@JsonProperty("FICreditTransferResponse")
	private FlatPacs002Pojo fiCreditTransferResponse;

	@JsonProperty("FailureResponse")
	private ChnlFailureResponsePojo faultResponse;

	public FlatPacs008Pojo getAccountEnquiryRequest() {
		return accountEnquiryRequest;
	}

	public void setAccountEnquiryRequest(FlatPacs008Pojo accountEnquiryRequest) {
		this.accountEnquiryRequest = accountEnquiryRequest;
	}

	public FlatPacs002Pojo getAccountEnquiryResponse() {
		return accountEnquiryResponse;
	}

	public void setAccountEnquiryResponse(FlatPacs002Pojo accountEnquiryResponse) {
		this.accountEnquiryResponse = accountEnquiryResponse;
	}

	public FlatPacs008Pojo getCreditTransferRequest() {
		return creditTransferRequest;
	}

	public void setCreditTransferRequest(FlatPacs008Pojo creditTransferRequest) {
		this.creditTransferRequest = creditTransferRequest;
	}

	public FlatPacs002Pojo getCreditTransferResponse() {
		return creditTransferResponse;
	}

	public void setCreditTransferResponse(FlatPacs002Pojo creditTransferResponse) {
		this.creditTransferResponse = creditTransferResponse;
	}

	public FlatPacs002Pojo getFiCreditTransferResponse() {
		return fiCreditTransferResponse;
	}

	public void setFiCreditTransferResponse(FlatPacs002Pojo fiCreditTransferResponse) {
		this.fiCreditTransferResponse = fiCreditTransferResponse;
	}

	public ChnlFailureResponsePojo getFaultResponse() {
		return faultResponse;
	}

	public void setFaultResponse(ChnlFailureResponsePojo faultResponse) {
		this.faultResponse = faultResponse;
	}



	
}
