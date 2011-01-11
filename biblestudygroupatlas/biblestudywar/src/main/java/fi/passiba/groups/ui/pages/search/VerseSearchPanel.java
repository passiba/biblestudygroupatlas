package fi.passiba.groups.ui.pages.search;



import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.Constants;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;


public class VerseSearchPanel extends Panel {
    


    private  ValueMap properties = new ValueMap();

    public VerseSearchPanel(String id) {
        super(id);
        add(new SearchForm("versesearchForm"));
       

    }
     private class SearchForm extends Form {

         
       private List<String> searchoptions = Constants.VerseSearchOption.getVerseSearchOptions();
        
        public SearchForm(String id) {
            super(id);
            
            add(new RadioChoice("versesearchcriteria", new PropertyModel(properties, "searchcriteria"), searchoptions).setSuffix(" ").setRequired(true));
            add(new TextField("versesearchString",  new PropertyModel(properties, "searchString")).setRequired(true));
            setMarkupId("versesearchForm");
        }

        public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchcriteria",  properties.getString("searchcriteria"));
            params.add("searchString",  properties.getString("searchString"));
            properties = new ValueMap();
            setResponsePage(new ListVerses(getPage(), params));
        }
       
       
       
    }
   
    @Override
  public boolean isVisible() 
  {
      return BibleStudySession.get().isAuthenticated();
      //return BibleStudyFaceBookSession.get().isAuthenticated();
  }

  


}
