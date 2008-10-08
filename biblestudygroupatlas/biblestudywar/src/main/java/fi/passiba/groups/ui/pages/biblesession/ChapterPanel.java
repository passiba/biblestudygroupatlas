package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.services.biblestudy.persistance.Chapter;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;


public class ChapterPanel extends AbstractDataPanel {
    
    
   
    public ChapterPanel(String id,final long chapterid) {
        
        super(id);
           
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return  bibleTranslationDataRetrievalService.findChapterById(chapterid);
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
