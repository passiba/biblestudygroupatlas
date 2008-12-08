package fi.passiba.services.biblestudy.dao;



import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import java.util.List;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Interface for BibletranslationDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IBibletranslationDAO  extends  BaseDao<Bibletranslation> {

     @CacheFlush(modelId="bibletranslationCache")
     public void save(Bibletranslation obj);
     @CacheFlush(modelId="bibletranslationCache")
     public void update(Bibletranslation obj);
     @CacheFlush(modelId="bibletranslationCache")
     public void saveOrUpdate(Bibletranslation obj);
     @CacheFlush(modelId="bibletranslationCache")
     public void delete(Bibletranslation obj);
     @Cacheable(modelId="bibletranslationCache")
	 public List<Bibletranslation> getAll();
}