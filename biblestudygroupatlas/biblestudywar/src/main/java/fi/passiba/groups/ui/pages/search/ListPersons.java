package fi.passiba.groups.ui.pages.search;

import org.apache.wicket.PageParameters;
import fi.passiba.groups.ui.pages.BasePage;


public class ListPersons extends BasePage {

    
    public ListPersons(PageParameters params) {

        final String searchCriteria = params.getString("searchcriteria");
        final String searchString = params.getString("searchString");

        ListPersonsPanel personListPanel=new ListPersonsPanel ("userlistPanel",searchCriteria,searchString,getPage());
        add(personListPanel);
      
    }

  
}
