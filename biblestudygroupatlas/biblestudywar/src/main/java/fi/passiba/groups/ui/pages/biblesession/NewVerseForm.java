package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.services.biblestudy.persistance.Chapter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;

import fi.passiba.services.biblestudy.persistance.Verse;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.dojo.markup.html.inlineeditbox.DojoInlineEditBox;

public final class NewVerseForm extends AbstractDataPanel {

    private Verse verse= new Verse();
    private  Component dojoInlineEditBox;
    public NewVerseForm(String id, Chapter chapter) {

        super(id);
        verse.setVerseText("Jakeen teksti");

        IModel model = new CompoundPropertyModel(verse);
        setModel(model);
        init(chapter);
    }
    public NewVerseForm(String id,final Verse verse) {

        super(id);
        IModel model = new CompoundPropertyModel(new LoadableDetachableModel()

        {
            @Override
            protected Object load() {
              return verse;
            }
        }


                );
        setModel(model);
        init(verse.getChapter());
    }

    private void init(Chapter chapter) {
        
         final VerseForm verseForm=new VerseForm("form", getModel(), chapter);
         dojoInlineEditBox = new DojoInlineEditBox("verseText", new PropertyModel(getModel(), "verseText")) {

                protected void onSave(AjaxRequestTarget target) {
                   // verse = (Verse) verseForm.getModelObject();
                    verse.setVerseText(dojoInlineEditBox.getModelObjectAsString());
                    target.addComponent(dojoInlineEditBox);
                }
         };
         dojoInlineEditBox.setVisible(true);
		 dojoInlineEditBox.setOutputMarkupPlaceholderTag(true);
         verseForm.add(dojoInlineEditBox);
         add(verseForm);

    }

    private final class VerseForm extends Form {


        
        public VerseForm(String id, IModel m, Chapter chapter) {
            super(id, m);
            add(new CancelButton("cancelButton", chapter.getId()));
            add(new SaveButton("saveButton", chapter));
            TextField verseNum = new TextField("verseNum", new PropertyModel(getModel(), "verseNum"));
            //verseNum.setRequired(true);
            add(verseNum);
            // TextArea verseText = new TextArea("verseText", new PropertyModel(getModel(), "verseText"));

           
            //verseText.setRequired(true);
        }
        private final class SaveButton extends Button {

            private Chapter chapter;
            private SaveButton(String id, Chapter chapter) {
                super(id);
                this.chapter=chapter;
            }

            @Override
            public void onSubmit() {
                verse = (Verse) getForm().getModelObject();
                verse.setVerseText(NewVerseForm.this.dojoInlineEditBox.getModelObjectAsString());
                verse.setChapter(chapter);
                bibleTranslationDataRetrievalService.updateVerse(verse);
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