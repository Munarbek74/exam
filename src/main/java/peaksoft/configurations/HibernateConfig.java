package peaksoft.configurations;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
        private static final SessionFactory sessionFactory = buildSessionFactory();
        private static SessionFactory buildSessionFactory() {
            try{

                return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            }catch (Throwable ex) {
                System.out.println("Session not created " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
public static SessionFactory getSessionFactory(){
            return sessionFactory;
}

        public static void shutDown() {

            sessionFactory.close();
        }
    }





