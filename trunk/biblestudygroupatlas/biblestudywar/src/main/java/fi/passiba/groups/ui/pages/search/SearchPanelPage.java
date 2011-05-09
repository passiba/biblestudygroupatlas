package fi.passiba.groups.ui.pages.search;


import fi.passiba.biblestudy.BibleStudySession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class SearchPanelPage extends Panel {

    private boolean inUserSearchMode = false;

    /**
     * Constructor
     */
    public SearchPanelPage(String id) {
        super(id);
        final String group = getLocalizer().getString("group", this);
        final String user = getLocalizer().getString("user", this);
        add(new UserSearchPanel("searchpanel"));
        Link searchLink = new Link("searchLink") {

            @Override
            public void onClick() {
                inUserSearchMode = !inUserSearchMode;
                setSearchContentPanel();
            }
        };
        add(searchLink);
        searchLink.add(new Label("searchLabel", new AbstractReadOnlyModel() {

            @Override
            public Object getObject() {

                return inUserSearchMode ? "[" + user + "] ": "[" + group + "] " ;
            }
        }));


    }

    void setSearchContentPanel() {
        if (inUserSearchMode) {
            addOrReplace(new UserSearchPanel("searchpanel"));
        } else {
            addOrReplace(new GroupSearchPanel("searchpanel"));
        }
    }

    @Override
    public boolean isVisible() {
       // return BibleStudySession.get().isAuthenticated();
         return BibleStudySession.get().isAuthenticated();
    }
}