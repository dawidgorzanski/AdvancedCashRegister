package cashregister.dao;

import cashregister.hibernate.HibernateUtil;
import cashregister.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public class BaseDao {
    public <T> T getById(final Class<T> type, final int id){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        T result = session.get(type, id);
        tx.commit();
        return result;
    }

    public <T> List<T> getAll(final Class<T> type) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    public <T> T save(final T o){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return (T) sessionFactory.getCurrentSession().save(o);
    }

    public <T> void saveOrUpdate(final T o){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public void delete(final Object object){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        sessionFactory.getCurrentSession().delete(object);
    }
}
