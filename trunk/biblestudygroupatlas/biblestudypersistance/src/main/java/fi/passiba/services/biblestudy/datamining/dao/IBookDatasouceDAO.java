package fi.passiba.services.biblestudy.datamining.dao;


import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;


/**
 * Interface for VerseDAO.
 * 
 * @author haverinen
 */

public interface IBookDatasouceDAO extends  BaseDao<Bookdatasource>{
    
      @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
      public List<Bookdatasource> findBookDataSourcesByStatus(String status);
	
}