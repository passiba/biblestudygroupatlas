package fi.passiba.groups.ui.pages;
import fi.passiba.groups.ui.pages.search.ListPersons;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;


public class Template extends Border
{
    public Template(final String id)
   {
      super(id);
      add(new BookmarkablePageLink("frontPage", Main.class));
      add(new BookmarkablePageLink("groupSelection", Main.class));
      add(new BookmarkablePageLink("groupInfo", Main.class));
      add(new BookmarkablePageLink("readBibleverses", Main.class));
      add(new BookmarkablePageLink("users", Main.class));
      add(new BookmarkablePageLink("help", Main.class));
      
      //add(new BookmarkablePageLink("settings", Password.class));
      add(new Link("logout")
      {
         @Override
         public void onClick()
         {
            //identity.logout();
            setResponsePage(Home.class);
         }
      });
      
     // add(new Label("userName", user.getName()));
       add(new SearchForm("searchForm"));
   }
    private class SearchForm extends Form {

        private String searchString;
        
       private List<String> searchoptions = Arrays
			.asList("Piiri","Paikkakunta","Hakusana");
        
        public SearchForm(String id) {
            super(id);
           // add(new BookmarkablePageLink("addContact", EditContact.class));
            
            add(new RadioChoice("searchcriteria",new PropertyModel(searchString,"searchcriteria"),searchoptions));
           // add(new TextField("searchString", new PropertyModel(this, "searchString")));
            setMarkupId("search-form");
        }

        public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchString", getSearchString());
            setResponsePage(ListPersons.class, params);
        }

        public String getSearchString() {
            return searchString;
        }

        public void setSearchString(String searchString) {
            this.searchString = searchString;
        }
    }
   

}
