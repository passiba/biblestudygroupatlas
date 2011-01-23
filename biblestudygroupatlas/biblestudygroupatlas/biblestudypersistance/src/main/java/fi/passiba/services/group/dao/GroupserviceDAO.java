package fi.passiba.services.group.dao;



import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.group.persistance.Groupservice;



public class GroupserviceDAO extends BaseDaoHibernate<Groupservice>  implements IGroupserviceDAO {
   public GroupserviceDAO() {
       setQueryClass(Groupservice.class);
    }

    
}