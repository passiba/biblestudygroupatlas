package fi.passiba.biblestudy.crosswaybible.webservice.client.request;





public class Options {

   
    protected String outputFormat;
  
    protected boolean includePassageReferences;
  
    protected boolean includeFirstVerseNumbers;
   
    protected boolean includeVerseNumbers;
  
    protected boolean includeFootnotes;
  
    protected boolean includeFootnoteLinks;

    protected boolean includeHeadings;
  
    protected boolean includeSubheadings;
  
    protected boolean includeSurroundingChapters;
 
    protected boolean includeShortCopyright;
 
    protected boolean includeXmlDeclaration;
   
    protected boolean includeDoctype;
   
    protected boolean includeQuoteEntities;
   
    protected boolean includeSimpleEntities;
   
    protected boolean includeCrossReferences;
 
    protected boolean includeLineBreaks;
    
    protected boolean includeVirtualAttributes;
 
    protected String baseElement;
 
    protected boolean includePassageHorizontalLines;
   
    protected boolean includeHeadingHorizontalLines;
   
    protected boolean includeSelahs;
   
    protected boolean includeWordIds;
   
    protected boolean includeContentType;
  
    protected Integer lineLength;

    /**
     * Gets the value of the outputFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    /**
     * Sets the value of the outputFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFormat(String value) {
        this.outputFormat = value;
    }

    /**
     * Gets the value of the includePassageReferences property.
     * 
     */
    public boolean isIncludePassageReferences() {
        return includePassageReferences;
    }

    /**
     * Sets the value of the includePassageReferences property.
     * 
     */
    public void setIncludePassageReferences(boolean value) {
        this.includePassageReferences = value;
    }

    /**
     * Gets the value of the includeFirstVerseNumbers property.
     * 
     */
    public boolean isIncludeFirstVerseNumbers() {
        return includeFirstVerseNumbers;
    }

    /**
     * Sets the value of the includeFirstVerseNumbers property.
     * 
     */
    public void setIncludeFirstVerseNumbers(boolean value) {
        this.includeFirstVerseNumbers = value;
    }

    /**
     * Gets the value of the includeVerseNumbers property.
     * 
     */
    public boolean isIncludeVerseNumbers() {
        return includeVerseNumbers;
    }

    /**
     * Sets the value of the includeVerseNumbers property.
     * 
     */
    public void setIncludeVerseNumbers(boolean value) {
        this.includeVerseNumbers = value;
    }

    /**
     * Gets the value of the includeFootnotes property.
     * 
     */
    public boolean isIncludeFootnotes() {
        return includeFootnotes;
    }

    /**
     * Sets the value of the includeFootnotes property.
     * 
     */
    public void setIncludeFootnotes(boolean value) {
        this.includeFootnotes = value;
    }

    /**
     * Gets the value of the includeFootnoteLinks property.
     * 
     */
    public boolean isIncludeFootnoteLinks() {
        return includeFootnoteLinks;
    }

    /**
     * Sets the value of the includeFootnoteLinks property.
     * 
     */
    public void setIncludeFootnoteLinks(boolean value) {
        this.includeFootnoteLinks = value;
    }

    /**
     * Gets the value of the includeHeadings property.
     * 
     */
    public boolean isIncludeHeadings() {
        return includeHeadings;
    }

    /**
     * Sets the value of the includeHeadings property.
     * 
     */
    public void setIncludeHeadings(boolean value) {
        this.includeHeadings = value;
    }

    /**
     * Gets the value of the includeSubheadings property.
     * 
     */
    public boolean isIncludeSubheadings() {
        return includeSubheadings;
    }

    /**
     * Sets the value of the includeSubheadings property.
     * 
     */
    public void setIncludeSubheadings(boolean value) {
        this.includeSubheadings = value;
    }

    /**
     * Gets the value of the includeSurroundingChapters property.
     * 
     */
    public boolean isIncludeSurroundingChapters() {
        return includeSurroundingChapters;
    }

    /**
     * Sets the value of the includeSurroundingChapters property.
     * 
     */
    public void setIncludeSurroundingChapters(boolean value) {
        this.includeSurroundingChapters = value;
    }

    /**
     * Gets the value of the includeShortCopyright property.
     * 
     */
    public boolean isIncludeShortCopyright() {
        return includeShortCopyright;
    }

    /**
     * Sets the value of the includeShortCopyright property.
     * 
     */
    public void setIncludeShortCopyright(boolean value) {
        this.includeShortCopyright = value;
    }

    /**
     * Gets the value of the includeXmlDeclaration property.
     * 
     */
    public boolean isIncludeXmlDeclaration() {
        return includeXmlDeclaration;
    }

    /**
     * Sets the value of the includeXmlDeclaration property.
     * 
     */
    public void setIncludeXmlDeclaration(boolean value) {
        this.includeXmlDeclaration = value;
    }

    /**
     * Gets the value of the includeDoctype property.
     * 
     */
    public boolean isIncludeDoctype() {
        return includeDoctype;
    }

    /**
     * Sets the value of the includeDoctype property.
     * 
     */
    public void setIncludeDoctype(boolean value) {
        this.includeDoctype = value;
    }

    /**
     * Gets the value of the includeQuoteEntities property.
     * 
     */
    public boolean isIncludeQuoteEntities() {
        return includeQuoteEntities;
    }

    /**
     * Sets the value of the includeQuoteEntities property.
     * 
     */
    public void setIncludeQuoteEntities(boolean value) {
        this.includeQuoteEntities = value;
    }

    /**
     * Gets the value of the includeSimpleEntities property.
     * 
     */
    public boolean isIncludeSimpleEntities() {
        return includeSimpleEntities;
    }

    /**
     * Sets the value of the includeSimpleEntities property.
     * 
     */
    public void setIncludeSimpleEntities(boolean value) {
        this.includeSimpleEntities = value;
    }

    /**
     * Gets the value of the includeCrossReferences property.
     * 
     */
    public boolean isIncludeCrossReferences() {
        return includeCrossReferences;
    }

    /**
     * Sets the value of the includeCrossReferences property.
     * 
     */
    public void setIncludeCrossReferences(boolean value) {
        this.includeCrossReferences = value;
    }

    /**
     * Gets the value of the includeLineBreaks property.
     * 
     */
    public boolean isIncludeLineBreaks() {
        return includeLineBreaks;
    }

    /**
     * Sets the value of the includeLineBreaks property.
     * 
     */
    public void setIncludeLineBreaks(boolean value) {
        this.includeLineBreaks = value;
    }

    /**
     * Gets the value of the includeVirtualAttributes property.
     * 
     */
    public boolean isIncludeVirtualAttributes() {
        return includeVirtualAttributes;
    }

    /**
     * Sets the value of the includeVirtualAttributes property.
     * 
     */
    public void setIncludeVirtualAttributes(boolean value) {
        this.includeVirtualAttributes = value;
    }

    /**
     * Gets the value of the baseElement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseElement() {
        return baseElement;
    }

    /**
     * Sets the value of the baseElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseElement(String value) {
        this.baseElement = value;
    }

    /**
     * Gets the value of the includePassageHorizontalLines property.
     * 
     */
    public boolean isIncludePassageHorizontalLines() {
        return includePassageHorizontalLines;
    }

    /**
     * Sets the value of the includePassageHorizontalLines property.
     * 
     */
    public void setIncludePassageHorizontalLines(boolean value) {
        this.includePassageHorizontalLines = value;
    }

    /**
     * Gets the value of the includeHeadingHorizontalLines property.
     * 
     */
    public boolean isIncludeHeadingHorizontalLines() {
        return includeHeadingHorizontalLines;
    }

    /**
     * Sets the value of the includeHeadingHorizontalLines property.
     * 
     */
    public void setIncludeHeadingHorizontalLines(boolean value) {
        this.includeHeadingHorizontalLines = value;
    }

    /**
     * Gets the value of the includeSelahs property.
     * 
     */
    public boolean isIncludeSelahs() {
        return includeSelahs;
    }

    /**
     * Sets the value of the includeSelahs property.
     * 
     */
    public void setIncludeSelahs(boolean value) {
        this.includeSelahs = value;
    }

    /**
     * Gets the value of the includeWordIds property.
     * 
     */
    public boolean isIncludeWordIds() {
        return includeWordIds;
    }

    /**
     * Sets the value of the includeWordIds property.
     * 
     */
    public void setIncludeWordIds(boolean value) {
        this.includeWordIds = value;
    }

    /**
     * Gets the value of the includeContentType property.
     * 
     */
    public boolean isIncludeContentType() {
        return includeContentType;
    }

    /**
     * Sets the value of the includeContentType property.
     * 
     */
    public void setIncludeContentType(boolean value) {
        this.includeContentType = value;
    }

    /**
     * Gets the value of the lineLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public Integer getLineLength() {
        return lineLength;
    }

    /**
     * Sets the value of the lineLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLineLength(Integer value) {
        this.lineLength = value;
    }

}
