package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.services.biblestudy.persistance.Chapter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import fi.passiba.services.biblestudy.persistance.Verse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;


public final class NewVerseForm extends AbstractDataPanel {



  public NewVerseForm (String id,final Chapter chapter) {

    super(id);
    final Form form = new Form("form", new CompoundPropertyModel(
        new Verse()));
    add(form);

    TextField verseNum = new TextField("verseNum", new PropertyModel(getModel(), "verseNum"));
    form.add(verseNum);

    TextArea verseText = new  TextArea("verseText", new PropertyModel(getModel(), "verseText"));
    form.add(verseText);
    form.add(new Button("saveButton") {
      @Override
      public void onSubmit() {
        Verse verse = (Verse) form.getModelObject();
        verse.setChapter(chapter);
        bibleTranslationDataRetrievalService.addVerse(verse);
        ChapterPanel chapterPanel = (ChapterPanel) NewVerseForm.this.getParent();
        chapterPanel.info("saved new verse " + verse);
        addOrReplace(chapterPanel);
      }
    });

    Button cancelButton = new Button("cancelButton") {
      @Override
      public void onSubmit() {
       ChapterPanel chapterPanel = (ChapterPanel) NewVerseForm.this.getParent();
       addOrReplace(chapterPanel);
    }};
    form.add(cancelButton);
    cancelButton.setDefaultFormProcessing(false);
  }
}