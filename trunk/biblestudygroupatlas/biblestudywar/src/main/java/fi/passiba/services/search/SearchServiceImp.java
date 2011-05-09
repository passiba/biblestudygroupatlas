/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.search;

import fi.passiba.services.biblestudy.dao.IVerseDAO;
import fi.passiba.services.biblestudy.persistance.Verse;
import fi.passiba.services.dao.IPersonDAO;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author haverinen
 */
@Service("SearchService")
public class SearchServiceImp implements ISearchService{

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private IPersonDAO personDAO;

    @Autowired

    private IVerseDAO versonDao;
   
      
   

    protected Session getSession() {
        return sessionFactory.openSession();
    }
    public FullTextSession getFullTextSession() {
        return Search.getFullTextSession(getSession());
    }
    /**
     *
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return List<Person> persons
     * @throws org.apache.lucene.queryParser.ParseException
     */
     private List<Person> searchPerson(String []searchQuery,String []fields,int pageNumber,int window) throws ParseException {
        for(String query:searchQuery)
        {
            if (!StringUtils.hasText(query)) {

                return null;
            }
        }
        Query query = searchPersonQuery(searchQuery,fields,pageNumber,window);

        List<Person> persons = query.list();
        return persons;
    }
    /**
     *
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return List<Person> persons
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private List<Person> searchPerson(String searchQuery,String []fields,Map<String, Float> boostPerField,int pageNumber,int window) throws ParseException {

       if (!StringUtils.hasText(searchQuery)) {

                return null;
        }

        Query query = searchPersonQuery(searchQuery,fields,boostPerField,pageNumber,window);

        List<Person> persons = query.list();
        return persons;
    }
    /**
     *   searching groups with single query run againts multiple fields
     *  with boostPerField factor added
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return List<Groups> groups
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private List<Groups> searchGroups(String searchQuery,String []fields,Map<String, Float> boostPerField,int pageNumber,int window) throws ParseException {

        if (!StringUtils.hasText(searchQuery)) {

            return null;
        }
        Query query = searchGroupQuery(searchQuery,fields,boostPerField,pageNumber,window);

         List<Groups> groups = query.list();
        return groups;
    }
    /**
     *   searching groups with single query run againts multiple fields
     *  with boostPerField factor added
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return List<Groups> groups
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private List<Groups> searchGroups(String []searchQueries,String []fields,int pageNumber,int window) throws ParseException {

         for(String query:searchQueries)
        {
            if (!StringUtils.hasText(query)) {

                return null;
            }
        }
        Query query = searchGroupQuery(searchQueries,fields,pageNumber,window);

         List<Groups> groups = query.list();
        return groups;
    }
    /**
     *  single search peformed againts multiple fields with boosPerfield factor in  Groups indexed entities
     *
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return Query
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private Query searchGroupQuery(String searchQuery,String []fields, Map<String, Float> boostPerField,int pageNumber,int window) throws ParseException {
        //lucene part

        QueryParser parser = new MultiFieldQueryParser(fields, new WhitespaceAnalyzer(), boostPerField);
        org.apache.lucene.search.Query luceneQuery;
        luceneQuery = parser.parse(searchQuery);

        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Groups.class);

        query.setFirstResult( (pageNumber - 1) * window );
        query.setMaxResults(window);

        return query;
    }
    /**
     *  multiple search queries peformed againts matchig fields in Groups indexed entities
     *
     * @param searchQuery
     * @param fields
     * @param pageNumber
     * @param  window
     * @return Query
     * @throws org.apache.lucene.queryParser.ParseException
     */
     private Query searchGroupQuery(String [] searchQuery,String []fields,int pageNumber,int window) throws ParseException {
        //lucene part
       org.apache.lucene.search.Query luceneQuery = MultiFieldQueryParser.parse(searchQuery,
fields, new WhitespaceAnalyzer());

        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Groups.class);

        query.setFirstResult( (pageNumber - 1) * window );
        query.setMaxResults(window);

        return query;
    }
    /**
     * Used to guery multiple queries againts multipe fields
     * @param searchQuery
     * @param fields
     * @return
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private Query searchPersonQuery(String [] searchQuery,String []fields,int pageNumber,int window) throws ParseException {
        //lucene part
        org.apache.lucene.search.Query luceneQuery = MultiFieldQueryParser.parse(searchQuery,
fields, new WhitespaceAnalyzer());
     
        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Person.class);

        query.setFirstResult( (pageNumber - 1) * window );
        query.setMaxResults(window);
        return query;
    }
    /**
     *  Userd to guery single query againts multiple fields
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return Query
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private Query searchPersonQuery(String  searchQuery,String []fields, Map<String, Float> boostPerField,int pageNumber,int window) throws ParseException {
        //lucene part

       QueryParser parser = new MultiFieldQueryParser(fields ,new WhitespaceAnalyzer(), boostPerField);
       org.apache.lucene.search.Query luceneQuery;
       luceneQuery = parser.parse(searchQuery);

        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Person.class);
        query.setFirstResult( (pageNumber - 1) * window );
        query.setMaxResults(window);
        return query;
    }
    /**
     * useed to find persons using location inforation and rolename as searchcriteria
     * 
     * @param rolename
     * @param country
     * @param city
     * @param pageNumber
     * @param  window
     * @return List<Person>
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Person> findPersonByRolenameWithLocation(String rolename, String country, String city,int pageNumber,int window) throws ParseException {
       
       final String[] fields = { "fk_userid.rolename","adress.country","adress.city"};
       String[] queries = new String[]{rolename, country,city};
        return searchPerson(queries,fields,pageNumber,window);
       
    }
    /**
     *  Fint the person by using username as searchcriteria
     * 
     * @param username
     * @param startNum
     * @param maxNum
     * @return
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Person> findPersonByUserName(String username, int startNum, int maxNum) throws ParseException {
       
        final String[] fields = { "fk_userid.username" };
        Map<String, Float> boostPerField = new HashMap<String, Float>(4);
        boostPerField.put("fk_userid.username", (float) 5);
        boostPerField.put("lastname", (float) .2);
        return searchPerson(username,fields,boostPerField,startNum,maxNum);
    }

    @Override
    public List<Person> findPersonByLocation(String country, String city,int pageNumber,int window) throws ParseException {
        final String[] fields = { "adress.city","adress.country" };

       String[] queries = new String[]{city,country};
       return searchPerson(queries,fields,pageNumber,window);
    }


   
    /**
     *  Returns the Group entities matching the given search criteria country and city
     * 
     * @param country
     * @param city
     * @param pageNumber
     * @param  window
     * @return List<Groups> groups
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Groups> findGroupsByLocation(String country, String city,int pageNumber,int window) throws ParseException{
       
        final String[] fields = { "adress.city","adress.country" };

       String[] queries = new String[]{city,country};
       List<Groups> groups= searchGroups(queries,fields,pageNumber,window);
       return  groups;
        
    }
    /**
     *  Returns the group entities matching the given search criteria country and grouptype
     * 
     * @param country
     * @param type
     * @param pageNumber
     * @param  window
     * @return
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Groups> findGroupsByType(String country, String type,int pageNumber,int window) throws ParseException {

       final String[] fields = { "grouptypename","adress.country" };
       String[] queries = new String[]{type,country};
       List<Groups> groups= searchGroups(queries,fields,pageNumber,window);
        return  groups;



    }
    /**
     * Finds the group entities matching the given groupname
     * @param groupName
     * @param startNum
     * @param maxNum
     * @return List<Groups>
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Groups> findGroupsByName(String groupName, int startNum, int maxNum) throws ParseException {
        final String[] fields = { "name" };
        Map<String, Float> boostPerField = new HashMap<String, Float>(4);
        boostPerField.put("name", (float) 5);
        return searchGroups(groupName,fields,boostPerField,startNum,maxNum);

    }
    /**
     *  multiple search queries peformed againts matchig fields in Verses indexed entities
     *
     * @param searchQuery
     * @param fields
     * @param pageNumber
     * @param  window
     * @return Query
     * @throws org.apache.lucene.queryParser.ParseException
     */
     private Query searchVersesQuery(String [] searchQuery,String []fields,int pageNumber,int window) throws ParseException {
        //lucene part
       org.apache.lucene.search.Query luceneQuery = MultiFieldQueryParser.parse(searchQuery,fields, new WhitespaceAnalyzer());
      

       /*  WildcardQuery qr =
new WildcardQuery(new Term(fields[0], searchQuery[0]));*/
        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery( luceneQuery, Verse.class);

    //    query.setFirstResult( (pageNumber - 1) * window );
     //   query.setMaxResults(window);

        return query;
    }
     /**
     *   searching veres with single query run againts multiple fields
     *  with boostPerField factor added
     * @param searchQuery
     * @param fields
     * @param boostPerField
     * @param pageNumber
     * @param  window
     * @return List<Verse> verses
     * @throws org.apache.lucene.queryParser.ParseException
     */
    private List<Verse> searchVerses(String []searchQueries,String []fields,int pageNumber,int window) throws ParseException {

         for(String query:searchQueries)
        {
            if (!StringUtils.hasText(query)) {

                return null;
            }
        }
        Query query = searchVersesQuery(searchQueries,fields,pageNumber,window);

         List<Verse> verses = query.list();
        return verses;
    }
    /**
     * Returns the verses mathinc the search criteria
     * 
     * @param verseContext
     * @param pageNumber
     * @param window
     * @return List<Verse>
     * @throws org.apache.lucene.queryParser.ParseException
     */
    @Override
    public List<Verse> findVersesByContext(String verseContext, int pageNumber, int window) throws ParseException {
       final String[] fields ={"verse_text"};
       String[] queries = new String[]{verseContext};
       //List<Verse> verses= searchVerses(queries,fields,pageNumber,window);
       List<Verse> verses=versonDao.findVersesByContext(verseContext);
       if(verses!=null && verses.size()>window)
       {
            return verses.subList((pageNumber - 1) * window, window);
       }
       return verses;
    }




  

   



}