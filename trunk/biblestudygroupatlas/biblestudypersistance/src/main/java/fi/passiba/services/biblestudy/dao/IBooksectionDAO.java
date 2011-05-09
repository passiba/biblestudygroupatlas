package fi.passiba.services.biblestudy.dao;

import fi.passiba.hibernate.BaseDao;


import fi.passiba.services.biblestudy.persistance.Booksection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


/**
 * Interface for BooksectionDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBooksectionDAO extends  BaseDao<Booksection> {
  
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Booksection> findBooksectionByBooksectionIdAndBibleTranslationId(long bibletranslationid, long booksectionid);

   
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Booksection> findBookSectionByBibleTranslationId(long id);

   

}