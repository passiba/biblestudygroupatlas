package fi.passiba.services.biblestudy.dao;



import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * Interface for BookDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBookDAO  extends  BaseDao<Book>{

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Book>  findBooksByBookIDSectionIDandTranslationId( long translationid, long sectionid, long bookid);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Book> findBooksByBooksectionId(long id);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Book findBooksByBookDataSourcId(long bookDatasourceid);
}