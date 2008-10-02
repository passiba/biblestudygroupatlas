package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.biblestudy.services.datamining.IBibleDataMining;
import fi.passiba.services.biblestudy.persistance.Chapter;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class ChapterPanel extends Panel {
    
    
    @SpringBean
    private IBibleDataMining bibleTranslationDataRetrievalService;
    public ChapterPanel(String id,final Chapter chapter) {
        
        super(id);
           
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return chapter;
            }
        }));

        add(new Label("chapterTitle", new PropertyModel(getModel(), "chapterTitle")));
        add(new Label("chapterNum", new PropertyModel(getModel(), "chapterNum")));
        
      
    }
     
   
    @Override
  public boolean isVisible() 
  {
      return BibleStudySession.get().isAuthenticated();
  }

}
