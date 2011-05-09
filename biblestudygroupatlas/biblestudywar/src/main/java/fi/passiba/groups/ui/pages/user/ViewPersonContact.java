package fi.passiba.groups.ui.pages.user;

import fi.passiba.groups.ui.pages.*;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.groups.ui.pages.search.ListPersons;
import org.apache.wicket.extensions.markup.html.basic.SmartLinkLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ViewPersonContact extends BasePage {

    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private IGroupServices groupservice;
    
    private final Page backPage;

    public ViewPersonContact(Page backPage,final Long contactId) {
        this.backPage=backPage;
        setDefaultModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                return authenticate.findPersonByPersonID(contactId);
            }
        }));


        add(new Label("firstName", new PropertyModel(getDefaultModel(), "firstname")));
        add(new Label("lastName", new PropertyModel(getDefaultModel(), "lastname")));
        add(new SmartLinkLabel("email", new PropertyModel(getDefaultModel(), "email")));
        add(new Label("role", new PropertyModel(getDefaultModel(), "fk_userid.rolename")));
        add(new Label("username", new PropertyModel(getDefaultModel(), "fk_userid.username")));


        IModel<Groups> groupsModel = new LoadableDetachableModel() {

            protected Object load() {
                long personid = Long.valueOf(new PropertyModel(getDefaultModel(), "id").getObject().toString());
                List<Groups> groupsCol = groupservice.findGroupsByPersonId(personid);
                return groupsCol;
            }
        };

        ListView groups = new ListView("groups", groupsModel) {

            protected void populateItem(ListItem item) {


                item.add(new Label("group",
                        new PropertyModel(item.getModel(), "name")));


            }
        };
        add(groups);

        add(new Link("edit", getDefaultModel()) {

            public void onClick() {
                Person p = (Person) getModelObject();
                setResponsePage(new EditPersonContact(getPage(),p.getId()));
            }
        });
        add(new Link("cancel") {

            public void onClick() {
                setResponsePage( ViewPersonContact.this.backPage);
            }
        });
        add(new Link("delete", getDefaultModel()) {

            public void onClick() {
                Person p = (Person) getModelObject();
                authenticate.deletePerson(p);
                setResponsePage(Main.class);
            }
        });
    }
}
