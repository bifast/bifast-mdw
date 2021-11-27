package bifast.library.iso20022.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.library.iso20022.head001.BusinessApplicationHeaderV01;



@JsonRootName(value = "BusMsg")
@XmlRootElement(name = "BusMsg")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessMessage {

    @XmlElement(name = "AppHdr", required = true)
    private BusinessApplicationHeaderV01 appHdr;
    
    @XmlElement(name = "Document", required = true)
    private Document document;

	public BusinessApplicationHeaderV01 getAppHdr() {
		return appHdr;
	}

	public void setAppHdr(BusinessApplicationHeaderV01 appHdr) {
		this.appHdr = appHdr;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

    
}
