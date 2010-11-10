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
 * This resource represents all verses in specific chapter in bible
 */
public class BibleVersionBookChapterVersesResource extends Resource{

    private String bibleversion_id,  section_id,  book_id,  chapter_id;
    public BibleVersionBookChapterVersesResource(Context context, Request request, Response response) {
        super(context, request, response);

        this.getVariants().add(new Variant(MediaType.TEXT_XML));
        this.bibleversion_id = (String) request.getAttributes().get("bibleversion_id");
        this.section_id = (String) request.getAttributes().get("section_id");
        this.book_id = (String) request.getAttributes().get("book_id");
        this.chapter_id = (String) request.getAttributes().get("chapter_id");
    }


    public Representation getRepresentation(Variant variant) {
        Representation rep = new StringRepresentation("Returning all verses in chapter: " +this.chapter_id+ " in book: " +this.book_id +" in section:" +this.section_id+ " in bibleversion "+this.bibleversion_id);
        return rep;
    }
}
