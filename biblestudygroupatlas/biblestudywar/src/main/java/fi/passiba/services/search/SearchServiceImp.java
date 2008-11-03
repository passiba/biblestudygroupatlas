/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.search;

import fi.passiba.services.persistance.Person;
import fi.passiba.services.search.ISearchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassContext;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassHitsOperations;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.gps.CompassGps;
import org.compass.gps.MirrorDataChangesGpsDevice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

/**
 *
 * @author haverinen
 */
public class SearchServiceImp implements ISearchService,InitializingBean{


    private Compass compass;
   
    
    private MirrorDataChangesGpsDevice mirrorGPS;
    private CompassGps compassGPS;
    @CompassContext
    private CompassSession compassSession;
    private Integer pageSize = 20;

    private CompassTemplate compassTemplate;

     public enum SearchType {

        USERNAME("username"), ROLENAME("rolename"),CITY("city"),COUNTRY("country");
        private String type;

        private SearchType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
     @Override
    public void afterPropertiesSet() throws Exception {


        if (compass == null) {
            throw new IllegalArgumentException(
                    "compass property must be set");
        }
        compassTemplate= new CompassTemplate(compass);
        // ensure that our gps is going to mirror all data changes from
        // here on out.
        // this will give us real time searchability of saved objects
        mirrorGPS.setMirrorDataChanges(true);
    }

    public Compass getCompass() {
        return compass;
    }
    @Required
    public void setCompass(Compass compass) {
        this.compass = compass;
    }

    public CompassGps getCompassGPS() {
        return compassGPS;
    }
    @Required
    public void setCompassGPS(CompassGps compassGPS) {
        this.compassGPS = compassGPS;
    }

    public MirrorDataChangesGpsDevice getMirrorGPS() {
        return mirrorGPS;
    }
    @Required
    public void setMirrorGPS(MirrorDataChangesGpsDevice mirrorGPS) {
        this.mirrorGPS = mirrorGPS;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    private List<Object> doSearch(HashMap data,String [] aliases,String query, int startNumber,int maxNum) {

        List<Object> hitobjects=new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.hasText(query)) {

            return null;
        }
        if(data!=null && ! data.isEmpty())
        {
            for(Iterator iter=data.keySet().iterator(); iter.hasNext();)
            {
                String key=(String)iter.next();
                String value=(String) data.get(key);
                sb.append(key+":"+value);
            }
        }else
        {
       
            sb.append("+");
            sb.append(query);
            sb.append("* ");
        }
       /*CompassHitsOperations searchHits = getHits(sb.toString(),
                    startNumber, maxNum, aliases);*/
        CompassHits searchHits= compassSession.find(sb.toString());
        System.out.println("Original Query: " +  searchHits.getQuery());
        if ( searchHits.getSuggestedQuery()!=null && searchHits.getSuggestedQuery().isSuggested()) {
            System.out.println("Did You Mean: " + searchHits.getSuggestedQuery().getSuggestedQuery());
        }
         for (CompassHit hit : searchHits) {
            Object obj=hit.getData();
            hitobjects.add(obj);
           // float score = hit.getScore();
        }
        return hitobjects;
    }
    private List<Person> populateSearchResult(List<Object> result)
    {
        List<Person> persons=new ArrayList();
        for(Object per:result)
        {
            if(per instanceof Person)
            {
                persons.add((Person)per);
            }
        }
       return persons;
    }
    @Override
    public List<Person> findPersonByRolenameWithLocation(String rolename, String country, String city) {
       
        final String[] aliases = { "Person" };
        HashMap data=new HashMap();
        data.put(SearchType.ROLENAME.getType(), rolename);

        data.put(SearchType.COUNTRY.getType(), country);
        data.put(SearchType.CITY.getType(), city);
        return populateSearchResult(doSearch(data,aliases,rolename,0,10));
    }

    @Override
    public List<Person> findPersonByUserName(String username, int startNum, int maxNum) {
       
        final String[] aliases = { "Person" };
        HashMap data=new HashMap();
        data.put(SearchType.USERNAME.getType(), username);
        return populateSearchResult(doSearch(data,aliases,username,startNum,maxNum));
    }



    private CompassHitsOperations getHits(final String searchString,
            final int start, final int max_num_hits,
            final String[] aliases) {

        CompassHitsOperations hits = compassTemplate
                .executeFind(new CompassCallback() {
                    public Object doInCompass(CompassSession session)
                            throws CompassException {

                        CompassHits hits = session.queryBuilder()
                                .queryString(searchString).toQuery()
                                .setAliases(aliases).addSort(
                                        "id",
                                        SortPropertyType.INT,
                                        SortDirection.REVERSE).hits();
                        return hits.detach(start, max_num_hits);
                    }
                });
        System.out.println("Search " + searchString + " hits "
                + hits.getLength());
        return hits;
    }
    @Override
    public void reindex() {
        compassGPS.index();
    }

   


  

   



}
