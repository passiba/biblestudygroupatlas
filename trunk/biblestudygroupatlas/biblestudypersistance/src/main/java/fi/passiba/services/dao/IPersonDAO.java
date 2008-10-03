package fi.passiba.services.dao;


import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.persistance.Person;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * Interface for PersonDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IPersonDAO extends  BaseDao <Person>{
      @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
      public List<Person> findPersonByUsername(String username);
      @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
      public List<Person> findPersonByUserRole(String rolename,String country,String contactcity);
}