//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.28 at 04:49:18 PM ICT 
//


package bifast.mock.iso20022.prxy003;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProxyLookUpChoice1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProxyLookUpChoice1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="PrxyOnly" type="{urn:iso:std:iso:20022:tech:xsd:prxy.003.001.01}ProxyLookUp11"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProxyLookUpChoice1", propOrder = {
    "prxyOnly"
})
public class ProxyLookUpChoice1 {

    @XmlElement(name = "PrxyOnly")
    protected ProxyLookUp11 prxyOnly;

    /**
     * Gets the value of the prxyOnly property.
     * 
     * @return
     *     possible object is
     *     {@link ProxyLookUp11 }
     *     
     */
    public ProxyLookUp11 getPrxyOnly() {
        return prxyOnly;
    }

    /**
     * Sets the value of the prxyOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProxyLookUp11 }
     *     
     */
    public void setPrxyOnly(ProxyLookUp11 value) {
        this.prxyOnly = value;
    }

}
