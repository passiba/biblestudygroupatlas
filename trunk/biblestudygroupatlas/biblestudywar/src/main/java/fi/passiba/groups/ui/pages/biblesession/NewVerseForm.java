package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.services.biblestudy.persistance.Chapter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

import fi.passiba.services.biblestudy.persistance.Verse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public final class NewVerseForm extends AbstractDataPanel {

    public NewVerseForm(String id, Chapter chapter) {

        super(id);

        IModel model = new CompoundPropertyModel(new LoadableDetachableModel() {
            @Override
            public Object load() {
                return new Verse();
            }
        });
        setModel(model);
        init(chapter);
    }

    private void init(Chapter chapter) {

        add(new VerseForm("form", getModel(), chapter));

    }

    private final class VerseForm extends Form {

        public VerseForm(String id, IModel m, Chapter chapter) {
            super(id, m);
            add(new CancelButton("cancelButton",chapter.getId()));
            add(new SaveButton("saveButton", chapter));
            TextField verseNum = new TextField("verseNum", new PropertyModel(getModel(), "verseNum"));
            add(verseNum);
            TextArea verseText = new TextArea("verseText", new PropertyModel(getModel(), "verseText"));
            add(verseText);
        }
        private final class SaveButton extends Button {

            private Chapter chapter;
            private SaveButton(String id, Chapter chapter) {
                super(id);
                this.chapter=chapter;
            }

            @Override
            public void onSubmit() {
                Verse verse = (Verse) getForm().getModelObject();
                verse.setChapter(chapter);
                bibleTranslationDataRetrievalService.addVerse(verse);
                NewVerseForm.this.replaceWith(new ChapterPanel("bibleSessionpanel",chapter.getId()).setOutputMarkupId(true));
            }
        }

        private final class CancelButton extends Button {

            private long chapterid;
            private CancelButton(String id,long chapterid) {
                super(id);
                this.chapterid=chapterid;
            }

            @Override
            public void onSubmit() {
                NewVerseForm.this.replaceWith(new ChapterPanel("bibleSessionpanel",chapterid).setOutputMarkupId(true));
            }
        }
    }
}