//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-646 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.21 at 08:31:55 PM EEST 
//


package fi.passiba.biblestudy.crosswaybible.webservice.client.jaxb.response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Next_QNAME = new QName("", "next");
    private final static QName _Previous_QNAME = new QName("", "previous");
    private final static QName _Copyright_QNAME = new QName("", "copyright");
    private final static QName _Reference_QNAME = new QName("", "reference");
    private final static QName _Heading_QNAME = new QName("", "heading");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Woc }
     * 
     */
    public Woc createWoc() {
        return new Woc();
    }

    /**
     * Create an instance of {@link I }
     * 
     */
    public I createI() {
        return new I();
    }

    /**
     * Create an instance of {@link Current }
     * 
     */
    public Current createCurrent() {
        return new Current();
    }

    /**
     * Create an instance of {@link BeginParagraph }
     * 
     */
    public BeginParagraph createBeginParagraph() {
        return new BeginParagraph();
    }

    /**
     * Create an instance of {@link SurroundingChapters }
     * 
     */
    public SurroundingChapters createSurroundingChapters() {
        return new SurroundingChapters();
    }

    /**
     * Create an instance of {@link BeginChapter }
     * 
     */
    public BeginChapter createBeginChapter() {
        return new BeginChapter();
    }

    /**
     * Create an instance of {@link VerseNum }
     * 
     */
    public VerseNum createVerseNum() {
        return new VerseNum();
    }

    /**
     * Create an instance of {@link Footnote }
     * 
     */
    public Footnote createFootnote() {
        return new Footnote();
    }

    /**
     * Create an instance of {@link Content }
     * 
     */
    public Content createContent() {
        return new Content();
    }

    /**
     * Create an instance of {@link Marker }
     * 
     */
    public Marker createMarker() {
        return new Marker();
    }

    /**
     * Create an instance of {@link EndParagraph }
     * 
     */
    public EndParagraph createEndParagraph() {
        return new EndParagraph();
    }

    /**
     * Create an instance of {@link DoPassageQueryResponse }
     * 
     */
    public DoPassageQueryResponse createDoPassageQueryResponse() {
        return new DoPassageQueryResponse();
    }

    /**
     * Create an instance of {@link Crossref }
     * 
     */
    public Crossref createCrossref() {
        return new Crossref();
    }

    /**
     * Create an instance of {@link CrosswayBible }
     * 
     */
    public CrosswayBible createCrosswayBible() {
        return new CrosswayBible();
    }

    /**
     * Create an instance of {@link Return }
     * 
     */
    public Return createReturn() {
        return new Return();
    }

    /**
     * Create an instance of {@link EndChapter }
     * 
     */
    public EndChapter createEndChapter() {
        return new EndChapter();
    }

    /**
     * Create an instance of {@link Passage }
     * 
     */
    public Passage createPassage() {
        return new Passage();
    }

    /**
     * Create an instance of {@link VerseUnit }
     * 
     */
    public VerseUnit createVerseUnit() {
        return new VerseUnit();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "next")
    public JAXBElement<String> createNext(String value) {
        return new JAXBElement<String>(_Next_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "previous")
    public JAXBElement<String> createPrevious(String value) {
        return new JAXBElement<String>(_Previous_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "copyright")
    public JAXBElement<String> createCopyright(String value) {
        return new JAXBElement<String>(_Copyright_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "reference")
    public JAXBElement<String> createReference(String value) {
        return new JAXBElement<String>(_Reference_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "heading")
    public JAXBElement<String> createHeading(String value) {
        return new JAXBElement<String>(_Heading_QNAME, String.class, null, value);
    }

}
