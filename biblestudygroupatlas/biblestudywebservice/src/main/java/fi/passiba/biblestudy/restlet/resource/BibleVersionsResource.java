package fi.passiba.biblestudy.restlet.resource;

import fi.passiba.biblestudy.restlet.IBibleStudyService;
import fi.passiba.biblestudy.restlet.resource.xml.BibleReporter;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import java.util.List;
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
 * This resource preprents all BibleVersion/Bibletranslation instances
 */
public class BibleVersionsResource extends Resource {

    @SpringBean
    IBibleStudyService biblestudyServce;
    private BibleReporter reporter;
    /**
     * @param context
     * @param request
     * @param response
     */
    public BibleVersionsResource(Context context, Request request, Response response) {
        super(context, request, response);
        this.getVariants().add(new Variant(MediaType.TEXT_XML));  
        this.reporter= new BibleReporter();
      
    }

    /**
     * @param variant
     * @return
     */
    public Representation getRepresentation(Variant variant) {
        
        List <Bibletranslation> bibleVersions=(List<Bibletranslation>) biblestudyServce.findBibleVersionByTranslations();
        String xml = this.reporter.bibletranslationsToXml(bibleVersions);
        return new StringRepresentation(xml);
    }
}
