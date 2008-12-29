package fi.passiba.groups.ui.pages.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.pages.BasePage;
import fi.passiba.services.biblestudy.persistance.Chapter;
import fi.passiba.services.biblestudy.persistance.Verse;

import fi.passiba.services.search.ISearchService;
import org.apache.lucene.queryParser.ParseException;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ListVerses extends BasePage {

    @SpringBean
    private ISearchService searchService;

    private int startPage=1,maxVerses=1000;

    private final Page backPage;
    public ListVerses(Page backPage,PageParameters params) {

       this.backPage=backPage;
       final String searchCriteria = params.getString("searchcriteria");
       final String searchString = params.getString("searchString");

       init(searchCriteria,searchString);


    }
	
    
    private void init(String searchCriteria,String searchString) {

        add(new VersesForm("form", searchCriteria, searchString));
    }

    private final class VersesForm extends Form {

        public VersesForm(String id, String searchCriteria,String searchString) {
            super(id);

            RefreshingView verses = populateBibleVerses(searchCriteria,searchString);
            verses.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
            add(verses);

            CancelButton cancel= new CancelButton("cancel");
            add(cancel);
              
            
        }

        private RefreshingView populateBibleVerses(final String searchCriteria,final String searchString) {
            RefreshingView bibleVerses = new RefreshingView("bibleVerses") {

                List<Verse> result = new ArrayList<Verse>(0);

                @Override
                protected Iterator getItemModels() {
                    try 
                    {

                        if(searchCriteria !=null && searchCriteria.equals(Constants.VerseSearchOption.VERSE.getOption()))
                        {
                            result = searchService.findVersesByContext(searchString, startPage, maxVerses);
                        }
                    } catch (ParseException ex) {
                       throw new WicketRuntimeException(ex);
                    }


                    return new DomainModelIteratorAdaptor<Verse>(result.iterator()) {

                        @Override
                        protected IModel model(final Object object) {

                            return new HashcodeEnabledCompoundPropertyModel((Verse) object);
                        }
                    };
                }

                @Override
                protected void populateItem(Item item) {

                Chapter chapter = (Chapter) new PropertyModel(item.getModel(), "chapter").getObject();
                final long chapterid = chapter.getId();
                Link viewchapter = new Link("chapterView") {
                    @Override
                    public void onClick() {
                        setResponsePage(new ListChapterVerses(chapterid));
                    }
                };
                viewchapter.add(new Label("verseNum",
                            new PropertyModel(item.getModel(), "verseNum")));
                item.add(viewchapter);

              
                item.add(new MultiLineLabel("verseText",
                            new PropertyModel(item.getModel(), "verseText")));
                }
            };
            return bibleVerses;
        }

        private final class CancelButton extends Button {

            private static final long serialVersionUID = 1L;

            private CancelButton(String id) {
                super(id);

                setDefaultFormProcessing(false);
            }

            @Override
            public void onSubmit() {
                if (ListVerses.this.backPage != null) {
                     setResponsePage(ListVerses.this.backPage);
                } 
            }
        }
    }
    
}


