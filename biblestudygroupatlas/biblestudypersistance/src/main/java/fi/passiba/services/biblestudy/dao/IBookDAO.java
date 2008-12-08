package fi.passiba.services.biblestudy.dao;



import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Interface for BookDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBookDAO  extends  BaseDao<Book>{

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    @Cacheable(modelId = "biblestudyBookCache")
    public List<Book>  findBooksByBookIDSectionIDandTranslationId( long translationid, long sectionid, long bookid);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    @Cacheable(modelId = "biblestudyBookCache")
    public List<Book> findBooksByBooksectionId(long id);
  
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheFlush(modelId = "biblestudyBookCache")
    public void updateBooksinBatch(List<Book> books);
    

   @CacheFlush(modelId = "biblestudyBookCache")
    public void save(Book obj);

    @CacheFlush(modelId = "biblestudyBookCache")
    public void update(Book obj);

    @CacheFlush(modelId = "biblestudyBookCache")
    public void saveOrUpdate(Book obj);

    @CacheFlush(modelId = "biblestudyBookCache")
    public void delete(Book obj);

    @Cacheable(modelId = "biblestudyBookCache")
    public List<Book> getAll();
}