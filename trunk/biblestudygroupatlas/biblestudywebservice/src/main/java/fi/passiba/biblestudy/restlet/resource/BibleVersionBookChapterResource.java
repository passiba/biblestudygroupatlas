package fi.passiba.biblestudy.restlet.resource;

import fi.passiba.biblestudy.restlet.IBibleStudyService;
import org.restlet.resource.Resource;
import org.restlet.resource.Variant;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;

import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.util.List;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * This resource represents single Book chapter in the bible
 */
public class BibleVersionBookChapterResource extends Resource {

    
    @SpringBean
    IBibleStudyService biblestudyServce;
    
    private String bibleversion_id,  section_id,  book_id,  chapter_id;
   private BibleReporter reporter;
   
    public BibleVersionBookChapterResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.bibleversion_id = (String) request.getAttributes().get("bibleversion_id");
        this.section_id = (String) request.getAttributes().get("section_id");
        this.book_id = (String) request.getAttributes().get("book_id");
        this.chapter_id = (String) request.getAttributes().get("chapter_id");

       this.reporter = new BibleReporter();



    }

    public Representation getRepresentation(Variant variant) {
         List <Chapter> chapters=(List<Chapter>)biblestudyServce.findChapterByChapterIdBookIdSectionIdBibleVersionID(Long.parseLong(this.bibleversion_id),Long.parseLong(this.section_id),Long.parseLong(this.book_id),Long.parseLong(this.chapter_id));
        
        String xml ="";
        for(Chapter chapter:chapters)
        {
            xml = this.reporter.chapterToXml(chapter);
        }
        return new StringRepresentation(xml);
    }
}
