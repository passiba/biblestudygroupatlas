package fi.passiba.biblestudy.restlet.resource;
import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import fi.passiba.biblestudy.restlet.IBibleStudyService;
import fi.passiba.services.biblestudy.persistance.Verse;
import java.util.List;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.restlet.resource.Resource;
import org.restlet.resource.Variant;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;

/**
 * This resource represents single verse in the chapter
 */
public class BibleVersionBookChapterVerseResource extends Resource{

    
    @SpringBean
    private IBibleStudyService biblestudyServce;
   private BibleReporter reporter;
    
    private String bibleversion_id,section_id,book_id,chapter_id,verse_id;
    public BibleVersionBookChapterVerseResource(Context context, Request request, Response response) {
        super(context, request, response);

        this.getVariants().add(new Variant(MediaType.TEXT_XML));
  
        this.bibleversion_id = (String) request.getAttributes().get("bibleversion_id");
        this.section_id = (String) request.getAttributes().get("section_id");
        this.book_id = (String) request.getAttributes().get("book_id");
        this.chapter_id = (String) request.getAttributes().get("chapter_id");
        this.verse_id = (String) request.getAttributes().get("verse_id");
       this.reporter= new BibleReporter();
    }


    public Representation getRepresentation(Variant variant) {
        List <Verse> verses=(List<Verse>)biblestudyServce.findVerseByVerseidBookIdChapterIdSectionIdTranslationId(Long.parseLong(this.bibleversion_id),Long.parseLong(this.section_id),Long.parseLong(this.book_id),Long.parseLong( this.chapter_id),Long.parseLong(this.verse_id));
        
        String xml ="";
        for(Verse verse:verses)
        {
           xml = this.reporter.verseToXml(verse);
        }
        return new StringRepresentation(xml);
    }
}
