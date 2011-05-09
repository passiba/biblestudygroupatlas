package fi.passiba.biblestudy.authorization;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.pages.Home;
import fi.passiba.groups.ui.pages.ProtectedPage;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

public final class BibleStudyAuthorizationStrategy implements
    IAuthorizationStrategy,
    IUnauthorizedComponentInstantiationListener {

  public boolean isActionAuthorized(Component component, Action action) {

    if (action.equals(Component.RENDER)) {
      Class<? extends Component> c = component.getClass();
      AdminOnly adminOnly = c.getAnnotation(AdminOnly.class);
      if (adminOnly != null) {
          
     
         String role = BibleStudySession.get().getPerson().getFk_userid().getRolename(); 
        
        return (BibleStudySession.get().getPerson()!=null && role != null 
                && role !=null 
                && (role.equals("Admin"))||role.equals("User"));
      }
    }
    return true;
  }

  public boolean isInstantiationAuthorized(Class componentClass) {

    if (ProtectedPage.class.isAssignableFrom(componentClass)) {
      return BibleStudySession.get().isAuthenticated();
    }

    return true;
  }

  public void onUnauthorizedInstantiation(Component component) {
    throw new RestartResponseAtInterceptPageException(
        Home.class);
  }
}
