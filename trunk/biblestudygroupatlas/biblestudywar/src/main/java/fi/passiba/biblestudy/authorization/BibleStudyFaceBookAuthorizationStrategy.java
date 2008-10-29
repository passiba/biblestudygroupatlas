package fi.passiba.biblestudy.authorization;

import com.google.code.facebookapi.FacebookRestClient;
import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.biblestudy.authorization.facebook.FaceBookAuthHandler;
import fi.passiba.groups.ui.pages.ProtectedPage;

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

    private String _apiKey = ""; //replace this
    private String _secretKey = ""; //replace this


  public boolean isActionAuthorized(Component component, Action action) {

    if (action.equals(Component.RENDER)) {
      Class<? extends Component> c = component.getClass();
      AdminOnly adminOnly = c.getAnnotation(AdminOnly.class);
      if (adminOnly != null) {
          
     
         String role = BibleStudyFaceBookSession.get().getPerson().getFk_userid().getRolename();
        
        return (BibleStudyFaceBookSession.get().getPerson()!=null && role != null
                && role !=null 
                && (role.equals("Admin"))||role.equals("User"));
      }
    }
    return true;
  }

  public boolean isInstantiationAuthorized(Class componentClass) {

    if (ProtectedPage.class.isAssignableFrom(componentClass)) {
      return  BibleStudyFaceBookSession.get().isAuthenticated();
    }

    return true;
  }

  public void onUnauthorizedInstantiation(Component component) {
   // throw new RestartResponseAtInterceptPageException(
     //   Home.class);

      if (component instanceof Page) {
            Page page = (Page) component;

            if ((( BibleStudyFaceBookSession) page.getSession()).getClient() != null) {
                return;
            }

            BibleStudyFaceBookSession session = ( BibleStudyFaceBookSession) page.getSession();
            try {
                FacebookRestClient authClient = FaceBookAuthHandler.getAuthenticatedClient(page.getRequest(), _apiKey, _secretKey);
                session.setClient(authClient);
            } catch (FailedLoginException fle) {
                //user not logged in
                forceLogin(page);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new UnauthorizedInstantiationException(component.getClass());
        }

  }
  private void forceLogin(Page page) {

        page.getRequestCycle().setRequestTarget(new RedirectRequestTarget("http://www.facebook.com/login.php?api_key=" + _apiKey + "&v=1.0"));

    }

}
