package library.iso20022.addendum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import library.iso20022.pacs002.CashAccount38;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Envlp", propOrder = {
    "dbtr",
    "crdt",
    "cdtrAgtAcct"
})
public class SupplEnvl {

    @XmlElement(name = "Dbtr")
	private PartyAddtInfo dbtr;
    @XmlElement(name = "Cdtr")
	private PartyAddtInfo cdtr;
    
    @XmlElement(name = "CdtrAgtAcct")
    private CashAccount38 cdtrAgtAcct;

    @XmlElement(name = "DbtrAgtAcct")
    private CashAccount38 dbtrAgtAcct;

	public PartyAddtInfo getDbtr() {
		return dbtr;
	}
	public void setDbtr(PartyAddtInfo dbtr) {
		this.dbtr = dbtr;
	}
	public PartyAddtInfo getCdtr() {
		return cdtr;
	}
	public SupplEnvl setCdtr(PartyAddtInfo cdtr) {
		this.cdtr = cdtr;
		return this;
	}
	public CashAccount38 getCdtrAgtAcct() {
		return cdtrAgtAcct;
	}
	public void setCdtrAgtAcct(CashAccount38 cdtrAgtAcct) {
		this.cdtrAgtAcct = cdtrAgtAcct;
	}
	public CashAccount38 getDbtrAgtAcct() {
		return dbtrAgtAcct;
	}
	public void setDbtrAgtAcct(CashAccount38 dbtrAgtAcct) {
		this.dbtrAgtAcct = dbtrAgtAcct;
	}
    
    
    
}
