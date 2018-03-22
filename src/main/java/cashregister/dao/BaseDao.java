package cashregister.dao;

import cashregister.dao.interfaces.IBaseDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public abstract class BaseDao<T> implements IBaseDao<T> {
    @Autowired
    SessionFactory sessionFactory;
    Class<T> classToSet;

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public final void setClass(Class<T> classToSet ){
        this.classToSet = classToSet;
    }

    @Transactional
    public T getById(final int id){
        Session session = sessionFactory.getCurrentSession();
        T result = session.get(classToSet, id);
        return result;
    }

    @Transactional
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(classToSet);
        return crit.list();
    }

    @Transactional
    public T save(final T object){
        Session session = sessionFactory.getCurrentSession();
        return (T) sessionFactory.getCurrentSession().save(object);
    }

    @Transactional
    public void saveOrUpdate(final T object){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
    }

    @Transactional
    public void delete(final Object object){
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
