package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;


public class BibleTranslationPanel extends Panel {
    
    

    public BibleTranslationPanel(String id,final Bibletranslation bibletranslation) {
        super(id);
           
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return bibletranslation;
            }
        }));

        add(new Label("bibleName", new PropertyModel(getModel(), "bibleName")));
        add(new Label("bibleAbbrev", new PropertyModel(getModel(), "bibleAbbrv")));
        add(new Label("publisherName", new PropertyModel(getModel(), "publisherName")));
        add(new Label("publishedDate", new PropertyModel(getModel(), "publishedDate")));
        
      
    }
     
   
    @Override
  public boolean isVisible() 
  {
      return BibleStudySession.get().isAuthenticated();
  }

}
