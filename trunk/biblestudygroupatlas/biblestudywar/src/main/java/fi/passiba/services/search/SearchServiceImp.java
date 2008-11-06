/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.search;

import fi.passiba.services.dao.IPersonDAO;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.util.StringUtils;

/**
 *
 * @author haverinen
 */
public class SearchServiceImp implements ISearchService{

    private SessionFactory sessionFactory;
    private IPersonDAO personDAO= null;




    public IPersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }
      

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return getSessionFactory().openSession();
    }
    public FullTextSession getFullTextSession() {
        return Search.getFullTextSession(getSession());
    }
     private List<Person> searchPerson(String searchQuery,String []fields,Map<String, Float> boostPerField) throws ParseException {

        if (!StringUtils.hasText(searchQuery)) {

            return null;
        }
        Query query = searchPersonQuery(searchQuery,fields,boostPerField);

        List<Person> persons = query.list();
        return persons;
    }
    private List<Groups> searchGroups(String searchQuery,String []fields,Map<String, Float> boostPerField) throws ParseException {

        if (!StringUtils.hasText(searchQuery)) {

            return null;
        }
        Query query = searchGroupQuery(searchQuery,fields,boostPerField);

         List<Groups> groups = query.list();
        return groups;
    }
    private Query searchGroupQuery(String searchQuery,String []fields, Map<String, Float> boostPerField) throws ParseException {
        //lucene part

        QueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer(), boostPerField);
        org.apache.lucene.search.Query luceneQuery;
        luceneQuery = parser.parse(searchQuery);

        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Groups.class);

        return query;
    }
    private Query searchPersonQuery(String searchQuery,String []fields, Map<String, Float> boostPerField) throws ParseException {
        //lucene part
       
        QueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer(), boostPerField);
        org.apache.lucene.search.Query luceneQuery;
        luceneQuery = parser.parse(searchQuery);

        //Hibernate Search
        final FullTextQuery query = getFullTextSession().createFullTextQuery(luceneQuery, Person.class);

        return query;
    }
  
    
    @Override
    public List<Person> findPersonByRolenameWithLocation(String rolename, String country, String city) throws ParseException {
       
        final String[] fields = { "fk_userid.rolename" };

        Map<String, Float> boostPerField = new HashMap<String, Float>(4);
        boostPerField.put("fk_userid.rolename", (float) 4);
        boostPerField.put("adress.city", (float) 2);
        boostPerField.put("adress.country", (float) 3);
        boostPerField.put("lastname", (float) .1);
        return searchPerson(rolename,fields,boostPerField);
       
    }

    @Override
    public List<Person> findPersonByUserName(String username, int startNum, int maxNum) throws ParseException {
       
        final String[] fields = { "fk_userid.username" };
        Map<String, Float> boostPerField = new HashMap<String, Float>(4);
        boostPerField.put("fk_userid.username", (float) 5);
        boostPerField.put("lastname", (float) .2);
        return searchPerson(username,fields,boostPerField);
    }


    @Override
    public void reindex() {
       personDAO.reIndex();
       
    }

    @Override
    public List<Groups> findGroupsByLocation(String country, String city, String grouptype) throws ParseException{

          final String[] fields = { "adress.city" };

        Map<String, Float> boostPerField = new HashMap<String, Float>(4);
        boostPerField.put("adress.city", (float) 5);
        boostPerField.put("adress.country", (float) .5);
        return  searchGroups(city,fields,boostPerField);
        
    }

   


  

   



}
