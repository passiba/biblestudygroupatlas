package fi.passiba.groups.ui.pages.biblesession;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import fi.passiba.groups.ui.pages.search.ListPersons;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.groups.ui.pages.user.ViewPersonContact;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.Verse;
import fi.passiba.services.persistance.Person;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
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
        RefreshingView verses=populateBibleVerses(chapterid);
        verses.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        add(verses);
      
    }
     
   
    @Override
  public boolean isVisible() 
  {
      return BibleStudySession.get().isAuthenticated();
  }
    
  
  private RefreshingView populateBibleVerses(final long chapterid)
  {
            RefreshingView bibleVerses = new RefreshingView("bibleVerses") {

            List<Verse> result = new ArrayList<Verse>(0);

            @Override
            protected Iterator getItemModels() {
                
                  result = bibleTranslationDataRetrievalService.findVersesByChapterId(chapterid);
                   
                
                return new DomainModelIteratorAdaptor<Verse>(result.iterator()) {

                    @Override
                    protected IModel model(final Object object) {
                        
                      return new HashcodeEnabledCompoundPropertyModel((Verse) object);
                    }
                };
            }
            @Override
            protected void populateItem(Item item) {

                item.add(new Label("verseNum",
                        new PropertyModel(item.getModel(), "verseNum")));


                item.add(new Label("verseText",
                        new PropertyModel(item.getModel(), "verseText")));

                
            }
        };
        return   bibleVerses;
    }

}
