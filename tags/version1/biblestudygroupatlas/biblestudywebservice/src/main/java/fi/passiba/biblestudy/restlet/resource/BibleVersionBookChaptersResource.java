package fi.passiba.biblestudy.restlet.resource;

import org.restlet.resource.Resource;
import org.restlet.resource.Variant;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;

/**
 * This resource represents all chapters in specific book
 */
public class BibleVersionBookChaptersResource extends Resource{
      
    private String bibleversion_id,section_id,book_id;
    public BibleVersionBookChaptersResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.section_id = (String)request.getAttributes().get("section_id");
        this.bibleversion_id = (String)request.getAttributes().get("bibleversion_id");
        this.book_id = (String)request.getAttributes().get("book_id");
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
    }


    public Representation getRepresentation(Variant variant) {
       Representation rep = new StringRepresentation("returning all chapters for book : " + this.book_id +" in section: " + this.section_id + " in bibleversion: "+  this.bibleversion_id);
        return rep;
    }
}
