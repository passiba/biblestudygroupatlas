/*
 * Created when user has logged in using facebook login page
 */
package fi.passiba.biblestudy;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookRestClient;
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

    private FacebookRestClient client;
    private Person person;

    public BibleStudyFaceBookSession(Request request) {
        super(request);
        setLocale(new Locale("fi"));
    }

    public static BibleStudyFaceBookSession get() {
        return (BibleStudyFaceBookSession) Session.get();
    }

    public synchronized FacebookRestClient getClient() {
        return client;
    }

    public synchronized void setClient(FacebookRestClient client) {
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
            userID = this.getClient()._getUserId();
            EnumSet<ProfileField> fields = EnumSet.of(
                    ProfileField.NAME,
                    ProfileField.PIC);

            Collection<Long> users = new ArrayList<Long>();
            users.add(userID);

            Document d = null;
            try {
                d = this.getClient().users_getInfo(users, fields);
            } catch (FacebookException ex) {
                // Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                // Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            name =
                    d.getElementsByTagName("name").item(0).getTextContent();

        }
        return name;
    }
}
