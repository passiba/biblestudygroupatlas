package fi.passiba.groups.ui.pages.biblesession;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.Verse;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
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
                return bibleTranslationDataRetrievalService.findChapterById(chapterid);
            }
        }));

        add(new Label("chapterTitle", new PropertyModel(getModel(), "chapterTitle")));
        add(new Label("chapterNum", new PropertyModel(getModel(), "chapterNum")));
    
        Form form = new Form("form");
        add(form);
        form.add(new Button("newButton") {

            @Override
            public void onSubmit() {
              //  ChapterPanel.this.replaceWith(new NewVerseForm(
                //        "bibleSessionpanel", (Chapter)getModel().getObject()).setOutputMarkupId(true));
            }
        });
        form.add(new Button("saveButton") {

            @Override
            public void onSubmit() {
                bibleTranslationDataRetrievalService.updateChapter((Chapter)getModel().getObject());

                // ChapterPanel.this.replaceWith(new ChapterPanel("bibleSessionpanel",chapterid).setOutputMarkupId(true));
                //info("chapter updated");
            }
        });


        RefreshingView verses=populateBibleVerses(chapterid);
        verses.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());

        form.add(verses);
        
      
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

                item.add(new TextField("verseNum",
                        new PropertyModel(item.getModel(), "verseNum")));


                item.add(new  TextField("verseText",
                        new PropertyModel(item.getModel(), "verseText")));
            }
        };
        return   bibleVerses;
    }

}
