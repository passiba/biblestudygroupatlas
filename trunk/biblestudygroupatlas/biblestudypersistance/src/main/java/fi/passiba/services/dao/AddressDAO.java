package fi.passiba.services.dao;



import fi.passiba.hibernate.BaseDaoHibernate;
import fi.passiba.services.persistance.Adress;



public class AddressDAO extends BaseDaoHibernate<Adress> implements IAddressDAO {
    public AddressDAO() {
        setQueryClass(Adress.class);
    }
}