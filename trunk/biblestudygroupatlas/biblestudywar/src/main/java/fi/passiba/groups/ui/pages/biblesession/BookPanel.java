package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.services.biblestudy.persistance.Booksection;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;


public class BookPanel  extends AbstractDataPanel {
    
    
    
    public BookPanel(String id,final long bookid) {
        
        super(id);
           
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return  bibleTranslationDataRetrievalService.findBookById(bookid);
            }
        }));

        add(new Label("bookTitle", new PropertyModel(getModel(), "bookText")));
        add(new Label("bookNum", new PropertyModel(getModel(), "bookNum")));
        
        IModel booksection = new LoadableDetachableModel() {

            protected Object load() {

                    Booksection booksection = (Booksection) new PropertyModel(getModel(), "booksection").getObject();
                    long booksectionid = booksection.getId();
                    booksection =  bibleTranslationDataRetrievalService.findBookSectionById(booksectionid);
                    return booksection;
            }
        };
        
        add(new Label("booksection", new PropertyModel(booksection, "section")));
        
      
    }
     
   
    @Override
  public boolean isVisible() 
  {
     // return BibleStudySession.get().isAuthenticated();
      return BibleStudyFaceBookSession.get().isAuthenticated();
  }

}
