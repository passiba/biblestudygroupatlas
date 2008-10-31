/*
 * Created when user has logged in using facebook login page
 */
package fi.passiba.biblestudy;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.ProfileField;
import fi.passiba.services.persistance.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.w3c.dom.Document;

/**
 *
 * @author haverinen
 */
public class BibleStudyFaceBookSession extends WebSession {

    private FacebookXmlRestClient client;
    private Person person;

    public BibleStudyFaceBookSession(Request request) {
        super(request);
        setLocale(new Locale("fi"));
    }

    public static BibleStudyFaceBookSession get() {
        return (BibleStudyFaceBookSession) Session.get();
    }

    public synchronized FacebookXmlRestClient getClient() {
        return client;
    }

    public synchronized void setClient(FacebookXmlRestClient client) {
        this.client = client;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isAuthenticated() {
        return (client != null);
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFaceBookUserName() {

        long userID = 0;
        String name = "";
        if (BibleStudyFaceBookSession.get().getClient() != null) {
            
            try {
            userID = this.getClient().users_getLoggedInUser();
            EnumSet<ProfileField> fields = EnumSet.of(
                    ProfileField.NAME,
                    ProfileField.PIC);

            Collection<Long> users = new ArrayList<Long>();
            users.add(userID);

            Document d = null;
            
                d = (Document) this.getClient().users_getInfo(users, fields);
               name =d.getElementsByTagName("name").item(0).getTextContent();
            } catch (FacebookException ex) {
                // Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                // Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return name;
    }
}
