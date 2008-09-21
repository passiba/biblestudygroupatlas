package fi.passiba.groups.ui.pages.search;

import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import java.util.Iterator;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import fi.passiba.groups.ui.pages.BasePage;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.groups.ui.pages.user.ViewPersonContact;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.persistance.Person;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ListPersons extends BasePage {

    @SpringBean
    private IAuthenticator authenticate;

    public ListPersons(PageParameters params) {

        final String searchCriteria = params.getString("searchcriteria");
        final String searchString = params.getString("searchString");




        
            /*@AdminOnly
    private class UserLink extends Link {
    
    UserLink(String id) {
    super(id);
    }
    
    @Override
    public void onClick() {
    inEditMode = !inEditMode;
    setContentPanel();
    }
    }
    private boolean inEditMode = false;
    void setContentPanel() {
    if (inEditMode) {
    addOrReplace(new DiscountsEditList("content"));
    } else {
    addOrReplace(new DiscountsList("content"));
    }
    }*/

        /*
        
        
        IModel contactsModel = new LoadableDetachableModel() {
        protected Object load() {
        List<Person> searchresult = new ArrayList<Person>(0);
        
        if (searchCriteria != null && searchString != null) {
        if (searchCriteria.equals("Käyttäjänimi")) {
        searchresult = authenticate.findPerson(searchString);
        } else if (searchCriteria.equals("Rooli")) {
        String city = BibleStudySession.get().getPerson().getAdress().getCity();
        String country = BibleStudySession.get().getPerson().getAdress().getCountry();
        
        searchresult = authenticate.findPersonByRolename(searchString, country, city);
        } else {
        String rolename = BibleStudySession.get().getPerson().getFk_userid().getRolename();
        String country = BibleStudySession.get().getPerson().getAdress().getCountry();
        searchresult = authenticate.findPersonByRolename(rolename, country, searchString);
        }
        }
        return searchresult;
        }
        };
        
        
        
        
        
        
        
        ListView contacts = new ListView("contacts", contactsModel) 
        {
        protected void populateItem(ListItem item) {
        Link linkview = new Link("view", item.getModel()) {
        public void onClick() {
        Person p = (Person ) getModelObject();
        setResponsePage(new ViewContact(p.getId()));
        }
        };
        linkview.add(new Label("firstName",
        new PropertyModel(item.getModel(), "firstname")));
        linkview.add(new Label("lastName",
        new PropertyModel(item.getModel(), "lastname")));
        item.add(linkview);
        
        item.add(new Label("phone",
        new PropertyModel(item.getModel(), "adress.phone")));
        
        
        item.add(new Label("email",
        new PropertyModel(item.getModel(), "email")));
        
        item.add(new Label("role",
        new PropertyModel(item.getModel(), "fk_userid.rolename")));
        
        item.add(new Link("edit", item.getModel()) {
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
        });
        }
        };*/
        RefreshingView contacts=populateSearchResult(searchCriteria,searchString);
        contacts.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        add(contacts);
    }
    private RefreshingView populateSearchResult(final String searchCriteria,final String searchString)
    {
            RefreshingView contacts = new RefreshingView("contacts") {

            List<Person> result = new ArrayList<Person>(0);

            @Override
            protected Iterator getItemModels() {
                if (searchCriteria != null && searchString != null) {
                    if (searchCriteria.equals("Käyttäjänimi")) {
                        result = authenticate.findPerson(searchString);
                    } else if (searchCriteria.equals("Rooli")) {
                        String city = BibleStudySession.get().getPerson().getAdress().getCity();
                        String country = BibleStudySession.get().getPerson().getAdress().getCountry();

                        result = authenticate.findPersonByRolename(searchString, country, city);
                    } else {
                        String rolename = BibleStudySession.get().getPerson().getFk_userid().getRolename();
                        String country = BibleStudySession.get().getPerson().getAdress().getCountry();
                        result = authenticate.findPersonByRolename(rolename, country, searchString);
                    }
                }
                return new DomainModelIteratorAdaptor<Person>(result.iterator()) {

                    @Override
                    protected IModel model(final Object object) {
                        
                      return new HashcodeEnabledCompoundPropertyModel((Person) object);
                    }
                };
            }

            @Override
            protected void populateItem(Item item) {

                Link linkview = new Link("view", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        setResponsePage(new ViewPersonContact(getPage(),p.getId()));
                    }
                };
                linkview.add(new Label("firstName",
                        new PropertyModel(item.getModel(), "firstname")));
                linkview.add(new Label("lastName",
                        new PropertyModel(item.getModel(), "lastname")));
                item.add(linkview);

                item.add(new Label("phone",
                        new PropertyModel(item.getModel(), "adress.phone")));


                item.add(new Label("email",
                        new PropertyModel(item.getModel(), "email")));

                item.add(new Label("role",
                        new PropertyModel(item.getModel(), "fk_userid.rolename")));

                item.add(new Link("edit", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        setResponsePage(new EditPersonContact(getPage(),p.getId()));
                    }
                });
                item.add(new Link("delete", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        authenticate.deletePerson(p);
                        setResponsePage(ListPersons.class);
                    }
                });
            }
        };
        return  contacts;
    }
}
