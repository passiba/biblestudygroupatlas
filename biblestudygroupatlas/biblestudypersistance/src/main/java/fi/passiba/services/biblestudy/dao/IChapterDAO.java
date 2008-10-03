package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * Interface for ChapterDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IChapterDAO extends  BaseDao<Chapter> {
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Chapter> findChapterByChapterIdBookIdSectionIdBibleVersionID(long translationid, long sectionid, long bookid, long chapterid);
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Chapter> findChaptersByBookId(long id);
}