package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDao;
import java.util.List;

import fi.passiba.services.biblestudy.persistance.Verse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * Interface for VerseDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IVerseDAO extends  BaseDao<Verse>{

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findVerseByVerseidBookIdChapterIdSectionIdTranslationId(long translationid,long sectionid,long bookid,long chaperid,long verseid);
	
}