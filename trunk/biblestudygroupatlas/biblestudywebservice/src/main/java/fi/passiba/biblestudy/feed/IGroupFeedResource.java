
/*
* This class provides Web Services interface using apache cfx web service
* framework to implement JAX-WS webservice
 */
package fi.passiba.biblestudy.feed;

//~--- non-JDK imports --------------------------------------------------------

import fi.passiba.services.group.persistance.Groups;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

import javax.jws.WebService;

/**
 *
 * @author haverinen
 */
@WebService
public interface IGroupFeedResource {
    public List<Groups> findBibleStudyGroupsByLocation(String country, String city, String grouptype);
}


//~ Formatted by Jindent --- http://www.jindent.com
