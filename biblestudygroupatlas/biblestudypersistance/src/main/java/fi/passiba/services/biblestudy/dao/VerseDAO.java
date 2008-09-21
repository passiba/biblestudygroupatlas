package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Verse;
import java.util.List;
import org.hibernate.Query;


public class VerseDAO extends BaseDaoHibernate<Verse> implements IVerseDAO {
	public VerseDAO() {
       setQueryClass(Verse.class);
    }

    public List<Verse> findVerseByVerseidBookIdChapterIdSectionIdTranslationId(long translationid, long sectionid, long bookid, long chapterid, long verseid) {
         Query query=super.getSessionFactory().getCurrentSession().createQuery("select distinct v from Verse v join v.chapter c.book b join b.booksection s join s.bibletranslation t where t.id=:translationid and s.id=:sectionid and b.id=:bookid and c.id=:chapterid and v.id=:verseid");
      query.setMaxResults(1);
      query.setLong("translationid", translationid);
      query.setLong("sectionid", sectionid);
      query.setLong("bookid", bookid);
      query.setLong("chapterid",  chapterid);
       query.setLong("chapterid",  verseid);
      List<Verse> result=query.list();
      return result;
    }
}