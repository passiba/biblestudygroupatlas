
package fi.passiba.groups.ui.pages;


import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.pages.wizards.WizardPage;
import fi.passiba.groups.ui.pages.wizards.userCreation.NewUserWizard;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.authenticate.PasswordService;
import fi.passiba.services.persistance.Person;
import java.util.List;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

public class Home extends WebPage {
     
    public static final String REDIRECTPAGE_PARAM = "redirectpage";
    @SpringBean
    private IAuthenticator authenticate;
  
    private static final long serialVersionUID = 1L;

    public Home(final PageParameters parameters) {
        //getSession().invalidate();
        //setResponsePage(pageClass);
        add(new LoginForm("login"));
      
    }

   

    public class LoginForm extends StatelessForm {
        
        
        private String password;

        private String username;
        
        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new TextField("username"));
            add(new PasswordTextField("password"));
            add(new FeedbackPanel("feedback"));
            add(new WizardLink("newUserWizardLink", NewUserWizard.class));
           /* add(new Link("map") {

                @Override
                public void onClick() {
                   setResponsePage(GetMarkersPage.class);
                }
            });*/
        //add(new BookmarkablePageLink("register", Register.class));
        // add(new FeedbackPanel("messages"));
        }
        
        
        
        @Override
        protected void onSubmit() {
            
            if (signIn(username, password)) {
                if (!continueToOriginalDestination()) {
                    setResponsePage(Main.class);
               }
            } else {
                error("Unknown username/ password");
            }
        }
        
        
        private boolean signIn(String username, String password) {
            if (username != null && password != null) {
                
                List<Person> persons = authenticate.findPerson(username);
               // Person person =  populateperson(username,password);
                for (Person per:persons) {
                    try {


                        if (PasswordService.decrypt(username.toCharArray(), per.getFk_userid().getPassword()).equals(password)) {
                            BibleStudySession.get().setPerson(per);
                            return true;
                        }
                        //return true;
                    } catch (Exception ex) {
                        return false;
                        //Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return false;
        }
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
      
        
    }
    
    
     /**
* Link to the wizard. It's an internal link instead of a bookmarkable page to help us with
* backbutton surpression. Wizards by default do not partipcate in versioning, which has the
* effect that whenever a button is clicked in the wizard, it will never result in a change of
* the redirection url. However, though that'll work just fine when you are already in the
* wizard, there is still the first access to the wizard. But if you link to the page that
* renders it using and internal link, you'll circumvent that.
*/

	private static final class WizardLink extends Link
    {
        private final Class<? extends Wizard> wizardClass;

        /**
         * Construct.
         * 
         * @param <C>
         * 
         * @param id
         *            Component id
         * @param wizardClass
         *            Class of the wizard to instantiate
         */
        public <C extends Wizard> WizardLink(String id, Class<C> wizardClass)
        {
            super(id);
            this.wizardClass = wizardClass;
        }

        /**
         * @see org.apache.wicket.markup.html.link.Link#onClick()
         */
        @Override
        public void onClick()
        {
            setResponsePage(new WizardPage(wizardClass));
        }
    }
    
}
