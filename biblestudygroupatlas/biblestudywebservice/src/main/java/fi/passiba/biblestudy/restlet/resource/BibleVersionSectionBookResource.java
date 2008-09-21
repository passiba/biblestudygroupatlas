package fi.passiba.biblestudy.restlet.resource;
import fi.passiba.biblestudy.restlet.IBibleStudyService;
import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import fi.passiba.services.biblestudy.persistance.Book;
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
 * This resource represents a book for a particular book 
 */
public class BibleVersionSectionBookResource extends Resource {
    private String bibleversion_id,section_id,book_id;
    
    @SpringBean
    private IBibleStudyService biblestudyServce;
    private BibleReporter reporter;
    public BibleVersionSectionBookResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.section_id = (String)request.getAttributes().get("section_id");
        this.bibleversion_id = (String)request.getAttributes().get("bibleversion_id");
        this.book_id = (String)request.getAttributes().get("book_id");
        this.reporter = new BibleReporter();

    }

    public Representation getRepresentation(Variant variant) {
        
        List <Book> books=(List<Book>) biblestudyServce.findBooksByBookIDSectionIDandTranslationId(Long.parseLong(this.bibleversion_id),Long.parseLong(this.section_id),Long.parseLong(this.book_id));
        
        String xml ="";
        for(Book book:books)
        {
            xml = this.reporter.bookToXml(book);
        }
        return new StringRepresentation(xml);
    }
}
