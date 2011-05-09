package fi.passiba.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtility {

	private static final SessionFactory sessionFactory;

    static {
        try {

            AnnotationConfiguration config = new AnnotationConfiguration();
            config.configure();
            config.setInterceptor(new AuditInterceptor());
            sessionFactory = config.buildSessionFactory();
            
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
