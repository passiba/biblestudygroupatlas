/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.search;

import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.List;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
 @Transactional(propagation = Propagation.REQUIRED)
public interface ISearchService {

     
     public List<Person>  findPersonByUserName(String username, int startNum,int maxNum) throws ParseException;
     public List<Person>  findPersonByRolenameWithLocation(String rolename,String country,String city) throws ParseException;

     public List<Groups> findGroupsByLocation(String country,String city,String grouptype) throws ParseException;

     @Transactional(propagation = Propagation.REQUIRED)
     public void reindex();

}
