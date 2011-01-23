package fi.passiba.groups.ui.pages.search;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.services.search.ISearchService;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;


public class UserSearchPanel extends Panel {
    
    @SpringBean
    private ISearchService searchService;

    private ValueMap properties = new ValueMap();

    public UserSearchPanel(String id) {
        super(id);
        init();
    }
    private void init()
    {
        add(new SearchForm("searchForm"));
    }
     private class SearchForm extends Form {

         
       private List<String> searchoptions = Constants.PersonSearchOption.getPersonSearchOptions();
        
        public SearchForm(String id) {
            super(id);
            
            add(new RadioChoice("searchcriteria", new PropertyModel(properties, "searchcriteria"), searchoptions).setSuffix(" ").setRequired(true));
            add(new TextField("searchString",  new PropertyModel(properties, "searchString")).setRequired(true));
            setMarkupId("search-form");

             add(new Button("Find") {

                    public void onSubmit() {
                        PageParameters params = new PageParameters();
                        params.add("searchcriteria",  properties.getString("searchcriteria"));
                        params.add("searchString",  properties.getString("searchString"));
                        properties = new ValueMap();
                        setResponsePage(ListPersons.class, params);


                    }
            });
        }

        /*public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchcriteria",  properties.getString("searchcriteria"));
            params.add("searchString",  properties.getString("searchString"));
            properties = new ValueMap();
            setResponsePage(ListPersons.class, params);
        }*/
       
       
       
    }
   
    @Override
  public boolean isVisible() 
  {
      return BibleStudySession.get().isAuthenticated();
      //return BibleStudyFaceBookSession.get().isAuthenticated();
  }

}
