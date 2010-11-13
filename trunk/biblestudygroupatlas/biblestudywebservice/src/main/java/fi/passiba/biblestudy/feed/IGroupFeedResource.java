/*
 * This class provides Web Services interface using apache cfx web service
 * framework to implement JAX-WS webservice
 */

package fi.passiba.biblestudy.feed;

import fi.passiba.services.group.persistance.Groups;
import java.util.List;
import javax.jws.WebService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author haverinen
 */
@WebService
public interface IGroupFeedResource {
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
     public List<Groups> findBibleStudyGroupsByLocation(String country,String city,String grouptype);
}
