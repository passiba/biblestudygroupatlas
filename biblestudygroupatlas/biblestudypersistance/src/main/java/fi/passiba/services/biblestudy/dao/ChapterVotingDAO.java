package fi.passiba.services.biblestudy.dao;


import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.ChapterVoting;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;



public class ChapterVotingDAO extends BaseDaoHibernate<ChapterVoting> implements IChapterVotingDAO {
	public ChapterVotingDAO() {
       setQueryClass(ChapterVoting.class);
    }
   
    public List<ChapterVoting> findTopRatedChapters() {
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.setProjection(Projections.projectionList().add(Projections.max("totalscore"))
                .add(Projections.max("numberofvotes"))
                );
        crit.setMaxResults(10);
        return crit.list();
    }


    public ChapterVoting findRatingByChapterid(long id) {
        ChapterVoting rating=null;
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.createCriteria("chapter").add(Restrictions.eq("id", id));
        crit.setMaxResults(1);
        for(Iterator iter=crit.list().iterator();iter!=null && iter.hasNext();)
        {
           rating=(ChapterVoting )iter.next();
        }
        return rating;
    }
}