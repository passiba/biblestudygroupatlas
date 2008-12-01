package fi.passiba.groups;
import fi.passiba.AbstractTransactionalJUnit4SpringContext;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Users;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public final class PersionServiceImpTest extends AbstractTransactionalJUnit4SpringContext{

  private String username="passiba";
  @Autowired
  private IAuthenticator authenticator;
  private Person addPerson(Person person)throws Exception
  {
     
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
          regularUser.setRolename("User");
          regularUser.setStatus("Aktiivinen");
          person.setFk_userid(regularUser);
      }

      authenticator.updatePerson(person);
      return person;
  }
  @Test
  public void testAddingNewPerson() throws Exception
  {

       

        Person  person=addPerson(null);
        List<Person> persons=    authenticator.findPerson(username);
        Person fetchUser=null;
        for(Person per:persons)
        {
             fetchUser=person;
        }
        assert(person.getFk_userid().getUsername().equals(fetchUser.getFk_userid().getUsername()));
  }
  @Test
   public void testUpdatingPerson() throws Exception
  {
      
        Person per=null;
        Adress ad= new Adress();
        ad.setAddr1("Testikuja 1 b");
        ad.setAddr2("PL 39");
        ad.setCity("Espoo");
        ad.setCountry("Finland");
        ad.setPhone("0505555555");
        ad.setState("Uusimaa");
         ad.setZip("01000");
        List<Person> persons=authenticator.findPerson(username);

        for(Person person:persons)
        {
             per=person;
        }
        if(persons==null || persons.isEmpty())
        {
             per=addPerson(null);
        }
        per.setAdress(ad);

        authenticator.updatePerson(per);
        persons=authenticator.findPerson(username);
        Person fetchUser=null;
        for(Person person:persons)
        {
             fetchUser=person;
        }
        assert(per.getAdress().getCity().equals(fetchUser.getAdress().getCity()));
  }
   @Test
  public void testDeletingPerson() throws Exception
  {
    
      Person per=null;
      List<Person> persons=authenticator.findPerson(username);

      for(Person person:persons)
      {
             per=person;
      }
      if(persons==null || persons.isEmpty())
      {
             per=addPerson(null);
      }
      authenticator.deletePerson(per);
      persons= authenticator.findPerson(username);
      assertEquals(0, persons.size());
  }
  
}
