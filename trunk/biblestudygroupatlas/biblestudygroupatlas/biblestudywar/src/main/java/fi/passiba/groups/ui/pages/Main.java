
package fi.passiba.groups.ui.pages;

import fi.passiba.groups.ui.pages.wizards.WizardPage;
import fi.passiba.groups.ui.pages.wizards.groupCreation.NewGroupWizard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;






public class Main extends  BasePage
{
   
  
  public Main()
  {
       List<AppFunctionLinkItem> functions =createAppTopFunctions();
      //creating applications functions
      ListView topfunctions = new ListView("topAppFunctions", functions) 
      {
          protected void populateItem(ListItem item) {
              AppFunctionLinkItem functionitem = (AppFunctionLinkItem) item.getModelObject();
              BookmarkablePageLink link = new BookmarkablePageLink("link",
                      functionitem.destination);
              link.add(new Label("caption", functionitem.caption));
              item.add(link);
              item.add(new Label("description", functionitem.description));
          }
      };
      add(topfunctions);
      add(new WizardLink("newGroupWizardLink", NewGroupWizard.class));
      //add(new WizardLink("newUserWizardLink", NewUserWizard.class));
      //add(new BookmarkablePageLink("addnweuser", EditPersonContact.class));
  }
  
  
  public class AppFunctionLinkItem implements Serializable {

        private String caption;
        private Class destination;
        private String description;
    }

    private List<AppFunctionLinkItem> createAppTopFunctions() {
        List<AppFunctionLinkItem> appfunctions = new ArrayList<AppFunctionLinkItem>();

        AppFunctionLinkItem function = new AppFunctionLinkItem();
        function.caption = getLocalizer().getString("group_calendar", this);
        function.description=getLocalizer().getString("group_calendar_des", this);
        function.destination = Main.class;
        appfunctions.add(function);
        
        function = new AppFunctionLinkItem();
        function.caption = getLocalizer().getString("bible_books", this);
        function.description=getLocalizer().getString("bible_books_des", this);
        function.destination = Main.class;
        appfunctions.add(function);
          
        function = new AppFunctionLinkItem();
        function.caption = getLocalizer().getString("bible_group_stat", this);
        function.description=getLocalizer().getString("bible_group_stat_des", this);
        function.destination = Main.class;
        
         function = new AppFunctionLinkItem();
        function.caption = getLocalizer().getString("bible_group_program", this);
        function.description=getLocalizer().getString("bible_group_program_des", this);
        function.destination = Main.class;
        
        appfunctions.add(function);

       
        return appfunctions;
    }
     
    
     /**
* Link to the wizard. It's an internal link instead of a bookmarkable page to help us with
* backbutton surpression. Wizards by default do not partipcate in versioning, which has the
* effect that whenever a button is clicked in the wizard, it will never result in a change of
* the redirection url. However, though that'll work just fine when you are already in the
* wizard, there is still the first access to the wizard. But if you link to the page that
* renders it using and internal link, you'll circumvent that.
*/

	private static final class WizardLink extends Link
    {
        private final Class<? extends Wizard> wizardClass;

        /**
         * Construct.
         * 
         * @param <C>
         * 
         * @param id
         *            Component id
         * @param wizardClass
         *            Class of the wizard to instantiate
         */
        public <C extends Wizard> WizardLink(String id, Class<C> wizardClass)
        {
            super(id);
            this.wizardClass = wizardClass;
        }

        /**
         * @see org.apache.wicket.markup.html.link.Link#onClick()
         */
        @Override
        public void onClick()
        {
            setResponsePage(new WizardPage(wizardClass));
        }
    }
   
}

