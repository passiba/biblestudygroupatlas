/*
 * Handles the authorizatrion of the user when using facebook login page
 */

package fi.passiba.biblestudy.authorization.facebook;


import  com.google.code.facebookapi.FacebookParam;

import com.google.code.facebookapi.FacebookXmlRestClient;
import java.util.Map;
import javax.security.auth.login.FailedLoginException;
import org.apache.wicket.Request;

/**
 *
 * @author haverinen
 */
public class FaceBookAuthHandler {

    public static FacebookXmlRestClient getAuthenticatedClient(Request request, String apiKey, String secretKey) throws Exception {
        String authToken = request.getParameter("auth_token");
        String sessionKey = request.getParameter(FacebookParam.SESSION_KEY.toString());
        FacebookXmlRestClient fbClient = null;
        if (sessionKey != null) {
            fbClient = new FacebookXmlRestClient(apiKey, secretKey, sessionKey);
        } else if (authToken != null) {
            fbClient = new FacebookXmlRestClient(apiKey, secretKey);
            //establish session
            fbClient.auth_getSession(authToken);
        } else {
            throw new FailedLoginException("Session key not found");
        }
       /*  Map<Integer, String> prefs = fbClient.data_getUserPreferences();
         System.out.println("All current preferences:");
        for (Integer key : prefs.keySet()) {
            System.out.println("\tkey " + key + " = " + prefs.get(key));
        }*/

        fbClient.setIsDesktop(false);
        return fbClient;
    }


}
