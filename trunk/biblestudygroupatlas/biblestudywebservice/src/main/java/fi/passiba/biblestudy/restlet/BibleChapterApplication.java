package fi.passiba.biblestudy.restlet;

import fi.passiba.biblestudy.restlet.resource.BibleVersionSectionBookResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionSectionBooksResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionBookChapterResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionBookChapterVerseResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionBookChapterVersesResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionBookChaptersResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionSectionResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionSectionsResource;
import fi.passiba.biblestudy.restlet.resource.BibleVersionsResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.Context;


public class BibleChapterApplication extends Application{

    public BibleChapterApplication(Context context) {
        super(context);
    }

    public Restlet createRoot() {
        Router router = new Router(this.getContext());

        router.attach("/bibleversion", BibleVersionsResource.class);
        router.attach("/bibleversion/{bibleversion_id}",  BibleVersionResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section", BibleVersionSectionsResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}", BibleVersionSectionResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book", BibleVersionSectionBooksResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book/{bookd_id}", BibleVersionSectionBookResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book/{bookd_id}/chapter", BibleVersionBookChaptersResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book/{bookd_id}/chapter/{chapter_id}", BibleVersionBookChapterResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book/{bookd_id}/chapter/{chapter_id}/verse", BibleVersionBookChapterVersesResource.class);
        router.attach("/bibleversion/{bibleversion_id}/section/{section_id}/book/{bookd_id}/chapter/{chapter_id}/verse/{verse_id}", BibleVersionBookChapterVerseResource.class);
     
        return router;
    }
}
