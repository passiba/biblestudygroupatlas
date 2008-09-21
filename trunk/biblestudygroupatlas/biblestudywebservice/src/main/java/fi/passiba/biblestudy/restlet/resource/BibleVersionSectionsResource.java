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
 * This resource reprents all sections for a particular bibleversion, namely new and old testament
 */
public class BibleVersionSectionsResource extends Resource {

    private String bibleversion_id;
   

    public BibleVersionSectionsResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.bibleversion_id = (String)request.getAttributes().get("bibleversion_id");
    }

    public Representation getRepresentation(Variant variant) {
        Representation rep = new StringRepresentation("returning all sections for bibleversion : " + this.bibleversion_id);
        return rep;
    }
}
