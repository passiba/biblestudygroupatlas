package fi.passiba.biblestudy.feed;


import fi.passiba.biblestudy.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import java.util.List;
import org.apache.wicket.spring.injection.annot.SpringBean;
import javax.jws.WebService;
@WebService(endpointInterface = "fi.passiba.biblestudy.feed.IGroupFeedResource")
public class GroupFeedResource implements
        IGroupFeedResource
{

    @SpringBean
    private IGroupServices groupservice;

    public List<Groups> findBibleStudyGroupsByLocation(String country, String city, String grouptype) {

        return groupservice.findGroupsByLocation(country, city, grouptype);
    }
   
  
   

  

}
