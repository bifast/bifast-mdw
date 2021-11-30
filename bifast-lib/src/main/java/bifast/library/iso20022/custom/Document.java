package bifast.library.iso20022.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import bifast.library.iso20022.admi002.MessageRejectV01;
import bifast.library.iso20022.admi004.SystemEventNotificationV02;
import bifast.library.iso20022.admi011.SystemEventAcknowledgementV01;
import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.iso20022.pacs028.FIToFIPaymentStatusRequestV04;
import bifast.library.iso20022.prxy001.ProxyRegistrationV01;
import bifast.library.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.library.iso20022.prxy003.ProxyLookUpV01;
import bifast.library.iso20022.prxy004.ProxyLookUpResponseV01;
import bifast.library.iso20022.prxy005.ProxyEnquiryV01;
import bifast.library.iso20022.prxy006.ProxyEnquiryResponseV01;
import bifast.library.iso20022.prxy901.ProxyNtfctnV01;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFICstmrCdtTrf", 
    "fiToFIPmtStsRpt", 
//    "fiCdtTrf", 
    "fiToFIPmtStsReq",
    "prxyRegn",
    "prxyRegnRspn",
    "prxyLookUp",
    "prxyLookUpRspn",
    "prxyNqryReq",
    "prxyNqryRspn",
    "messageReject",
    "sysEvtNtfctn",
    "sysEvtAck",
    "prxyNtfctn"
})
public class Document {

	@XmlElement(name = "FIToFICstmrCdtTrf", required = false)
    protected FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf;

    @XmlElement(name = "FIToFIPmtStsRpt", required = false)
    protected FIToFIPaymentStatusReportV10 fiToFIPmtStsRpt;   // SettlementConfirmation

//    @XmlElement(name = "FicdtTrf", required = false)
//    protected FinancialInstitutionCreditTransferV09 fiCdtTrf;   // FI Credit Transfer

    // unt Payment Status Request 
    @XmlElement(name = "FIToFIPmtStsReq", required = false)
    protected FIToFIPaymentStatusRequestV04 fiToFIPmtStsReq;
    
    @XmlElement(name = "PrxyRegn", required = false)
    protected ProxyRegistrationV01 prxyRegn;

    @XmlElement(name = "PrxyRegnRspn", required = false)
    protected ProxyRegistrationResponseV01 prxyRegnRspn;
    
    @XmlElement(name = "PrxyLookUp", required = false)
    protected ProxyLookUpV01 prxyLookUp;

    @XmlElement(name = "PrxyLookUpRspn", required = false)
    protected ProxyLookUpResponseV01 prxyLookUpRspn;
    
    @XmlElement(name = "PrxyNqryReq", required = true)
    protected ProxyEnquiryV01 prxyNqryReq;
    
    @XmlElement(name = "PrxyNqryRspn", required = true)
    protected ProxyEnquiryResponseV01 prxyNqryRspn;
    
    // unt Message Reject
    @XmlElement(name = "MessageReject", required = false)
    protected MessageRejectV01 messageReject;
    
    @XmlElement(name = "SysEvtNtfctn", required = true)
    protected SystemEventNotificationV02 sysEvtNtfctn;
    
    @XmlElement(name = "SysEvtAck", required = true)
    protected SystemEventAcknowledgementV01 sysEvtAck;

    @XmlElement(name = "PrxyNtfctn", required = true)
    protected ProxyNtfctnV01 prxyNtfctn;

    
	public FIToFICustomerCreditTransferV08 getFiToFICstmrCdtTrf() {
		return fiToFICstmrCdtTrf;
	}

	public void setFiToFICstmrCdtTrf(FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf) {
		this.fiToFICstmrCdtTrf = fiToFICstmrCdtTrf;
	}

	public FIToFIPaymentStatusReportV10 getFiToFIPmtStsRpt() {
		return fiToFIPmtStsRpt;
	}

	public void setFiToFIPmtStsRpt(FIToFIPaymentStatusReportV10 fiToFIPmtStsRpt) {
		this.fiToFIPmtStsRpt = fiToFIPmtStsRpt;
	}

//	public FinancialInstitutionCreditTransferV09 getFiCdtTrf() {
//		return fiCdtTrf;
//	}
//
//	public void setFiCdtTrf(FinancialInstitutionCreditTransferV09 fiCdtTrf) {
//		this.fiCdtTrf = fiCdtTrf;
//	}

    public FIToFIPaymentStatusRequestV04 getFIToFIPmtStsReq() {
        return fiToFIPmtStsReq;
    }

    public void setFIToFIPmtStsReq(FIToFIPaymentStatusRequestV04 value) {
        this.fiToFIPmtStsReq = value;
    }

	public FIToFIPaymentStatusRequestV04 getFiToFIPmtStsReq() {
		return fiToFIPmtStsReq;
	}

	public void setFiToFIPmtStsReq(FIToFIPaymentStatusRequestV04 fiToFIPmtStsReq) {
		this.fiToFIPmtStsReq = fiToFIPmtStsReq;
	}

	public ProxyLookUpV01 getPrxyLookUp() {
		return prxyLookUp;
	}

	public void setPrxyLookUp(ProxyLookUpV01 prxyLookUp) {
		this.prxyLookUp = prxyLookUp;
	}

	public ProxyLookUpResponseV01 getPrxyLookUpRspn() {
		return prxyLookUpRspn;
	}

	public void setPrxyLookUpRspn(ProxyLookUpResponseV01 prxyLookUpRspn) {
		this.prxyLookUpRspn = prxyLookUpRspn;
	}

	public MessageRejectV01 getMessageReject() {
		return messageReject;
	}

	public void setMessageReject(MessageRejectV01 messageReject) {
		this.messageReject = messageReject;
	}

	public ProxyRegistrationV01 getPrxyRegn() {
		return prxyRegn;
	}

	public void setPrxyRegn(ProxyRegistrationV01 prxyRegn) {
		this.prxyRegn = prxyRegn;
	}

	public ProxyRegistrationResponseV01 getPrxyRegnRspn() {
		return prxyRegnRspn;
	}

	public void setPrxyRegnRspn(ProxyRegistrationResponseV01 prxyRegnRspn) {
		this.prxyRegnRspn = prxyRegnRspn;
	}

	public ProxyEnquiryV01 getPrxyNqryReq() {
		return prxyNqryReq;
	}

	public void setPrxyNqryReq(ProxyEnquiryV01 prxyNqryReq) {
		this.prxyNqryReq = prxyNqryReq;
	}

	public ProxyEnquiryResponseV01 getPrxyNqryRspn() {
		return prxyNqryRspn;
	}

	public void setPrxyNqryRspn(ProxyEnquiryResponseV01 prxyNqryRspn) {
		this.prxyNqryRspn = prxyNqryRspn;
	}

	public SystemEventNotificationV02 getSysEvtNtfctn() {
		return sysEvtNtfctn;
	}

	public void setSysEvtNtfctn(SystemEventNotificationV02 sysEvtNtfctn) {
		this.sysEvtNtfctn = sysEvtNtfctn;
	}

	public SystemEventAcknowledgementV01 getSysEvtAck() {
		return sysEvtAck;
	}

	public void setSysEvtAck(SystemEventAcknowledgementV01 sysEvtAck) {
		this.sysEvtAck = sysEvtAck;
	}

	public ProxyNtfctnV01 getPrxyNtfctn() {
		return prxyNtfctn;
	}

	public void setPrxyNtfctn(ProxyNtfctnV01 prxyNtfctn) {
		this.prxyNtfctn = prxyNtfctn;
	}

}