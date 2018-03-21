package cashregister.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;
import java.util.List;

public class BaseDao {
    SessionFactory sessionFactory;

    public BaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public <T> T getById(final Class<T> type, final int id){
        Session session = sessionFactory.getCurrentSession();
        T result = session.get(type, id);
        return result;
    }

    @Transactional
    public <T> List<T> getAll(final Class<T> type) {
        Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    public <T> T save(final T object){
        Session session = sessionFactory.getCurrentSession();
        return (T) sessionFactory.getCurrentSession().save(object);
    }

    public <T> void saveOrUpdate(final T object){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
    }

    public void delete(final Object object){
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
