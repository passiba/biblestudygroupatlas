package fi.passiba.biblestudy.restlet.resource;

import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import fi.passiba.biblestudy.restlet.IBibleStudyService;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

/**
 * This resource preprents an individual bible version /bibletranslation instance
 */
public class BibleVersionResource extends Resource {

    private String bibleversion_id;
    
    @SpringBean
    private IBibleStudyService biblestudyServce;
    private BibleReporter reporter;

    /**
     * @param context
     * @param request
     * @param response
     */
    public BibleVersionResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));  
        this.bibleversion_id = (String) request.getAttributes().get("bibleversion_id");
        this.reporter = new BibleReporter();
    }

    /**
     * @param variant
     * @return
     */
    public Representation getRepresentation(Variant variant) {
        
        Bibletranslation bibletranslation=biblestudyServce.findBibleVersionByTranslationId(Long.parseLong(this.bibleversion_id));
        String xml =reporter.bibletranslationToXml(bibletranslation);
        return new StringRepresentation(xml);
    }
}
