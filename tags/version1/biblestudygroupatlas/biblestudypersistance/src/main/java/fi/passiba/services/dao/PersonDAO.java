package fi.passiba.services.dao;



import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.persistance.Person;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


public class PersonDAO extends BaseDaoHibernate<Person> implements IPersonDAO {
    public PersonDAO() {
        setQueryClass(Person.class);
    }

    public List<Person>  findPersonByUsername(String username) {
      Query query=super.getSessionFactory().getCurrentSession().createQuery("select distinct p from Person p join p.fk_userid u where u.username=:user");
      query.setMaxResults(1);
      query.setString("user", username);
      return query.list();
    }

    public List<Person> findPersonByUserRole(String rolename,String country,String city) {
      /*Query query=super.getSessionFactory().getCurrentSession().createQuery("select distinct p from Person p join p.fk_userid u where u.rolename=:role");
      query.setMaxResults(1);
      query.setString("role", rolename);
      List<Person> result=query.list();
      return result;*/
        
        Criteria crit = super.getSessionFactory().getCurrentSession().createCriteria(getQueryClass());
        crit.createCriteria("fk_userid").add(Restrictions.eq("rolename", rolename));
        crit.createCriteria("adress").add(Restrictions.eq("country", country)).add(Restrictions.eq("city", city));
        return crit.list();

    }

    public List<Biblesession> findBibleSessionsByPersonId(long personid) {
       Query query = super.getSessionFactory().getCurrentSession().createQuery("select distinct b from Biblesession b join Person p where p.id=:id");
       query.setLong("id", personid);
       return query.list();
    }

  

	
}