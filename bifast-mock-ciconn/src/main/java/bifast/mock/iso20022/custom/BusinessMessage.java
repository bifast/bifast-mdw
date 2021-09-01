package bifast.mock.iso20022.custom;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonRootName;

import bifast.mock.iso20022.head001.BusinessApplicationHeaderV01;


@JsonRootName(value = "BusMsg")
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
