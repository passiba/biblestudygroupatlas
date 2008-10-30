package fi.passiba.groups.ui.pages.user;

import fi.passiba.groups.ui.pages.*;
import fi.passiba.groups.ui.pages.address.AddressPanel;

import fi.passiba.groups.ui.pages.wizards.WizardPage;
import fi.passiba.groups.ui.pages.wizards.biblesession.NewBibleSessionWizard;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;

import fi.passiba.services.persistance.Users;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EditPersonContact extends BasePage {

    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private IGroupServices groupservice;
    
    private final Page backPage;

    /** cheap roles database. */
    private static final List<String> allRoles = Arrays.asList(new String[]{"Admin", "User"});

    public EditPersonContact(Page backPage,final Long contactId) {
        
        this.backPage = backPage;
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

                return authenticate.findPersonByPersonID(contactId);
               
            }
        }));
        init();
    }

    private void init() {

        add(new ContactForm("form", getModel()));

         

    }

    private class ContactForm extends Form {

        public ContactForm(String id, IModel m) {
            super(id, m);
            TextField firstName = new TextField("firstName", new PropertyModel(getModel(), "firstname"));
            firstName.setRequired(true);
            firstName.add(StringValidator.maximumLength(15));
            add(firstName);

            TextField lastName = new TextField("lastName", new PropertyModel(getModel(), "lastname"));
            lastName.setRequired(true);
            lastName.add(StringValidator.maximumLength(20));
            add(lastName);

            TextField userName = new TextField("username", new PropertyModel(getModel(), "fk_userid.username"));
            userName.setRequired(true);
            userName.add(StringValidator.maximumLength(20));
            add(userName);
            
           
            try {
                Users user=(Users)new PropertyModel(getModel(), "fk_userid").getObject();
               /* String passwd = PasswordService.decrypt(user.getUsername().toCharArray(), user.getPassword());
                user.setPassword(passwd);*/
                Person person=(Person)getModel().getObject();
                person.setFk_userid(user);
            } catch (Exception ex) {
              
            }
           /* PasswordTextField password = new PasswordTextField("password", new PropertyModel(getModel(), "fk_userid.password"));
            password.setRequired(true);
            password.setResetPassword(false);
            password.add(StringValidator.maximumLength(20));
            password.add(StringValidator.minimumLength(6));
            add(password);


            PasswordTextField confirmpassword = new PasswordTextField("confirmpassword",  new PropertyModel(getModel(), "fk_userid.password"));
            confirmpassword.setResetPassword(false);
            confirmpassword.setRequired(true);
            confirmpassword.add(StringValidator.maximumLength(20));
            confirmpassword.add(StringValidator.minimumLength(6));
            add(confirmpassword);

            add(new EqualPasswordInputValidator(password, confirmpassword));

            */
            TextField email = new TextField("email", new PropertyModel(getModel(), "email"));
            email.add(StringValidator.maximumLength(150));
            email.add(EmailAddressValidator.getInstance());
            add(email);
            // ChoiceRenderer choiceRenderer = new ChoiceRenderer("fk_userid.rolename");
            add(new DropDownChoice("role", new PropertyModel(getModel(),
                    "fk_userid.rolename"), allRoles).setRequired(true));




            IModel groupsModel = new LoadableDetachableModel() {

                protected Object load() {
                    long personid = Long.valueOf(new PropertyModel(getModel(), "id").getObject().toString());
                    List<Groups> groupsCol = groupservice.findGroupsByPersonId(personid);
                    return groupsCol;
                }
            };

            ListView groups = new ListView("groups", groupsModel) {

                protected void populateItem(ListItem item) {


                    item.add(new Label("group",
                            new PropertyModel(item.getModel(), "name")));


                /*item.add(new Link("edit", item.getModel()) {
                public void onClick() {
                Person p = (Person ) getModelObject();
                setResponsePage(new EditPersonContact(p.getId()));
                }
                });
                item.add(new Link("delete", item.getModel()) {
                public void onClick() {
                Person p = (Person ) getModelObject();
                authenticate.deletePerson(p);
                setResponsePage(ListPersons.class);
                }
                });*/
                }
            };
          
            add(groups);
           
            Adress address=(Adress)new PropertyModel(getModel(), "adress").getObject();
            long addressid = address.getId();
            final AddressPanel addressPanel= new AddressPanel("addressPanel",addressid);
            
            add(addressPanel);
            add(new WizardLink("newBibleSessionLink", NewBibleSessionWizard.class));
            add(new SaveButton ("save",addressPanel));
            add(new CancelButton("cancel"));
        }
    }
    private final class CancelButton extends Button {

        private static final long serialVersionUID = 1L;
        private CancelButton(String id) {
            super(id);
        
            setDefaultFormProcessing(false);
        }

        @Override
        public void onSubmit() {
            setResponsePage(EditPersonContact.this.backPage);
        }
    }
    private final class SaveButton extends Button {
        
        private AddressPanel addressPanel;
        private SaveButton(String id, AddressPanel addressPanel) {
            super(id);
            this.addressPanel=addressPanel;
    
        }

        @Override
        public void onSubmit() {
            Person person = (Person) getForm().getModelObject();
            person.setAdress(this.addressPanel.getAddress());

            Users user = person.getFk_userid();
            /*try {
                user.setPassword(PasswordService.encrypt(user.getUsername().toCharArray(), user.getPassword()));
            } catch (Exception ex) {
            }*/
            person.setFk_userid(user);
            authenticate.updatePerson(person);
            // setResponsePage(ListContacts.class);
            setResponsePage(EditPersonContact.this.backPage);
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
