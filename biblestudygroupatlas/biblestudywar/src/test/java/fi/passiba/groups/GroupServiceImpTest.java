package fi.passiba.groups;

import fi.passiba.AbstractTransactionalJUnit4SpringContext;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Users;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public final class GroupServiceImpTest  extends AbstractTransactionalJUnit4SpringContext {

    private String groupType = "Miestenpiiri",  city = "Espoo",  country = "Finland",username="habbo";

   @Autowired
   private  IGroupServices groupServices;
   @Autowired
   private IAuthenticator authenticator;

    private Groups addGroup(Groups group) throws Exception {
     
        if (group == null) {
            Adress ad = new Adress();
            ad.setAddr1("Testikuja 2 b");
            ad.setAddr2("PL 39");
            ad.setCity("Helsinki");
            ad.setCountry(country);
            ad.setPhone("0505555555");
            ad.setState("Uusimaa");
            ad.setZip("01000");


            group = new Groups();
            group.setAdress(ad);

            group.setCongregatiolistemailaddress("hellarit@svk.fi");
            group.setCongregationname("Espoon Lähiseurakunta");
            group.setCongregationwebsiteurl("www.lahisrk.fi");
            group.setGrouptypename(groupType);
            group.setDescription("Kristillisten miesten kasvuryhmä");
            group.setStatus(Constants.StatusType.ACTIVE.getType());
            group.setName("Miehet muutoksessa");
           // group.setCreatedBy("Admin");
           // group.setCreatedBy("Admin");

        }

        groupServices.addGroup(group);
        return group;
    }
    @Test
    public void testAddingNewGroup() throws Exception {

        
      /*  IGroupsDAO groupDAO = (IGroupsDAO) applicationContext.getBean("GroupsDAO");
        Groups group = addGroup(null);
        List<Groups> groups = groupDAO.getByExample(group);
        Groups fetchGroup = null;
        for (Groups grp : groups) {
            fetchGroup = grp;
        }
        assert (group.getName().equals(fetchGroup.getName()));*/
    }
    @Test
    public void testUpdatingGroup() throws Exception {
      
        Adress ad = new Adress();
        ad.setAddr1("Testikuja 1 b");
        ad.setAddr2("PL 39");
        ad.setCity(city);
        ad.setCountry("Finland");
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
        ad.setZip("01000");
        List<Groups> groups = groupServices.findGroupsByLocation(country, city, groupType);

        Groups group = null;
        for (Groups grp : groups) {
            group = grp;
        }
        if (groups == null || groups.isEmpty()) {
            group = addGroup(null);
        }
        group.setAdress(ad);

        groupServices.updateGroup(group);
        groups =  groupServices.findGroupsByLocation(country, city, groupType);
        Groups fetchGroup = null;
        for (Groups grp : groups) {
            fetchGroup = grp;
        }
        assert (group.getAdress().getCity().equals(fetchGroup.getAdress().getCity()));
    }
    @Test
    public void testDeletingGroup() throws Exception {
      
        List<Groups> groups = groupServices.findGroupsByLocation(country, city, groupType);

        Groups group = null;
        for (Groups grp : groups) {
            group = grp;
        }
        if (groups == null || groups.isEmpty()) {
            group = addGroup(null);
        }
        groupServices.deleteGroup(group);
        groups = groupServices.findGroupsByLocation(country, city, groupType);
        assertEquals(0, groups.size());
    }
    @Test
    public void testAddingDeletingGroupPerson() throws Exception 
    {
        List<Groups> groups = groupServices.findGroupsByLocation(country, city, groupType);

        Groups group = null;
        for (Groups grp : groups) {
            group = grp;
        }
        if (groups == null || groups.isEmpty()) {
            group = addGroup(null);
        }
        Person person=addPerson();
        Set<Person> persons = new HashSet<Person>(0);
        persons.add(person);
        group.setGrouppersons(persons);
        groupServices.updateGroup(group);
        List<Person> fetchedPersons=groupServices.findGroupsPersonsByGroupId(group.getId());
        assertEquals(1,fetchedPersons.size());
        Person groupperson=fetchedPersons.get(0);
        assertEquals(person.getId(),groupperson.getId());
        groupServices.deleteGroupPersonFromGroup(person.getId(),group.getId());
        fetchedPersons=groupServices.findGroupsPersonsByGroupId(group.getId());
        assertEquals(0,fetchedPersons.size());
        person=authenticator.findPersonByPersonID(person.getId());
        assertNotNull(person);
    }
     private Person addPerson()throws Exception
  {
          Person person;
       
          Adress ad = new Adress();
          ad.setAddr1("Testikuja 1 b");
          ad.setAddr2("PL 39");
          ad.setCity("Helsinki");
          ad.setCountry("Finland");
          ad.setPhone("0505555555");
          ad.setState("Uusimaa");
          ad.setZip("01000");


          person = new Person();
          person.setAdress(ad);

          person.setEmail("testuser@hotmail.com");
          person.setFirstname("Jenna");
          person.setLastname("Gopher");
          person.setDateofbirth(new Date());


          Users regularUser = new Users();
          regularUser.setUsername(username);
          regularUser.setRolename(Constants.RoleType.USER.getType());
          regularUser.setStatus(Constants.StatusType.ACTIVE.getType());
          person.setFk_userid(regularUser);
      

      authenticator.registerPerson(person);
      return person;
  }
}
