package fi.passiba.groups.ui.pages;

import fi.passiba.biblestudy.BibleStudySession;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;



public class LogOutPanel extends Panel {

  public LogOutPanel(String id, Class<? extends Page> logoutPageClass){

    super(id);
   
    add(new Link("signout") {
      @Override
      public boolean isVisible() {
        return BibleStudySession.get().isAuthenticated();
      }
      @Override
      public void onClick() {
        BibleStudySession.get().invalidate();
           setResponsePage(Home.class);
      }
    });
    add(new Link("signin") {

      @Override
      public void onClick() {
        throw new RestartResponseAtInterceptPageException(
            Home.class);
      }

      @Override
      public boolean isVisible() {
        return !BibleStudySession.get().isAuthenticated();
      }
    });
    add(new BookmarkablePageLink("help", Main.class));
  }
}
