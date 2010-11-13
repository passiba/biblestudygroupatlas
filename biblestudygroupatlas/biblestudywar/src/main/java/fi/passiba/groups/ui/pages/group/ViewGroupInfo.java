package fi.passiba.groups.ui.pages.group;

import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.pages.*;

import fi.passiba.groups.ui.pages.search.ListGroups;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import fi.passiba.biblestudy.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.basic.SmartLinkLabel;
import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ViewGroupInfo extends BasePage {

    @SpringBean
    private IGroupServices groupservice;
    private Groups groups = null;
    private Person contactperson = new Person();
    private final Page backPage;
    public ViewGroupInfo(Page backPage,final Long groupId) {
        
        this.backPage=backPage;
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

                groups = groupservice.findGroupByGroupId(groupId);
                List<Person> contactpersons = groupservice.findGroupsPersonsByGroupId(groupId);

                if (contactpersons != null && !contactpersons.isEmpty()) {
                    contactperson = contactpersons.get(0);
                }

                return groups;
            }
        }));
        init();
    }

    private void init() {

        add(new GroupForm("form", getModel()));



    }

    private class GroupForm extends Form {

        final List<String> allGroupTypes =  Constants.GroupType.getGroupTypes();

        public GroupForm(String id, IModel m) {
            super(id, m);
            Label  groupName = new  Label ("groupname", new PropertyModel(getModel(), "name"));
            add(groupName);

            Label grouptype = new Label("grouptypes", new PropertyModel(getModel(), "grouptypename"));

            add(grouptype);

            Label Name = new Label("congregationname", new PropertyModel(getModel(), "congregationname"));
            add(Name);
            SmartLinkLabel email = new SmartLinkLabel("congregationemail", new PropertyModel(getModel(), "congregatiolistemailaddress"));
            add(email);

            SmartLinkLabel siteurl = new SmartLinkLabel("congregationwebsiteurl", new PropertyModel(getModel(), "congregationwebsiteurl"));
            add(siteurl);

            IModel contactPersonsModel = new LoadableDetachableModel() {

                protected Object load() {
                    long groupid = Long.valueOf(new PropertyModel(getModel(), "id").getObject().toString());
                    List<Person> contactpersons = groupservice.findGroupsPersonsByGroupId(groupid);
                    return contactpersons;
                }
            };
            ListView persons = new ListView("contacpersons", contactPersonsModel) {

                protected void populateItem(ListItem item) {


                    item.add(new Label("contactperson",
                            new PropertyModel(item.getModel(), "lastname")));

                }
            };
            add(persons);



            Adress address = (Adress) new PropertyModel(getModel(), "adress").getObject();
            long addressid = address.getId();
            //final AddressPanel addressPanel = new AddressPanel("addressPanel", addressid);

            //  add(addressPanel);

             add(new Link("cancel") {

                public void onClick() {
                     setResponsePage(ViewGroupInfo.this.backPage);
                  
                }
            });

            add(new Link("edit", getModel()) {

                public void onClick() {

                    Groups group = (Groups) getModelObject();
                    setResponsePage(new EditGroupInfo(getPage(),group.getId()));
                }
            });
             add(new Link("delete", getModel()) {

                public void onClick() {

                    Groups group = (Groups) getModelObject();
                    groupservice.deleteGroup(group);
                     setResponsePage(Main.class);
                  
                }
            });
        }
    }
}
