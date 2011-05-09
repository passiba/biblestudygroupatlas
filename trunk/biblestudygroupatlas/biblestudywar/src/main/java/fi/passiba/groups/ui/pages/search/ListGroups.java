package fi.passiba.groups.ui.pages.search;

import org.apache.wicket.PageParameters;
import fi.passiba.groups.ui.pages.BasePage;


public class ListGroups extends BasePage {


    public ListGroups(PageParameters params) {

       final String searchCriteria = params.getString("searchcriteria");
       final String searchString = params.getString("searchString");

       final ListGroupsPanel groupsListPanel=new ListGroupsPanel ("usergroupsPanel",searchCriteria,searchString,getPage());
        add(groupsListPanel);

    }

   
}
