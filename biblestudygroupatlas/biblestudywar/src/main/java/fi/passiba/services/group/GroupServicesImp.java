/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.group;


import fi.passiba.hibernate.PaginationInfo;
import fi.passiba.services.group.dao.IGroupsDAO;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import java.util.List;

/**
 *
 * @author haverinen
 */
public class GroupServicesImp implements IGroupServices{

    
    
     private IGroupsDAO groupDAO= null;

    public IGroupsDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(IGroupsDAO groupDAO) {
        this.groupDAO = groupDAO;
    }
    
   
    
    
    public List<Groups> findGroupsByLocation(String country, String city,String grouptype) {
       
       return groupDAO.findGroupsByLocation(country, city, grouptype);
    }

    
    
    
    public void addGroup(Groups group) {
       groupDAO.save(group);
    }

    public Groups updateGroup(Groups group) {
        groupDAO.saveOrUpdate(group);
        return group;
    }

    public void deleteGroup(Groups group) {
       groupDAO.delete(group);
    }
    
   

    public List<Person> findGroupsPersonsByGroupId(Long id) {
       return groupDAO.findGroupsPersonsByGroupId(id);
    }


    public List<Groups> findGroupsByPersonId(Long id) {
        return groupDAO.findGroupsByPersonId(id);
    }

    public List<Groups> findAllGroups() {
        return groupDAO.getAll();
    }

    public List<Groups> findGroupsWithPaging(PaginationInfo page) {
        return groupDAO.getPageOfDataAll(page);
    }

    public PaginationInfo findPagingInfoForGroups(int maxResult) {
        return groupDAO.findPagingInfoForGroups(maxResult);
    }

    public Groups findGroupByGroupId(Long id) {
        return groupDAO.getById(id);
    }

   
    
   

}
