package fi.passiba.services.biblestudy.dao;

import fi.passiba.hibernate.BaseDao;


import fi.passiba.services.biblestudy.persistance.Booksection;
import java.util.List;

/**
 * Interface for BooksectionDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBooksectionDAO extends  BaseDao<Booksection> {
    
      public List<Booksection> findBooksectionByBooksectionIdAndBibleTranslationId(long bibletranslationid,long booksectionid);
      public List<Booksection> findBookSectionByBibleTranslationId(long id);
}