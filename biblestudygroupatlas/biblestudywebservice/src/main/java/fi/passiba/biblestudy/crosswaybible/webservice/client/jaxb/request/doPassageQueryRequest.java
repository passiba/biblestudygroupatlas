//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-646 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.22 at 01:19:22 AM EEST 
//


package fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.request;


/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "key",
    "passage",
    "options"
})
@XmlRootElement(name = "doPassageQueryRequest")*/
public class doPassageQueryRequest {

   // @XmlElement(required = true)
    protected String key;
   // @XmlElement(required = true)
    protected String passage;
    //@XmlElement(required = true)
    protected Options options;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the passage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassage() {
        return passage;
    }

    /**
     * Sets the value of the passage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassage(String value) {
        this.passage = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link Options }
     *     
     */
    public Options getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link Options }
     *     
     */
    public void setOptions(Options value) {
        this.options = value;
    }

}