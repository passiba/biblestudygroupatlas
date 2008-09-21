package fi.passiba.biblestudy.restlet.resource;

import fi.passiba.biblestudy.restlet.IBibleStudyService;
import fi.passiba.services.biblestudy.persistance.Booksection;
import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import java.util.List;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.restlet.resource.Resource;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;
import org.restlet.resource.StringRepresentation;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;

/**
 * This resource reprents single instance of bibleversionsection
 */
public class BibleVersionSectionResource extends Resource {

    @SpringBean
    IBibleStudyService biblestudyServce;
    private BibleReporter reporter;
     
    private String bibleversion_id,section_id;

    public BibleVersionSectionResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.bibleversion_id = (String)request.getAttributes().get("bibleversion_id");
        this.section_id=(String)request.getAttributes().get("section_id");
        this.reporter=new BibleReporter();
    }

    public Representation getRepresentation(Variant variant) {
        
        List <Booksection> sections=(List<Booksection>) biblestudyServce.findBooksectionByBooksectionIdAndBibleTranslationId(Long.parseLong(this.bibleversion_id),Long.parseLong(this.section_id));
        
        String xml ="";
        for(Booksection booksection:sections)
        {
            xml = this.reporter.booksectionToXml(booksection);
        }
        return new StringRepresentation(xml);
    }
}
