package fi.passiba.services.biblestudy.datamining.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;


public class BookDatasouceDAO extends BaseDaoHibernate<Bookdatasource> implements IBookDatasouceDAO {
	public BookDatasouceDAO() {
       setQueryClass(Bookdatasource.class);
    }
}