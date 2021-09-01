package bifast.mock.iso20022.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import bifast.mock.iso20022.admi002.MessageRejectV01;
import bifast.mock.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.mock.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.mock.iso20022.pacs009.FinancialInstitutionCreditTransferV09;
import bifast.mock.iso20022.pacs028.FIToFIPaymentStatusRequestV04;
import bifast.mock.iso20022.prxy001.ProxyRegistrationV01;
import bifast.mock.iso20022.prxy002.ProxyRegistrationResponseV01;
import bifast.mock.iso20022.prxy003.ProxyLookUpV01;
import bifast.mock.iso20022.prxy004.ProxyLookUpResponseV01;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFICstmrCdtTrf", 
    "fiToFIPmtStsRpt", 
    "fiCdtTrf", 
    "fiToFIPmtStsReq",
    "prxyRegn",
    "prxyRegnRspn",
    "prxyLookUp",
    "prxyLookUpRspn",
    "messageReject"
})
public class Document {

	@XmlElement(name = "FitoFICstmrCdtTrf", required = false)
    protected FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf;

    @XmlElement(name = "FitoFIPmtStsRpt", required = false)
    protected FIToFIPaymentStatusReportV10 fiToFIPmtStsRpt;   // SettlementConfirmation

    @XmlElement(name = "FicdtTrf", required = false)
    protected FinancialInstitutionCreditTransferV09 fiCdtTrf;   // FI Credit Transfer

    // unt Payment Status Request 
    @XmlElement(name = "FitoFIPmtStsReq", required = false)
    protected FIToFIPaymentStatusRequestV04 fiToFIPmtStsReq;
    
    @XmlElement(name = "PrxyRegn", required = false)
    protected ProxyRegistrationV01 prxyRegn;

    @XmlElement(name = "PrxyRegnRspn", required = false)
    protected ProxyRegistrationResponseV01 prxyRegnRspn;
    
    @XmlElement(name = "PrxyLookUp", required = false)
    protected ProxyLookUpV01 prxyLookUp;

    @XmlElement(name = "PrxyLookUpRspn", required = false)
    protected ProxyLookUpResponseV01 prxyLookUpRspn;

    // unt Message Reject
    @XmlElement(name = "MessageReject", required = false)
    protected MessageRejectV01 messageReject;
    
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

	public FinancialInstitutionCreditTransferV09 getFiCdtTrf() {
		return fiCdtTrf;
	}

	public void setFiCdtTrf(FinancialInstitutionCreditTransferV09 fiCdtTrf) {
		this.fiCdtTrf = fiCdtTrf;
	}

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
}