/*
 * Created when user has logged in using facebook login page
 */

package fi.passiba.biblestudy;

import com.google.code.facebookapi.FacebookRestClient;
import fi.passiba.services.persistance.Person;
import java.util.Locale;
import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;


/**
 *
 * @author haverinen
 */
public class BibleStudyFaceBookSession extends WebSession  {

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
        return (client!=null && person != null);
    }

    public void setPerson(Person person) {
        this.person = person;
    }





}
