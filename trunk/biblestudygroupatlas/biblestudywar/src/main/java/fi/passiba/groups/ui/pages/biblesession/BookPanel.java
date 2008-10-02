package fi.passiba.groups.ui.pages.biblesession;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.biblestudy.services.datamining.IBibleDataMining;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.biblestudy.persistance.Booksection;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class BookPanel extends Panel {
    
    
    @SpringBean
    private IBibleDataMining bibleTranslationDataRetrievalService;
    public BookPanel(String id,final Book book) {
        
        super(id);
           
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return book;
            }
        }));

        add(new Label("booktitle", new PropertyModel(getModel(), "bookText")));
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
      return BibleStudySession.get().isAuthenticated();
  }

}
