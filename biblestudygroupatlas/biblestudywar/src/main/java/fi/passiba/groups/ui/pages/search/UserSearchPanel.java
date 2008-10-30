package fi.passiba.groups.ui.pages.search;


import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.biblestudy.BibleStudySession;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;


public class UserSearchPanel extends Panel {
    
    
    private final ValueMap properties = new ValueMap();

    public UserSearchPanel(String id) {
        super(id);
        add(new SearchForm("searchForm"));
    }
     private class SearchForm extends Form {

         
       private List<String> searchoptions = Arrays
			.asList("Rooli","Käyttäjänimi","Paikkakunta");
        
        public SearchForm(String id) {
            super(id);
            
            add(new RadioChoice("searchcriteria", new PropertyModel(properties, "searchcriteria"), searchoptions).setSuffix(" ").setRequired(true));
            add(new TextField("searchString",  new PropertyModel(properties, "searchString")).setRequired(true));
            setMarkupId("search-form");
        }

        public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchcriteria",  properties.getString("searchcriteria"));
            params.add("searchString",  properties.getString("searchString"));
            setResponsePage(ListPersons.class, params);
        }
       
       
       
    }
   
    @Override
  public boolean isVisible() 
  {
      //return BibleStudySession.get().isAuthenticated();
      return BibleStudyFaceBookSession.get().isAuthenticated();
  }

}
