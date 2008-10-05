package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.biblestudy.persistance.Verse;
import java.util.List;
import org.hibernate.Query;


public class BiblesessionDAO extends BaseDaoHibernate<Biblesession> implements IBiblesessionDAO {
	public  BiblesessionDAO() {
       setQueryClass(Biblesession.class);
    }
    public List<Verse> findBibleSessionVerses(long sessionid) {
       
       Query query = super.getSessionFactory().getCurrentSession().createQuery("select distinct v from Verse v join Biblesession b where b.id=:id");
       query.setLong("id", sessionid);
       return query.list();
        
        
    }

   
}