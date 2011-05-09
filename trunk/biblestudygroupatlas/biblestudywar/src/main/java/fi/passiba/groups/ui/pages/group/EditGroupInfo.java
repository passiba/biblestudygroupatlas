package fi.passiba.groups.ui.pages.group;

import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.pages.*;
import fi.passiba.groups.ui.pages.address.AddressPanel;

import fi.passiba.groups.ui.pages.search.ListPersonsPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;

import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class EditGroupInfo extends BasePage {

    @SpringBean
    private IGroupServices groupservice;
    private Groups groups = null;
    private Person contactperson = new Person();
    private final Page backPage;
  
    private static final List<String> allRoles = Constants.RoleType.getRoleTypes();
    List<Person> contactpersons ;
    public EditGroupInfo(Page backPage, final Long groupId) {
        this.backPage = backPage;
        setDefaultModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

                groups = groupservice.findGroupByGroupId(groupId);
                contactpersons = groupservice.findGroupsPersonsByGroupId(groupId);

                if (contactpersons != null && !contactpersons.isEmpty()) {
                    contactperson = contactpersons.get(0);
                }

                return groups;
            }
        }));
        init();
    }

    private void init() {

        add(new GroupForm("form", getDefaultModel()));



    }

    private class GroupForm extends Form {

        final List<String> allGroupTypes = Constants.GroupType.getGroupTypes();

        public GroupForm(String id, IModel m) {
            super(id, m);
            TextField groupName = new TextField("groupname", new PropertyModel<String>(getModel(), "name"));
            groupName.setRequired(true);
            add(groupName);
            final long groupid = Long.valueOf(new PropertyModel<String>(getModel(), "id").getObject().toString());

            WebMarkupContainer grouptypes = new WebMarkupContainer("grouptype");
            add(grouptypes);
            grouptypes.setVisible(true);
            grouptypes.add(new DropDownChoice("grouptypes", new PropertyModel<String>(getModel(),
                    "grouptypename"), allGroupTypes).setRequired(true));


            TextField Name = new TextField("congregationname", new PropertyModel<String>(getModel(), "congregationname"));
            Name.setRequired(true);
            add(Name);

            TextField email = new TextField("congregationemail", new PropertyModel<String>(getModel(), "congregatiolistemailaddress"));
            email.setRequired(true);
            email.add(EmailAddressValidator.getInstance());
            add(email);

            TextField siteurl = new TextField("congregationwebsiteurl", new PropertyModel<String>(getModel(), "congregationwebsiteurl"));

            siteurl.setRequired(true);
            add(siteurl);

            ChoiceRenderer choiceRenderer = new ChoiceRenderer("lastname");

            DropDownChoice<Person> contactpersonddc = new DropDownChoice("contactperson", new PropertyModel(contactperson, "lastname"),
                    new LoadableDetachableModel() {

                        @Override
                        protected Object load() {
                          
                            contactpersons = groupservice.findGroupsPersonsByGroupId(groupid);
                            if (contactpersons != null && !contactpersons.isEmpty()) {
                                contactperson = contactpersons.get(0);
                            }
                            return contactpersons;

                        }
                    }, choiceRenderer);
            contactpersonddc.setOutputMarkupId(true);
            add(contactpersonddc);

            final Label selectedContactAddressAdd1 = new Label("selectedContactaddr1", new PropertyModel<String>(contactperson, "adress.addr1"));
            selectedContactAddressAdd1.setOutputMarkupId(true);
            add(selectedContactAddressAdd1);

            final Label selectedContactAddressCity = new Label("selectedContactcity", new PropertyModel<String>(contactperson, "adress.city"));
            selectedContactAddressCity.setOutputMarkupId(true);
            add(selectedContactAddressCity);

            final Label selectedContactAddressCountry = new Label("selectedContactcountry", new PropertyModel<String>(contactperson, "adress.country"));
            selectedContactAddressCountry.setOutputMarkupId(true);
            add(selectedContactAddressCountry);



            Adress address = (Adress) new PropertyModel(getModel(), "adress").getObject();
            long addressid = address.getId();
            final AddressPanel addressPanel = new AddressPanel("addressPanel", addressid);

            add(addressPanel);

            contactpersonddc.add(new AjaxFormSubmitBehavior("onchange") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    //group.setAdress(groupservice.findGroupAddressByGroupId(group.getId()));
                    selectedContactAddressAdd1.setDefaultModel(new PropertyModel(contactperson, "adress.addr1"));
                    selectedContactAddressCity.setDefaultModel(new PropertyModel(contactperson, "adress.city"));
                    selectedContactAddressCountry.setDefaultModel(new PropertyModel(contactperson, "adress.country"));
                    target.addComponent(selectedContactAddressAdd1);
                    target.addComponent(selectedContactAddressCity);
                    target.addComponent(selectedContactAddressCountry);
                }

                @Override
                protected void onError(AjaxRequestTarget target) {
                }
            });
            ListPersonsPanel personListPanel=new ListPersonsPanel ("userlistPanel",contactpersons,EditGroupInfo.this.getPage(),groupid);
            personListPanel.setOutputMarkupId(true);


            personListPanel.add(new AjaxFormSubmitBehavior("onchange") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    //group.setAdress(groupservice.findGroupAddressByGroupId(group.getId()));
                  
                    contactpersons = groupservice.findGroupsPersonsByGroupId(groupid);
                    target.addComponent(addOrReplace(new  ListPersonsPanel("userlistPanel",contactpersons,EditGroupInfo.this.getPage(), groupid)));
                    //reset();
                }

                @Override
                protected void onError(AjaxRequestTarget target) {
                }
            });














            add(personListPanel);
            add(new Button("save") {

                public void onSubmit() {

                    Groups group = (Groups) getForm().getModelObject();
                    group.setAdress(addressPanel.getAddress());
                    groupservice.updateGroup(group);
                    setResponsePage(EditGroupInfo.this.backPage);
                }
            });
            add(new Button("cancel") {

                public void onSubmit() {

                    setResponsePage(EditGroupInfo.this.backPage);
                }
            });
        }
    }
}
