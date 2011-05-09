package fi.passiba.biblestudy.feed;


import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/*
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.QueryParam;

import org.apache.wicket.spring.injection.annot.SpringBean;



@ProduceMime("application/atom+xml")
@Path("/")*/
public class GroupFeedResource
{
   
   /* @SpringBean
    private IGroupServices groupservice;
    
    private static final List<String> allGroupTypes = Arrays.asList(new String[]{"Miestenpiiri", "Naistenpiiri", "Raamattupiiri", "Pyhäkoulu", "Äiti/lapsi-piiri", "Rukouspiiri", "Nuoret aikuiset"});
   

    
 
    @GET
    public String fingBibleStudyGroupByLocation(@QueryParam("grouptype")String grouptype, @QueryParam("city")String city, 
            @QueryParam("country")String country)
    {
        
        StringBuilder feed = new StringBuilder();
        if(grouptype!=null && city !=null && country!=null)
        {
            List<Groups> groups =groupservice.findGroupsByLocation(country, city, grouptype);
            SimpleDateFormat sdfOutput =  
                new SimpleDateFormat  (  "yyyy-mm-dd:HH:MM:SS"  ) ; 
  

            feed.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
            feed.append("<feed xmlns=\'http://www.w3.org/2005/Atom\'");
            feed.append("xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos# "); 
            feed.append("xmlns:georss=\"http://www.georss.org/georss>");
            feed.append("<title>Bible studygroups by Location with geodata</title>");
            feed.append("<link rel='self' href='http://localhost:8084/biblestudy-1.0.0/atom/?grouptype=" +grouptype+ "&amp;city="+city+"&amp;country="+country+" />");

            feed.append("<link rel='alternate' href=\'http://www.passiba.fi/groups/' />");
            feed.append("<updated>2008-09-09T15:41:19Z</updated>");
            feed.append("<id>tag:passsiba.fi,2008:/groups/group/geo</id>");
                for (Groups group : groups)
                {
                    Adress address=groupservice.findGroupAddressByGroupId(group.getId());
                    feed.append("<entry><title>"+group.getName()+"</title>");
                    feed.append("<id>tag:passsiba.fi,2008:/group/"+group.getId()+"</id>");

                    feed.append("<link rel='alternate' type='text/html'  href='http://www.passiba.fi/groups/group?id="+group.getId()+"' />");
                 
                    feed.append("<summary>"+group.getDescription()+"</summary>");
                    feed.append("<updated> "+sdfOutput.format(group.getUpdatedOn())+"</updated>");
                    feed.append("<published> "+sdfOutput.format(group.getUpdatedOn())+"</published>");
                   
                    feed.append("<content type='html'>Available biblegroup in the area " + group.getCongregationname() + " ,congregations website href='http://"+group.getCongregationwebsiteurl()+ " </content>");

                     feed.append("<link rel='enclosure' type='text/html' href='"+ group.getCongregationname() +"' />");
                     
                    feed.append("<author><name>"+group.getCongregationname()+"</name></author>");
                    feed.append("<georss:point>"+address.getLocation_lat() +" "+address.getLocation_lng()+ "</georss:point>");
                    feed.append("<geo:lat>"+address.getLocation_lat()+"</geo:lat>");
                    feed.append("<geo:long>"+address.getLocation_lng()+"</geo:long>");                    

                    feed.append("</entry>");
                }
            
             feed.append("</feed>");
        }
       

        return feed.toString();
    }*/
   

  

}
