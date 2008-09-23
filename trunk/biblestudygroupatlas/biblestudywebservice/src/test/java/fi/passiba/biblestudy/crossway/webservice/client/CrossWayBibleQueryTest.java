/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.biblestudy.crossway.webservice.client;
import fi.passiba.biblestudy.crosswaybible.webservice.client.BibleVerseClient;
import fi.passiba.biblestudy.crosswaybible.webservice.client.domain.RequestDetails;
import fi.passiba.biblestudy.crosswaybible.webservice.client.request.Options;
import fi.passiba.biblestudy.crosswaybible.webservice.client.response.CrosswayBible;
import java.math.BigInteger;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 *
 * @author haverinen
 */
public class CrossWayBibleQueryTest extends
                  AbstractDependencyInjectionSpringContextTests {
    
    
     @Override
   protected String[] getConfigLocations() {
      return new String[] { "classpath:META-INF/biblestudy-webservice.xml" };
   }

   public void testTemplateBasedClient() throws Exception {
       
       
      BibleVerseClient templateBasedClient = (BibleVerseClient) applicationContext
                        .getBean("templateClient");
      
      RequestDetails requestDetails= new RequestDetails();
      requestDetails.setKey("IP");
      requestDetails.setPassage("Mark 5");
      
      Options options= new Options();
      
      options.setOutputFormat("crossway-xml-1.0");
      
      options.setIncludePassageReferences(true);
      options.setIncludeFirstVerseNumbers(false);
      options.setIncludeVerseNumbers(true);
      options.setIncludeFootnotes(false);
      options.setIncludeDoctype(true);
      options.setBaseElement("verse-unit");
      options.setIncludeFootnoteLinks(false);
      options.setIncludeHeadings(true);
      options.setIncludeSurroundingChapters(true);
      options.setIncludeSurroundingChapters(false);
      options.setIncludeShortCopyright(false);
      options.setIncludeXmlDeclaration(true);
     
       
      options.setIncludeLineBreaks(true);
      options.setIncludeCrossReferences(false);
      options.setIncludeSimpleEntities(true);
      options.setIncludeSelahs(false);
      options.setIncludeQuoteEntities(true);
      options.setIncludeVirtualAttributes(false);
      options.setIncludePassageHorizontalLines(false);
      options.setIncludeHeadingHorizontalLines(false);
      options.setIncludeWordIds(false);
      options.setIncludeContentType(true);      
      options.setLineLength(Integer.valueOf(100));
      requestDetails.setOptions(options);
      /*CrosswayBible bibleChapter=templateBasedClient.doPassageQuery(requestDetails);
      assertNotNull( bibleChapter);
      assertNotNull( bibleChapter.getPassage());*/
    
   }

}
