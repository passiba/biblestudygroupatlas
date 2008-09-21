/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.authenticate;

//import fi.passiba.services.persistance.Users;
import fi.passiba.services.dao.IPersonDAO;

import fi.passiba.services.persistance.Person;
import java.util.List;

/**
 *
 * @author jaanakayttajatili
 */
public class AuthenticatorImp implements IAuthenticator {
     
    
    private IPersonDAO personDAO= null;
   
    
    
    
    
    public IPersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    

    
    
    
    public void registerPerson(Person person) {
      
      personDAO.save(person);
    }

    public Person updatePerson(Person person) {
        personDAO.saveOrUpdate(person);
        return person;
    }

    public void deletePerson(Person person) {
        personDAO.delete(person);
    }

    public  List<Person> findPerson(String username) {
      return personDAO.findPersonByUsername(username);
    }

    public List<Person> findPersonByRolename(String rolename,String country,String city) {
        return personDAO.findPersonByUserRole(rolename,country,city);
    }

    public Person findPersonByPersonID(long personid) {
       return personDAO.getById(personid);
    }
    
   
}
