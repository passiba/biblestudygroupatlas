package fi.passiba.groups;
/*import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Status;
import fi.passiba.services.persistance.UserRole;
import fi.passiba.services.persistance.Users;*/
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Status;
import fi.passiba.services.persistance.Users;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public final class PersionServiceImpTest implements InitializingBean,
    ApplicationContextAware {

	private SessionFactory sessionFactory;

  private ApplicationContext context;

  public PersionServiceImpTest() {
  }

  public void afterPropertiesSet() throws Exception {

    // setup database
    LocalSessionFactoryBean sessionFactoryBean = findSessionFactoryBean(context);
    sessionFactoryBean.createDatabaseSchema();

    Session session = null;
    Transaction tx = null;
    try {

      session = sessionFactory.openSession();
      tx = session.beginTransaction();

     /// fill with test data
     Status stat = new Status();
      stat.setStatusname("Aktiivinen");
      session.save(stat);

      Status stat2 = new Status();
      stat2.setStatusname("Ei Aktiivinen");
      session.save(stat2);
      Adress ad= new Adress();
      ad.setAddr1("Testikuja 1 b");
      ad.setAddr2("PL 39");
      ad.setCity("Helsinki");
      ad.setCountry("Finland");
      ad.setPhone("0505555555");
      ad.setState("Uusimaa");
      
     
   
      
      Person person= new Person();
      person.setAdress(ad);
     
      person.setEmail("testuser@hotmail.com");
      person.setFirstname("paul");
      person.setLastname("Geronimo");
      person.setDateofbirth(new Date());
      
      
      Users regularUser = new Users();
      regularUser.setUsername("passiba");
      regularUser.setPassword("leppis");
      regularUser.setRolename("User");
      regularUser.setStatus("Aktiivinen");
      person.setFk_userid(regularUser);
      session.save(person);
      
      //session.save(regularUser);


      tx.commit();

    } catch (Exception e) {
      tx.rollback();
    } finally {
      session.close();
    }
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void setApplicationContext(
      ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  private LocalSessionFactoryBean findSessionFactoryBean(
      ApplicationContext context) {
    Map beans = context.getBeansOfType(LocalSessionFactoryBean.class);
    if (beans.size() > 1) {
      throw new IllegalStateException(
          "more than one local session factory bean found");
    } else if (beans.size() == 0) {
      throw new IllegalStateException(
          "session factory bean not found");
    }
    return (LocalSessionFactoryBean) beans.values().iterator().next();
  }
}
