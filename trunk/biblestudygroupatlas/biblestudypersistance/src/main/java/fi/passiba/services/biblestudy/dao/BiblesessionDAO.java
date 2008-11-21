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
    

   
}