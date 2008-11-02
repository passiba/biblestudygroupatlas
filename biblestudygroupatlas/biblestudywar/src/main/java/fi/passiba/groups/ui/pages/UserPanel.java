package fi.passiba.groups.ui.pages;

import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.groups.ui.pages.locale.LocaleDropDown;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.groups.ui.pages.wizards.WizardPage;
import fi.passiba.groups.ui.pages.wizards.userCreation.NewUserWizard;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.persistance.Person;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class UserPanel extends Panel {

    @SpringBean
    private IAuthenticator authenticate;
   
    
    public UserPanel(String id) {

        super(id);
      
        init();
    }
    private void init()
    {
         final List<Person> persons = authenticate.findPerson(BibleStudyFaceBookSession.get().getFaceBookUserName());
         long personid=0;
         if (persons!=null && !persons.isEmpty()) {
              personid=persons.get(0).getId();

         }

         IModel model = new CompoundPropertyModel(new LoadableDetachableModel() {

            @Override
            public Object load() {

                // Person person =  populateperson(username,password);
                for (Person person : persons) {
                   return person;

                }
                return null;
            }
        });
        setModel(model);
        
        add(new Label("groups", new PropertyModel(getModel(),
                "groupFirstName")));
        add(new LocaleDropDown("localeSelect", Arrays.asList(new Locale[]{Locale.ENGLISH, new Locale("fi")})));
       
       
        EditPerson edit =new EditPerson("edit", model, personid);
           
        edit.add(new Label("fullname", new PropertyModel(getModel(),
                "fullname")));
        WizardLink userWisard = new WizardLink("newUserWizardLink", NewUserWizard.class);
        if (persons ==null || persons.isEmpty()) {
            edit.setVisible(false);
            userWisard.setVisible(true);
        }else
        {
           userWisard.setVisible(false);
        }
        add(edit);
        add(userWisard);

    }
    private static final class EditPerson extends Link
    {
        private long personid;
        
        private EditPerson(String id,IModel m,long personid) {
                super(id,m);
                this.personid=personid;
            }

        @Override
        public void onClick() {
                setResponsePage(new EditPersonContact(getPage(),personid));
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
    private static final class WizardLink extends Link {

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
        public <C extends Wizard> WizardLink(String id, Class<C> wizardClass) {
            super(id);
            this.wizardClass = wizardClass;
        }

        /**
         * @see org.apache.wicket.markup.html.link.Link#onClick()
         */
        @Override
        public void onClick() {
            setResponsePage(new WizardPage(wizardClass));
        }
    }
}
