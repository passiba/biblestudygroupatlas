package fi.passiba.services.biblestudy.datamining.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;


public class BookDatasouceDAO extends BaseDaoHibernate<Bookdatasource> implements IBookDatasouceDAO {
	public BookDatasouceDAO() {
       setQueryClass(Bookdatasource.class);
    }

    public List<Bookdatasource> findBookDataSourcesByStatus(String status) {
        
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.add(Restrictions.eq("status", status));
        return crit.list();
    }

}