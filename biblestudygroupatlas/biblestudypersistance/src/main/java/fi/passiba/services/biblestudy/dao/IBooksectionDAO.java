package fi.passiba.services.biblestudy.dao;

import fi.passiba.hibernate.BaseDao;


import fi.passiba.services.biblestudy.persistance.Booksection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Interface for BooksectionDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBooksectionDAO extends  BaseDao<Booksection> {
    @Cacheable(modelId = "biblestudySectionCache")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Booksection> findBooksectionByBooksectionIdAndBibleTranslationId(long bibletranslationid, long booksectionid);

    @Cacheable(modelId = "biblestudySectionCache")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Booksection> findBookSectionByBibleTranslationId(long id);

    @CacheFlush(modelId = "biblestudySectionCache")
    public void save(Booksection obj);

    @CacheFlush(modelId = "biblestudySectionCache")
    public void update(Booksection obj);

    @CacheFlush(modelId = "biblestudySectionCache")
    public void saveOrUpdate(Booksection obj);

    @CacheFlush(modelId = "biblestudySectionCache")
    public void delete(Booksection obj);

    @Cacheable(modelId = "biblestudySectionCache")
    public List<Booksection> getAll();


}