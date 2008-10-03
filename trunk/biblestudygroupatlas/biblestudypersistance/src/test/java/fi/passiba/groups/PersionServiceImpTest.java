package fi.passiba.groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.dao.IPersonDAO;
import fi.passiba.services.persistance.Users;
import java.util.Date;
import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public final class PersionServiceImpTest  extends AbstractDependencyInjectionSpringContextTests {

  private String username="passiba";

   @Override
      protected String[] getConfigLocations() {
         return new String[] { "classpath:META-INF/biblestudy-data-hibernate.xml"};
   }

  private Person addPerson(Person person)throws Exception
  {
      IPersonDAO personDAO = (IPersonDAO) applicationContext.getBean("PersonDAO");
      if (person == null) {
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
          person.setFirstname("paul");
          person.setLastname("Geronimo");
          person.setDateofbirth(new Date());


          Users regularUser = new Users();
          regularUser.setUsername(username);
          regularUser.setPassword("leppis");
          regularUser.setRolename("User");
          regularUser.setStatus("Aktiivinen");
          person.setFk_userid(regularUser);
      }

      personDAO.save(person);
      return person;
  }
  public void testAddingNewPerson() throws Exception
  {

       IPersonDAO personDAO = (IPersonDAO) applicationContext.getBean("PersonDAO");


        Person  person=addPerson(null);
        List<Person> persons=   personDAO.findPersonByUsername(username);
        Person fetchUser=null;
        for(Person per:persons)
        {
             fetchUser=person;
        }
        assert(person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));
  }

   public void testUpdatingPerson() throws Exception
  {
       IPersonDAO personDAO = (IPersonDAO) applicationContext.getBean("PersonDAO");

        Person per=null;
        Adress ad= new Adress();
        ad.setAddr1("Testikuja 1 b");
        ad.setAddr2("PL 39");
        ad.setCity("Espoo");
        ad.setCountry("Finland");
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
         ad.setZip("01000");
        List<Person> persons=   personDAO.findPersonByUsername(username);

        for(Person person:persons)
        {
             per=person;
        }
        if(persons==null || persons.isEmpty())
        {
             per=addPerson(null);
        }
        per.setAdress(ad);

        personDAO.saveOrUpdate(per);
        persons=   personDAO.findPersonByUsername(username);
        Person fetchUser=null;
        for(Person person:persons)
        {
             fetchUser=person;
        }
        assert(per.getAdress().getCity().equals(fetchUser.getAdress().getCity()));
  }

  public void testDeletingPerson() throws Exception
  {
     IPersonDAO personDAO = (IPersonDAO) applicationContext.getBean("PersonDAO");
      Person per=null;
      List<Person> persons=   personDAO.findPersonByUsername(username);

      for(Person person:persons)
      {
             per=person;
      }
      if(persons==null || persons.isEmpty())
      {
             per=addPerson(null);
      }
      personDAO.delete(per);
      persons= personDAO.findPersonByUsername(username);
      assertEquals(0, persons.size());
  }
  
}
