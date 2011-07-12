package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Biblesession;


public class BiblesessionDAO extends BaseDaoHibernate<Biblesession> implements IBiblesessionDAO {
	public  BiblesessionDAO() {
       setQueryClass(Biblesession.class);
    }
    

   
}