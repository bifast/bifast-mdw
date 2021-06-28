package bifast.library.iso20022.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import bifast.library.iso20022.pacs002.FIToFIPaymentStatusReportV10;
import bifast.library.iso20022.pacs008.FIToFICustomerCreditTransferV08;
import bifast.library.iso20022.pacs009.FinancialInstitutionCreditTransferV09;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "fiToFICstmrCdtTrf", "fiToFIPmtStsRpt", "fiCdtTrf"
})
public class Document {

	@XmlElement(name = "FIToFICstmrCdtTrf", required = false)
    protected FIToFICustomerCreditTransferV08 fiToFICstmrCdtTrf;

    @XmlElement(name = "FIToFIPmtStsRpt", required = false)
    protected FIToFIPaymentStatusReportV10 fiToFIPmtStsRpt;

    @XmlElement(name = "FICdtTrf", required = false)
    protected FinancialInstitutionCreditTransferV09 fiCdtTrf;

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


    
}
