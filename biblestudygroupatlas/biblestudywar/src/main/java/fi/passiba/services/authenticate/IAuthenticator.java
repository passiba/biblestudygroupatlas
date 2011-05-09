/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.authenticate;


import fi.passiba.services.persistance.Person;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jaanakayttajatili
 */

public interface IAuthenticator {
    

	
	
	
     @Transactional(propagation = Propagation.REQUIRED)
     public void registerPerson(Person person);
     @Transactional(propagation = Propagation.REQUIRED)
     public Person updatePerson(Person person);
     @Transactional(propagation = Propagation.REQUIRED)
     public void deletePerson(Person person);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Person>  findPerson(String username);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Person>  findPersonByRolename(String rolename,String country,String city);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public Person  findPersonByPersonID(long personid);

}
