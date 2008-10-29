/*
 * Handles the authorizatrion of the user when using facebook login page
 */

package fi.passiba.biblestudy.authorization.facebook;


import  com.google.code.facebookapi.FacebookParam;
import com.google.code.facebookapi.FacebookRestClient;
import javax.security.auth.login.FailedLoginException;
import org.apache.wicket.Request;

/**
 *
 * @author haverinen
 */
public class FaceBookAuthHandler {

    public static FacebookRestClient getAuthenticatedClient(Request request, String apiKey, String secretKey) throws Exception {
        String authToken = request.getParameter("auth_token");
        String sessionKey = request.getParameter(FacebookParam.SESSION_KEY.toString());
        FacebookRestClient fbClient = null;
        if (sessionKey != null) {
            fbClient = new FacebookRestClient(apiKey, secretKey, sessionKey);
        } else if (authToken != null) {
            fbClient = new FacebookRestClient(apiKey, secretKey);
            //establish session
            fbClient.auth_getSession(authToken);
        } else {
            throw new FailedLoginException("Session key not found");
        }
        fbClient.setIsDesktop(false);
        return fbClient;
    }


}
