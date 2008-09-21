package fi.passiba.services.dao;


import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.persistance.Person;
import java.util.List;

/**
 * Interface for PersonDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IPersonDAO extends  BaseDao <Person>{
      public List<Person> findPersonByUsername(String username);
      public List<Person> findPersonByUserRole(String rolename,String country,String contactcity);
}