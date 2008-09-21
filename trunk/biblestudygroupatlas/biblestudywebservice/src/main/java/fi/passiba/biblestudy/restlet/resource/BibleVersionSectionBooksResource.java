package fi.passiba.biblestudy.restlet.resource;

import org.restlet.resource.Resource;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;
import org.restlet.resource.StringRepresentation;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.MediaType;

/**
 * This resource represents all books for a particular booksection
 */
public class BibleVersionSectionBooksResource extends Resource {
    private String bibleversion_id,section_id;

    public BibleVersionSectionBooksResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.bibleversion_id = (String)request.getAttributes().get("bibleversion_id");
        this.section_id = (String)request.getAttributes().get("section_id");

    }

    public Representation getRepresentation(Variant variant) {
        Representation rep = new StringRepresentation("returning all book for sections " + this.section_id +
        "  is in bibletranslation: " +  this.bibleversion_id);
        return rep;
    }
}
