package library.iso20022.addendum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import library.iso20022.pacs002.FIToFIPaymentStatusReportV11;
import library.iso20022.pacs008.FIToFICustomerCreditTransferV09;
import library.iso20022.pacs009.FinancialInstitutionCreditTransferV10;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFICstmrCdtTrf", "fiToFIPmtStsRpt", "fiCdtTrf"
})
public class Document {

	@XmlElement(name = "FIToFICstmrCdtTrf", required = false)
    protected FIToFICustomerCreditTransferV09 fiToFICstmrCdtTrf;

    @XmlElement(name = "FIToFIPmtStsRpt", required = false)
    protected FIToFIPaymentStatusReportV11 fiToFIPmtStsRpt;

    @XmlElement(name = "FICdtTrf", required = false)
    protected FinancialInstitutionCreditTransferV10 fiCdtTrf;

	public FIToFICustomerCreditTransferV09 getFiToFICstmrCdtTrf() {
		return fiToFICstmrCdtTrf;
	}

	public void setFiToFICstmrCdtTrf(FIToFICustomerCreditTransferV09 fiToFICstmrCdtTrf) {
		this.fiToFICstmrCdtTrf = fiToFICstmrCdtTrf;
	}

	public FIToFIPaymentStatusReportV11 getFiToFIPmtStsRpt() {
		return fiToFIPmtStsRpt;
	}

	public void setFiToFIPmtStsRpt(FIToFIPaymentStatusReportV11 fiToFIPmtStsRpt) {
		this.fiToFIPmtStsRpt = fiToFIPmtStsRpt;
	}

	public FinancialInstitutionCreditTransferV10 getFiCdtTrf() {
		return fiCdtTrf;
	}

	public void setFiCdtTrf(FinancialInstitutionCreditTransferV10 fiCdtTrf) {
		this.fiCdtTrf = fiCdtTrf;
	}


    
}
