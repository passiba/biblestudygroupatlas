package fi.passiba.groups.ui.pages.search;


import fi.passiba.biblestudy.BibleStudyFaceBookSession;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.model.DomainModelIteratorAdaptor;
import fi.passiba.groups.ui.model.HashcodeEnabledCompoundPropertyModel;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.groups.ui.pages.user.ViewPersonContact;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.search.ISearchService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryParser.ParseException;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class ListPersonsPanel extends Panel {
    

    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private ISearchService searchService;
    private final Page backPage;
    
    public ListPersonsPanel(String id,String searchCriteria,String searchString,Page backPage) {
        super(id);
        this.backPage=backPage;
        final List<Person> persons = authenticate.findPerson(BibleStudyFaceBookSession.get().getFaceBookUserName());
        Person currentLogInPerson = null;
        if (persons != null && !persons.isEmpty()) {
            currentLogInPerson = persons.get(0);
        }
        RefreshingView contacts = populateSearchResult(searchCriteria, searchString, currentLogInPerson);
        contacts.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        add(contacts);

    }
     public ListPersonsPanel(String id,List<Person> result,Page backPage) {
        super(id);
        this.backPage=backPage;
        final List<Person> persons = authenticate.findPerson(BibleStudyFaceBookSession.get().getFaceBookUserName());
        Person currentLogInPerson = null;
        if (persons != null && !persons.isEmpty()) {
            currentLogInPerson = persons.get(0);
        }
        RefreshingView contacts = populateResult(result);
        contacts.setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
        add(contacts);

    }
  
    @Override
  public boolean isVisible() 
  {
     // return BibleStudySession.get().isAuthenticated();
      return BibleStudyFaceBookSession.get().isAuthenticated();
  }
   private RefreshingView populateResult(final List<Person> result) {

        RefreshingView contacts = new RefreshingView("contacts") {

          
            @Override
            protected Iterator getItemModels() {


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
                        setResponsePage(new ViewPersonContact(getPage(), p.getId()));
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
                        setResponsePage(new EditPersonContact(getPage(), p.getId()));
                    }
                });
                item.add(new Link("delete", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        authenticate.deletePerson(p);
                        setResponsePage(ListPersonsPanel.this.backPage);
                    }
                });
            }
        };
        return contacts;
    }
   private RefreshingView populateSearchResult(final String searchCriteria, final String searchString, final Person currentLogInPerson) {

        RefreshingView contacts = new RefreshingView("contacts") {

            List<Person> result = new ArrayList<Person>(0);

            @Override
            protected Iterator getItemModels() {

                try {
                    if (searchCriteria != null && searchString != null) {
                        if (searchCriteria.equals(Constants.PersonSearchOption.USERNAME.getOption())) {

                            result = searchService.findPersonByUserName(searchString, 0, 20);

                        } else if (searchCriteria.equals(Constants.PersonSearchOption.USERNAME.USERROLE.getOption())) {
                            String city = "";
                            String country = "";
                            if (currentLogInPerson != null) {
                                city = currentLogInPerson.getAdress().getCity();
                                country = currentLogInPerson.getAdress().getCountry();
                            }

                            result = searchService.findPersonByRolenameWithLocation(searchString, country, city);

                        } else {

                            String country = "", city = "";
                            if (currentLogInPerson != null) {
                                city = currentLogInPerson.getAdress().getCity();
                                country = currentLogInPerson.getAdress().getCountry();
                            }
                            result = searchService.findPersonByLocation(country, searchString);
                        }

                    }
                    return new DomainModelIteratorAdaptor<Person>(result.iterator()) {

                        @Override
                        protected IModel model(final Object object) {

                            return new HashcodeEnabledCompoundPropertyModel((Person) object);
                        }
                    };
                } catch (ParseException ex) {
                    throw new WicketRuntimeException(ex);
                }
            }

            @Override
            protected void populateItem(Item item) {

                Link linkview = new Link("view", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        setResponsePage(new ViewPersonContact(getPage(), p.getId()));
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
                        setResponsePage(new EditPersonContact(getPage(), p.getId()));
                    }
                });
                item.add(new Link("delete", item.getModel()) {

                    public void onClick() {
                        Person p = (Person) getModelObject();
                        authenticate.deletePerson(p);
                        setResponsePage(ListPersonsPanel.this.backPage);
                    }
                });
            }
        };
        return contacts;
    }


}