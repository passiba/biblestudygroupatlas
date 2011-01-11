package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.biblestudy.BibleStudySession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;


public class BibleTranslationPanel extends AbstractDataPanel {
    
    

    public BibleTranslationPanel(String id,final long translationid) {
        super(id);
           
       setDefaultModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
               return  bibleTranslationDataRetrievalService.findBibleTranslationById(translationid);
            }
        }));

        add(new Label("bibleName", new PropertyModel(getDefaultModel(), "bibleName")));
        add(new Label("bibleAbbrev", new PropertyModel(getDefaultModel(), "bibleAbbrv")));
        add(new Label("publisherName", new PropertyModel(getDefaultModel(), "publisherName")));
        add(new Label("publishedDate", new PropertyModel(getDefaultModel(), "publishedDate")));
        
      
    }
     
   
    @Override
  public boolean isVisible() 
  {
      //return BibleStudySession.get().isAuthenticated();
      return BibleStudyFaceBookSession.get().isAuthenticated();
  }

}
