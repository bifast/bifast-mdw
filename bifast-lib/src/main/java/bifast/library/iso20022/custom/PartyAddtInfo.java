package bifast.library.iso20022.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CrediturEnvlSuppl", propOrder = {
    "tp",
    "id",
    "rsdntSts",
    "twnNm"
})
public class PartyAddtInfo {

    @XmlElement(name = "Tp")
	private String tp;
    @XmlElement(name = "Id")
	private String id;
    @XmlElement(name = "RsdntSts")
	private String rsdntSts;
    @XmlElement(name = "TwnNm")
	private String twnNm;
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRsdntSts() {
		return rsdntSts;
	}
	public void setRsdntSts(String rsdntSts) {
		this.rsdntSts = rsdntSts;
	}
	public String getTwnNm() {
		return twnNm;
	}
	public void setTwnNm(String twnNm) {
		this.twnNm = twnNm;
	}

    
}
