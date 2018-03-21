package cashregister.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration  configuration = new Configuration().configure("/hibernate.cfg.xml");
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        getSessionFactory().close();
    }

}

/*
        SessionFactory sessionFactory = new Configuration()
                .configure("/resources/hibernate.cfg.xml").buildSessionFactory();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Person person = (Person) session.load(Person.class, new Integer(1));
        if(person != null)
            System.out.println(person.getName());

        session.close();
        sessionFactory.close();

 */