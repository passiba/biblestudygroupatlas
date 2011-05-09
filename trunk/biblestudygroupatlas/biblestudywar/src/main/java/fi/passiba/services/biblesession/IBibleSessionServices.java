/*
 * Handles the adding,modifications and deletion of BibleSession,whatever it is 
 *  group or person specific biblesesssion
 * 
 */

package fi.passiba.services.biblesession;

import fi.passiba.services.biblestudy.persistance.Biblesession;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
public interface IBibleSessionServices {
    
     @Transactional(propagation = Propagation.REQUIRED)
     public void addBibleSession(Biblesession session,Object sessionOwner);
     @Transactional(propagation = Propagation.REQUIRED)
     public Biblesession updateBibleSession(Biblesession session,Object sessionOwner);
     @Transactional(propagation = Propagation.REQUIRED)
     public void deleteBibleSession(Biblesession session,Object sessionOwner);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Biblesession> findBibleSessionsByGroupId(long groupid);
     @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Biblesession> findBibleSessionsByPersonId(long personid);

}
