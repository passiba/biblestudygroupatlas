package fi.passiba.services.group.dao;




import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.hibernate.PaginationInfo;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Adress;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class GroupsDAO extends BaseDaoHibernate<Groups> implements IGroupsDAO {
    public GroupsDAO() {
           setQueryClass(Groups.class);
    }

    public List<Groups> findGroupsByLocation(String country, String city, String grouptype) {
        
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        if(grouptype!=null)
        {
            crit.add(Restrictions.eq("grouptypename", grouptype));
        }
        crit.createCriteria("adress").add(Restrictions.eq("country", country)).add(Restrictions.eq("city", city));
        return crit.list();
    }

    public List<Person> findGroupsPersonsByGroupId(Long id) {
        Query query = super.getSessionFactory().getCurrentSession().createQuery("select distinct p from Person p join p.groups g where g.id=:id");

        query.setLong("id", id);
        return query.list();
    }


    public List<Groups> findGroupsByPersonId(Long id) {
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.createCriteria("grouppersons").add(Restrictions.eq("id", id));
        return crit.list();
    }

    public PaginationInfo findPagingInfoForGroups(int maxResult) {
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        PaginationInfo pageInfo= new PaginationInfo(crit,0,maxResult);
        pageInfo.setCount(super.getCountAll());
        pageInfo.setMaxResult(maxResult);
        return pageInfo;
    }

    public List<Biblesession> findBibleSessionsByGroupId(long groupid) {
       Query query = super.getSessionFactory().getCurrentSession().createQuery("select distinct b from Biblesession b join Groups g where g.id=:id");
       query.setLong("id", groupid);
       return query.list();
    }

    public void deleteGroupPersonFromGroup(long personid,long groupid) {
       SQLQuery query= super.getSessionFactory().getCurrentSession().createSQLQuery("delete from groupperson where fk_person_id="+personid+ " and fk_group_id="+groupid);
       int i=query.executeUpdate();
    }

   
}