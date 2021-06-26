package library.iso20022.addendum;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonRootName;

import library.iso20022.head001.BusinessApplicationHeaderV02;

@JsonRootName(value = "BusMsg")
public class BusinessMessage {

    @XmlElement(name = "AppHdr", required = true)
    private BusinessApplicationHeaderV02 appHdr;
    
    @XmlElement(name = "Document", required = true)
    private Document document;

	public BusinessApplicationHeaderV02 getAppHdr() {
		return appHdr;
	}

	public void setAppHdr(BusinessApplicationHeaderV02 appHdr) {
		this.appHdr = appHdr;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

    
}
