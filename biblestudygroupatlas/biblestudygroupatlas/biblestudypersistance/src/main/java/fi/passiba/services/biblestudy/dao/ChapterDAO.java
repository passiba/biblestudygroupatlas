package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Chapter;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;




public class ChapterDAO extends BaseDaoHibernate<Chapter> implements IChapterDAO {
	
    public ChapterDAO() {
         setQueryClass(Chapter.class);
    }

    public List<Chapter> findChapterByChapterIdBookIdSectionIdBibleVersionID(long translationid, long sectionid, long bookid, long chapterid) {
         Query query=super.getSessionFactory().getCurrentSession().createQuery("select distinct c from Chapter c join c.book b join b.booksection s join s.bibletranslation t where t.id=:translationid and s.id=:sectionid and b.id=:bookid and c.id=:chapterid");
      query.setMaxResults(1);
      query.setLong("translationid", translationid);
      query.setLong("sectionid", sectionid);
      query.setLong("bookid", bookid);
      query.setLong("chapterid",  chapterid);
      return query.list();
    }

    public List<Chapter> findChaptersByBookId(long id) {
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.createCriteria("book").add(Restrictions.eq("id", id));
        return crit.list();
    }

   
}