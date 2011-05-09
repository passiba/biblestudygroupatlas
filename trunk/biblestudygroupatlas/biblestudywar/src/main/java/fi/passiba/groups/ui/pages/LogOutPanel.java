package fi.passiba.groups.ui.pages;

import fi.passiba.biblestudy.BibleStudyApplication;
import fi.passiba.biblestudy.BibleStudySession;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;



public class LogOutPanel extends Panel {

  public LogOutPanel(String id, Class<? extends Page> logoutPageClass){

    super(id);
   
    add(new Link("signout") {
      @Override
      public boolean isVisible() {
       // return BibleStudySession.get().isAuthenticated();
           return BibleStudySession.get().isAuthenticated();
      }
      @Override
      public void onClick() {
       BibleStudySession.get().invalidate();
          Logout(this.getPage());
      }
    });
    add(new Link("signin") {

      @Override
      public void onClick() {
        //throw new RestartResponseAtInterceptPageException(
           // Home.class);
          Logout(this.getPage());
      }

      @Override
      public boolean isVisible() {
        return !BibleStudySession.get().isAuthenticated();
           //return ! BibleStudyFaceBookSession.get().isAuthenticated();
      }
    });
    add(new BookmarkablePageLink("help", Main.class));


  }
  private void Logout(Page page) {

        page.getRequestCycle().setRequestTarget(new RedirectRequestTarget("http://www.facebook.com/login.php?api_key=" + BibleStudyApplication.get().getFaceBookAPIkey() + "&v=1.0"));

  }
}
