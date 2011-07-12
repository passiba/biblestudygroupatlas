package fi.passiba.services.biblestudy.dao;


import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fi.passiba.hibernate.BaseDao;
import fi.passiba.services.biblestudy.persistance.Verse;

/**
 * Interface for VerseDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IVerseDAO extends  BaseDao<Verse>{

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findVerseByVerseidBookIdChapterIdSectionIdTranslationId(long translationid,long sectionid,long bookid,long chaperid,long verseid);
    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findVersesByChapterId(long id);

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findVersesByContext(String verseContext);
	
}