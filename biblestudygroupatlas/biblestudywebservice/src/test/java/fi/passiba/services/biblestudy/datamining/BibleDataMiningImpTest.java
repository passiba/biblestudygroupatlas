/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.biblestudy.datamining;

import fi.passiba.biblestudy.services.datamining.BibleDataMiningImp;
import fi.passiba.hibernate.HibernateUtility;
import fi.passiba.services.biblestudy.datamining.dao.BookDatasouceDAO;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author haverinen
 */
public class BibleDataMiningImpTest extends TestCase {
    
    
    private SessionFactory sessionFactory;
    private BibleDataMiningImp service = null;
    private BookDatasouceDAO dao = null;
    
    private Session session = null;
    
    public BibleDataMiningImpTest(String testName) {
        super(testName);
    }            
    
    protected void setUp() throws Exception {
        try {
            sessionFactory = HibernateUtility.getSessionFactory();
            dao = new BookDatasouceDAO();
            dao.setSessionFactory(sessionFactory);

            service = new BibleDataMiningImp();
            service.setDatasourceDAO(dao);
            session=sessionFactory.openSession();
        }
        finally {
            session.beginTransaction();
           // sessionFactory.getCurrentSession().beginTransaction();
        }
    }

   
    protected void tearDown() throws Exception {
         try {
            dao = null;
            service = null;
            
        }
        finally {
           /* Session session = sessionFactory.getCurrentSession();
            session.close();*/
            session.getTransaction().commit();
            session.close();
        }
    }
    
    protected void flushSession() {
        sessionFactory.getCurrentSession().flush();
    }

    protected void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }
    
    
    public void testRetrieveBibleData() {
        service.retrieveBookdata();
    }

    

}
