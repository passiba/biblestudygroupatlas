package fi.passiba.groups.ui.pages.search;

import fi.passiba.groups.ui.pages.BasePage;
import fi.passiba.groups.ui.pages.biblesession.ChapterPanel;


public class ListChapterVerses extends BasePage {


    public ListChapterVerses(long chapterid) {

       ChapterPanel chapter=new ChapterPanel("chapterpanel",chapterid,getPage());
       add(chapter);
    }

   
}
