/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.serices.search;

import fi.passiba.AbstractDependencyInjectionSpringContextTest;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Users;
import fi.passiba.services.search.ISearchService;
import java.util.Date;
import java.util.List;

/**
 *
 * @author haverinen
 */
public class SearchServiceImpTest extends AbstractDependencyInjectionSpringContextTest {

    private String username = "hemuli",  rolename = Constants.RoleType.USER.getType();
    private String groupType = Constants.GroupType.MENSGROUP.getType(),groupName="Miehet muutoksessa",  city = "Espoo",  country = "Finland";

    public void testsearchingPersons() throws Exception {
        IAuthenticator authenticator = (IAuthenticator) applicationContext.getBean("IAuthenticator");
        Person person = addPerson();
        ISearchService searchService = (ISearchService) applicationContext.getBean("SearchService");

        List<Person> persons = searchService.findPersonByUserName(username, 0, 20);

        Person fetchUser = null;
        for (Person per : persons) {
            fetchUser = per;
            break;
        }
        assert (person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));


        persons = searchService.findPersonByRolenameWithLocation(rolename, person.getAdress().getCountry(), person.getAdress().getCity());

        for (Person per : persons) {
            fetchUser = per;
            break;
        }
        assert (person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));

        persons = searchService.findPersonByLocation(person.getAdress().getCountry(), person.getAdress().getCity());

        for (Person per : persons) {
            fetchUser = per;
            break;
        }
        assert (person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));


        authenticator.deletePerson(person);
    }

    private Person addPerson() throws Exception {
        IAuthenticator authenticator = (IAuthenticator) applicationContext.getBean("IAuthenticator");

        Adress ad = new Adress();
        ad.setAddr1("Testikuja 1 b");
        ad.setAddr2("PL 39");
        ad.setCity("Helsinki");
        ad.setCountry("Finland");
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
        ad.setZip("01000");


        Person person = new Person();
        person.setAdress(ad);

        person.setEmail("testuser@hotmail.com");
        person.setFirstname("paul");
        person.setLastname("Geronimo");
        person.setDateofbirth(new Date());


        Users regularUser = new Users();
        regularUser.setUsername(username);
        regularUser.setRolename(rolename);
        regularUser.setStatus(Constants.StatusType.ACTIVE.getType());
        person.setFk_userid(regularUser);


        authenticator.registerPerson(person);
        return person;
    }

    public void testsearchingGroups() throws Exception {
        Groups group = addGroup();
        ISearchService searchService = (ISearchService) applicationContext.getBean("SearchService");
        IGroupServices groupServices = (IGroupServices) applicationContext.getBean("IGroupServices");
        List<Groups> groups = searchService.findGroupsByLocation(country, city);

        Groups fetchGroup = null;
        for (Groups g : groups) {
            fetchGroup = g;
            break;
        }
        assert (group.getName().equals(fetchGroup.getName()));

        groups = searchService.findGroupsByType(country, groupType);

        for (Groups g : groups) {
            fetchGroup = g;
            break;
        }
        assert (group.getGrouptypename().equals(fetchGroup.getGrouptypename()));

        groups = searchService.findGroupsByName(groupName, 0,20);

        for (Groups g : groups) {
            fetchGroup = g;
            break;
        }
        assert (group.getGrouptypename().equals(fetchGroup.getGrouptypename()));
        groupServices.deleteGroup(group);

    }

    private Groups addGroup() throws Exception {
        IGroupServices groupServices = (IGroupServices) applicationContext.getBean("IGroupServices");

        Adress ad = new Adress();
        ad.setAddr1("Testikuja 2 b");
        ad.setAddr2("PL 39");
        ad.setCity("Helsinki");
        ad.setCountry(country);
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
        ad.setZip("01000");


        Groups group = new Groups();
        group.setAdress(ad);

        group.setCongregatiolistemailaddress("hellarit@svk.fi");
        group.setCongregationname("Espoon Lähiseurakunta");
        group.setCongregationwebsiteurl("www.lahisrk.fi");
        group.setGrouptypename(groupType);
        group.setDescription("Kristillisten miesten kasvuryhmä");
        group.setStatus(Constants.StatusType.ACTIVE.getType());
        group.setName(groupName);
        // group.setCreatedBy("Admin");
        // group.setCreatedBy("Admin");



        groupServices.addGroup(group);
        return group;
    }
}
