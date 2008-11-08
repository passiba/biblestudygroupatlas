package fi.passiba.groups.ui.pages.search;


import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;


public class GroupSearchPanel extends Panel {
    


    private  ValueMap properties = new ValueMap();

    public GroupSearchPanel(String id) {
        super(id);
        add(new SearchForm("groupsearchForm"));
       

    }
     private class SearchForm extends Form {

         
       private List<String> searchoptions = Arrays
			.asList("Ryhmätyyppi","Kaupunki","Nimi");
        
        public SearchForm(String id) {
            super(id);
            
            add(new RadioChoice("groupsearchcriteria", new PropertyModel(properties, "searchcriteria"), searchoptions).setSuffix(" ").setRequired(true));
            add(new TextField("groupsearchString",  new PropertyModel(properties, "searchString")).setRequired(true));
            setMarkupId("groupsearchForm");
        }

        public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchcriteria",  properties.getString("searchcriteria"));
            params.add("searchString",  properties.getString("searchString"));
            properties = new ValueMap();
            setResponsePage(ListGroups.class, params);
        }
       
       
       
    }
   
    @Override
  public boolean isVisible() 
  {
     // return BibleStudySession.get().isAuthenticated();
      return BibleStudyFaceBookSession.get().isAuthenticated();
  }

  


}
