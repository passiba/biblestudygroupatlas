package fi.passiba.groups.ui.pages.biblesession;

import fi.passiba.biblestudy.BibleStudySession;
import java.util.EnumSet;

public class BibleSessionPanel extends AbstractDataPanel  {

    /**
     * Constructor
     */
    public BibleSessionPanel(String id,
            String type,long itemid) {
        super(id);
        setContentPanel(DataType.valueOf(type),itemid);


    }

    void  setContentPanel(DataType type,long itemId) {
        
        if(type==null)
        {
            add(new BibleDataTreePanel("bibleSessionpanel"));
        }
        
       if(EnumSet.of(DataType.BIBLETRANSLATION).contains(type))
       {
            addOrReplace(new BibleTranslationPanel("bibleSessionpanel",itemId));
       }
       if(EnumSet.of(DataType.BOOK).contains(type))
       {
            addOrReplace(new BookPanel("bibleSessionpanel",itemId));
       }
       if(EnumSet.of(DataType.CHAPTER).contains(type))
       {
            addOrReplace(new ChapterPanel("bibleSessionpanel",itemId,getPage()).setOutputMarkupId(true));
       }
       
    }

    @Override
    public boolean isVisible() {
        return BibleStudySession.get().isAuthenticated();
       //return BibleStudyFaceBookSession.get().isAuthenticated();
    }
}