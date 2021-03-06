//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.28 at 04:48:40 PM ICT 
//


package bifast.library.iso20022.prxy001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProxyRegistrationV01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProxyRegistrationV01"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:prxy.001.001.01}GroupHeader59"/&gt;
 *         &lt;element name="Regn" type="{urn:iso:std:iso:20022:tech:xsd:prxy.001.001.01}ProxyRegistration1"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:prxy.001.001.01}BI_SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProxyRegistrationV01", propOrder = {
    "grpHdr",
    "regn",
    "splmtryData"
})
public class ProxyRegistrationV01 {

    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader59 grpHdr;
    @XmlElement(name = "Regn", required = true)
    protected ProxyRegistration1 regn;
    @XmlElement(name = "SplmtryData")
    protected List<BISupplementaryData1> splmtryData;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader59 }
     *     
     */
    public GroupHeader59 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader59 }
     *     
     */
    public void setGrpHdr(GroupHeader59 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the regn property.
     * 
     * @return
     *     possible object is
     *     {@link ProxyRegistration1 }
     *     
     */
    public ProxyRegistration1 getRegn() {
        return regn;
    }

    /**
     * Sets the value of the regn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProxyRegistration1 }
     *     
     */
    public void setRegn(ProxyRegistration1 value) {
        this.regn = value;
    }

    /**
     * Gets the value of the splmtryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BISupplementaryData1 }
     * 
     * 
     */
    public List<BISupplementaryData1> getSplmtryData() {
        if (splmtryData == null) {
            splmtryData = new ArrayList<BISupplementaryData1>();
        }
        return this.splmtryData;
    }

}
