package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Book;
import java.util.List;
import org.hibernate.Query;

public class BookDAO extends BaseDaoHibernate<Book> implements
        IBookDAO {

    public BookDAO() {
         setQueryClass(Book.class);
    }

    public List<Book>  findBooksByBookIDSectionIDandTranslationId(long translationid, long sectionid, long bookid) {
       
        
      Query query=super.getSessionFactory().getCurrentSession().createQuery("select distinct b from Book b join b.booksection s join s.bibletranslation t where t.id=:translationid and s.id=:sectionid and b.id=:bookid");
      query.setMaxResults(1);
      query.setLong("translationid", translationid);
      query.setLong("sectionid", sectionid);
      query.setLong("bookid", bookid);
      List<Book> result=query.list();
      return result;
        
        
    }

    
}
