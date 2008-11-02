/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages.search;

import java.util.HashMap;
import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassContext;
import org.compass.core.CompassDetachedHits;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassTransaction;
import org.compass.core.support.search.CompassSearchCommand;
import org.compass.core.support.search.CompassSearchHelper;
import org.compass.core.support.search.CompassSearchResults;
import org.compass.gps.CompassGps;
import org.compass.gps.MirrorDataChangesGpsDevice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

/**
 *
 * @author haverinen
 */
public class compassSearchUtil{

   
    private Compass compass;
    private CompassSearchHelper searchHelper;
    private CompassSearchResults searchResults;

    //private CompassTemplate template;
    /*private CompassTemplate compassTemplate;

    private MirrorDataChangesGpsDevice mirrorGPS;
    private CompassGps compassGPS;*/

    @CompassContext
    private CompassSession compassSession;
    private Integer pageSize=20;
    private String query;
    public compassSearchUtil()
    {
         searchHelper = new CompassSearchHelper(getCompass(), getPageSize());

    }
    private Compass getCompass() {
        return compass;
    }
    @Required
    public void setCompass(Compass compass) {
        this.compass = compass;
    }

    /**
     * Sets the page size for the pagination of the results. If not set, not
     * pagination will be used.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Returns the page size for the pagination of the results. If not set, not
     * pagination will be used.
     *
     * @param pageSize
     *            The page size when using paginated results
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public CompassSearchHelper getSearchHelper() {
        return searchHelper;
    }

   public void setSearchHelper(CompassSearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


   public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

  /*  public CompassGps getCompassGPS() {
        return compassGPS;
    }

    public void setCompassGPS(CompassGps compassGPS) {
        this.compassGPS = compassGPS;
    }

    public CompassTemplate getCompassTemplate() {
        return compassTemplate;
    }
    @Required
    public void setCompassTemplate(CompassTemplate compassTemplate) {
        this.compassTemplate = compassTemplate;
    }

    public MirrorDataChangesGpsDevice getMirrorGPS() {
        return mirrorGPS;
    }
     @Required
    public void setMirrorGPS(MirrorDataChangesGpsDevice mirrorGPS) {
        this.mirrorGPS = mirrorGPS;
    }*/

   public void doSearch(Integer page)
   {
        final CompassSearchCommand searchCommand = new CompassSearchCommand();
        searchCommand.setQuery(getQuery());

       //final CompassSearchCommand searchCommand = (CompassSearchCommand) command;
        if (!StringUtils.hasText(searchCommand.getQuery())) {
           
            return ;
        }
        Integer pageRequested = page -1;
        searchCommand.setPage(pageRequested);

        CompassSearchResults searchResults = searchHelper.search(searchCommand);
        CompassHit[] hits=searchResults.getHits();
        HashMap data = new HashMap();

        data.put("searchcommand", searchCommand);
        data.put("searchcommandResult", searchResults);
        CompassHits results=compassSession.find(query);
        for (CompassHit hit:results)
        {
            //Bug bug= (Bug)hit.getData(0);
            float score = hit.getScore();
        }
       /* CompassHits hits= template.find(query);
        for (CompassHit hit:hits)
        {
            //Bug bug= (Bug)hit.getData(0);
            float score = hit.getScore();
        }*/


   }

   /* @Override
    public void afterPropertiesSet() throws Exception {
       this.compassTemplate = new CompassTemplate(compass);
        // ensure that our gps is going to mirror all data changes from
        // here on out.
        // this will give us real time searchability of saved objects
        mirrorGPS.setMirrorDataChanges(true);
    }*/
    
     /*public String doSearch(int page) {
        final CompassSearchCommand searchCommand = new
CompassSearchCommand();
        searchCommand.setPage(page);
        searchCommand.setQuery(searchCommand.getQuery());
        if (!StringUtils.hasText(searchCommand.getQuery())) {
            return "search";
        }
        searchResults = (CompassSearchResults)
template.execute(

CompassTransaction.TransactionIsolation.READ_ONLY_READ_COMMITTED, new
CompassCallback() {
            public Object doInCompass(CompassSession session) throws
CompassException {
                return performSearch(searchCommand, session);
            }
        });
        return "searchResults";
    }

    protected CompassSearchResults performSearch(CompassSearchCommand
searchCommand, CompassSession session) {
        long time = System.currentTimeMillis();
        CompassQuery query = buildQuery(searchCommand, session);
        CompassHits hits = query.hits();
        CompassDetachedHits detachedHits;
        CompassSearchResults.Page[] pages = null;
        if (pageSize == null) {
       
            detachedHits = hits.detach();
        } else {
            int iPageSize = pageSize.intValue();
            int page = 0;
            int hitsLength = hits.getLength();
            if (searchCommand.getPage() != null) {
                page = searchCommand.getPage().intValue();
            }
            int from = page * iPageSize;
            if (from > hits.getLength()) {
                from = hits.getLength() - iPageSize;
             
                detachedHits = hits.detach(from, hitsLength);
            } else if ((from + iPageSize) > hitsLength) {
               
                detachedHits = hits.detach(from, hitsLength);
            } else {
               
                detachedHits = hits.detach(from, iPageSize);
            }
          
            int numberOfPages = (int) Math.ceil((float) hitsLength /
iPageSize);
            pages = new CompassSearchResults.Page[numberOfPages];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new CompassSearchResults.Page();
                pages[i].setFrom(i * iPageSize + 1);
                pages[i].setSize(iPageSize);
                pages[i].setTo((i + 1) * iPageSize);
                if (from >= (pages[i].getFrom() - 1) && from <
pages[i].getTo()) {
                    pages[i].setSelected(true);
                } else {
                    pages[i].setSelected(false);
                }
            }
            if (numberOfPages > 0) {
                CompassSearchResults.Page lastPage = pages[numberOfPages
- 1];
                if (lastPage.getTo() > hitsLength) {
                    lastPage.setSize(hitsLength - lastPage.getFrom());
                    lastPage.setTo(hitsLength);
                }
            }
        }
        time = System.currentTimeMillis() - time;
        CompassSearchResults searchResults = new CompassSearchResults(detachedHits.getHits(), time);
        searchResults.setPages(pages);
        return searchResults;
    }
    protected CompassQuery buildQuery(CompassSearchCommand searchCommand, CompassSession session) {
        return session.queryBuilder().queryString(searchCommand.getQuery().trim()).toQuery();
    }*/
    

    



}
