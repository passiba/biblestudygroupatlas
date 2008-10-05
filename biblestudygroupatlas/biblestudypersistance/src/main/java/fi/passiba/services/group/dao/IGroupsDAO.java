package fi.passiba.services.group.dao;




import fi.passiba.hibernate.BaseDao;
import fi.passiba.hibernate.PaginationInfo;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for GroupsDAO.
 * 
 * @author haverinen
 */

public interface IGroupsDAO extends BaseDao<Groups>{
         
         @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
         public List<Groups> findGroupsByLocation(String country,String city,String grouptype);
         @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
         public List<Person> findGroupsPersonsByGroupId(Long id);
         @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
         public List<Groups> findGroupsByPersonId(Long id);
         @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
         public PaginationInfo findPagingInfoForGroups(int maxResult);
         
         @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
         public List<Biblesession> findBibleSessionsByGroupId(long groupid);
    
}