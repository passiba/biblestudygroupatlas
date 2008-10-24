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

    public ChapterPanel(String id, final long chapterid) {


        super(id);

        IModel model = new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return bibleTranslationDataRetrievalService.findChapterById(chapterid);
            }
        });

        setModel(model);
        init(chapterid);
        add(new Label("chapterTitle", new PropertyModel(getModel(), "chapterTitle")));
        add(new Label("chapterNum", new PropertyModel(getModel(), "chapterNum")));

    }

    private void init(long chapterid) {

        add(new VersesForm("form", getModel(), chapterid));
    }

    private class VersesForm extends Form {

        public VersesForm(String id, IModel m, long chapterid) {
            super(id, m);

            RefreshingView verses = populateBibleVerses(chapterid);
            verses.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
            add(verses);
            add(new NewButton("newButton"));
            add(new SaveButton("saveButton"));
        }

        @Override
        public boolean isVisible() {
            return BibleStudySession.get().isAuthenticated();
        }

        private final class SaveButton extends Button {

            private SaveButton(String id) {
                super(id);
            }

            @Override
            public void onSubmit() {
                Chapter chapter = (Chapter) getForm().getModelObject();
                bibleTranslationDataRetrievalService.updateChapter(chapter);
            // setResponsePage(ListContacts.class);
            //setResponsePage(EditPersonContact.this.backPage);
            }
        }

        private final class NewButton extends Button {

            private NewButton(String id) {
                super(id);
            }

            @Override
            public void onSubmit() {
                Chapter chapter = (Chapter) getForm().getModelObject();
                ChapterPanel.this.replaceWith(new NewVerseForm("bibleSessionpanel", chapter).setOutputMarkupId(true));
            }
        }

        private RefreshingView populateBibleVerses(final long chapterid) {
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


                    item.add(new TextField("verseText",
                            new PropertyModel(item.getModel(), "verseText")));
                }
            };
            return bibleVerses;
        }
    }
}


