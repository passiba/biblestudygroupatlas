package fi.passiba.services.group.dao;




import fi.passiba.hibernate.BaseDao;
import fi.passiba.hibernate.PaginationInfo;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.List;

/**
 * Interface for GroupsDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface IGroupsDAO extends BaseDao<Groups>{
    
         public List<Groups> findGroupsByLocation(String country,String city,String grouptype);
         public List<Person> findGroupsPersonsByGroupId(Long id);
         public List<Groups> findGroupsByPersonId(Long id);
         public PaginationInfo findPagingInfoForGroups(int maxResult);
    
}