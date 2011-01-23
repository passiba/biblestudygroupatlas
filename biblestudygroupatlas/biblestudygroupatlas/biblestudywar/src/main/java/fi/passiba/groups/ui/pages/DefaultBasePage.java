package fi.passiba.groups.ui.pages;

import fi.passiba.groups.ui.pages.search.SearchPanelPage;
import fi.passiba.groups.ui.pages.search.UserSearchPanel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


public class DefaultBasePage extends WebPage  {
    
    List<MenuItem> menu = createMenu();
    public DefaultBasePage() {
        //add(new StyleSheetReference("stylesheet", BasePage.class, "styles.css"));
        add(new SearchPanelPage("searchPanel"));
      
      
       //creating menu items
        ListView lv2 = new ListView("menus", menu) {

            protected void populateItem(ListItem item) {
                MenuItem menuitem = (MenuItem) item.getModelObject();
                BookmarkablePageLink link = new BookmarkablePageLink("link",
                        menuitem.destination);
                link.add(new Label("caption", menuitem.caption));
                item.add(link);
            }
        };
        add(lv2);
    
    }
    
     public class MenuItem implements Serializable {

        private String caption;
        private Class destination;
    }

    private List<MenuItem> createMenu() {
        List<MenuItem> menu = new ArrayList<MenuItem>();

        MenuItem item = new MenuItem();
        item.caption = getLocalizer().getString("nav_front_page", this);
        item.destination = Main.class;
        menu.add(item);

        item = new MenuItem();
        item.caption = getLocalizer().getString("navigation_groups_page", this);
        item.destination = Main.class;
        menu.add(item);
        return menu;
    }

}
