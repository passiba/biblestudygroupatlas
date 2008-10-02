package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import org.apache.wicket.markup.html.panel.Panel;

public class BibleSessionPanel extends Panel {

    /**
     * Constructor
     */
    public BibleSessionPanel(String id,Object obj) {
        super(id);

        setContentPanel(obj);


    }

    void  setContentPanel(Object obj) {
        
        
       if(obj!=null && obj instanceof Bibletranslation)
       {
            addOrReplace(new BibleTranslationPanel("bibleSessionpanel",(Bibletranslation)obj));
       }
    }

    @Override
    public boolean isVisible() {
        return BibleStudySession.get().isAuthenticated();
    }
}