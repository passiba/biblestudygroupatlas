package fi.passiba.services.biblestudy.datamining.dao;


import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Interface for VerseDAO.
 * 
 * @author haverinen
 */

public interface IBookDatasouceDAO extends  BaseDao<Bookdatasource>{
    
      @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
      public List<Bookdatasource> findBookDataSourcesByStatus(String status);
	
}