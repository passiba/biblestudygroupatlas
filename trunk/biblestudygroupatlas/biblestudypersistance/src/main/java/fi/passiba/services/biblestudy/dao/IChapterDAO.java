package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.util.List;

/**
 * Interface for ChapterDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IChapterDAO extends  BaseDao<Chapter> {
    
    public List<Chapter> findChapterByChapterIdBookIdSectionIdBibleVersionID(long translationid, long sectionid, long bookid, long chapterid);
    public List<Chapter> findChaptersByBookId(long id);
}