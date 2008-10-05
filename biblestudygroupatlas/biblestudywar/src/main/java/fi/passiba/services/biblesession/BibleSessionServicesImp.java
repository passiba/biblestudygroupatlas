/*
 *Handles the adding,modifications and deletion of BibleSession,whatever it is 
 *  group or person specific biblesesssion
 */

package fi.passiba.services.biblesession;

import fi.passiba.services.biblestudy.dao.IBiblesessionDAO;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.dao.IPersonDAO;
import fi.passiba.services.group.dao.IGroupsDAO;
import fi.passiba.services.persistance.Person;
import java.util.List;

/**
 *
 * @author haverinen
 */
public class BibleSessionServicesImp implements IBibleSessionServices {
    
     private IGroupsDAO groupDAO= null;
     private IPersonDAO personDAO= null;
     private IBiblesessionDAO biblesessionDAO;

    public IBiblesessionDAO getBiblesessionDAO() {
        return biblesessionDAO;
    }

    public void setBiblesessionDAO(IBiblesessionDAO biblesessionDAO) {
        this.biblesessionDAO = biblesessionDAO;
    }

    public IGroupsDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(IGroupsDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public IPersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }
     
     
    /**
     *  adds new biblestudy Session
     * @param Biblesession session
     * @param Object sessionOwner
     */
    public void addBibleSession(Biblesession session,Object sessionOwner) {
        
        biblesessionDAO.save(session);
        if(sessionOwner!=null && sessionOwner instanceof Person)
        {
            personDAO.update((Person)sessionOwner);
        }
    }
    
    /**
     *  updates existing biblesession
     * @param session
     * @param Object sessionOwner
     * @return Biblesession
     */
    public Biblesession updateBibleSession(Biblesession session,Object sessionOwner) {
        
        biblesessionDAO.saveOrUpdate(session);
        if(sessionOwner!=null && sessionOwner instanceof Person)
        {
            personDAO.update((Person)sessionOwner);
        }
        return session;
    }
    /**
     * deletes existing biblesession
     * 
     * @param session
     * @param Object sessionOwner
     */
    public void deleteBibleSession(Biblesession session,Object sessionOwner) {
        biblesessionDAO.delete(session);
        if(sessionOwner!=null && sessionOwner instanceof Person)
        {
            personDAO.update((Person)sessionOwner);
        }
    }
    /**
     *  Returns list of Biblesessionf by given groupid
     * @param groupid
     * @return List<Biblesession>
     */
    public List<Biblesession> findBibleSessionsByGroupId(long groupid) {
       return groupDAO.findBibleSessionsByGroupId(groupid);
    }
    
    /**
     * Returns list of Biblesessionf by given personid
     * @param personid
     * @return List<Biblesession>
     */
    public List<Biblesession> findBibleSessionsByPersonId(long personid) {
        return personDAO.findBibleSessionsByPersonId(personid);
    }

}
