package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDao;
import java.util.List;

import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.biblestudy.persistance.Verse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * Interface for BiblesessionDAO.
 * 
 * @author haverinen
 */

public interface IBiblesessionDAO extends  BaseDao<Biblesession>{

    
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Verse> findBibleSessionVerses(long sessionid);
    
   
    
    
	
}