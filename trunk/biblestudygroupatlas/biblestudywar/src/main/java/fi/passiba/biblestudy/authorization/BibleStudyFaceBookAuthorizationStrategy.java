package fi.passiba.biblestudy.authorization;

import fi.passiba.biblestudy.BibleStudyApplication;
import fi.passiba.biblestudy.authorization.facebook.FaceBookAuthHandler;
import fi.passiba.groups.ui.pages.ProtectedPage;
import com.google.code.facebookapi.FacebookXmlRestClient;
import javax.security.auth.login.FailedLoginException;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.request.target.basic.RedirectRequestTarget;

public final class BibleStudyFaceBookAuthorizationStrategy implements
    IAuthorizationStrategy,
    IUnauthorizedComponentInstantiationListener {




    public static final String CLIENT = "auth.client";

    

  public boolean isActionAuthorized(Component component, Action action) {

    /*if (action.equals(Component.RENDER)) {
      Class<? extends Component> c = component.getClass();
      AdminOnly adminOnly = c.getAnnotation(AdminOnly.class);
      if (adminOnly != null) {
          
     
         String role = BibleStudyFaceBookSession.get().getPerson().getFk_userid().getRolename();
        
        return (BibleStudyFaceBookSession.get().getPerson()!=null && role != null
                && role !=null 
                && (role.equals("Admin"))||role.equals("User"));
      }
    }*/
    return true;
  }

  public boolean isInstantiationAuthorized(Class componentClass) {

    if (ProtectedPage.class.isAssignableFrom(componentClass)) {
      //return  BibleStudyFaceBookSession.get().isAuthenticated();
    }

    return true;
  }

  public void onUnauthorizedInstantiation(Component component) {
   // throw new RestartResponseAtInterceptPageException(
     //   Home.class);
      
        }
  private void forceLogin(Page page) {

        page.getRequestCycle().setRequestTarget(new RedirectRequestTarget("http://www.facebook.com/login.php?api_key=" + BibleStudyApplication.get().getFaceBookAPIkey() + "&v=1.0"));

    }

}