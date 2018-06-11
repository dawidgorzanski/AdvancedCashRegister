package cashregister.dao;

import cashregister.dao.interfaces.IBaseDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Abstract Data Access Object which implements basic functionalities for getting, saving, updating and deleting objects.
 * @param <T> Generic Parameter for class which is handled by specific DAO.
 */
public abstract class BaseDao<T> implements IBaseDao<T> {
    @Autowired
    SessionFactory sessionFactory;
    Class<T> classToSet;

    /**
     * Getter for Session Factory.
     * @return SessionFactory property.
     */
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Setter for Session Factory.
     * @param sessionFactory Session Factory to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Method for setting types of objects which should be returned from DAO.
     * @param classToSet Type of class which should be set for DAO object - must be the same like class passed to generic paramter.
     */
    public final void setClass(Class<T> classToSet ){
        this.classToSet = classToSet;
    }

    /**
     * Returns object with specified identifier.
     * @param id Object identifier.
     * @return Generic object
     */
    @Transactional
    public T getById(final int id){
        Session session = sessionFactory.getCurrentSession();
        T result = session.get(classToSet, id);
        return result;
    }

    /**
     * Returns all objects without lazy-loaded(reference) types.
     * @return List of generic objects.
     */
    @Transactional
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(classToSet);
        return crit.list();
    }

    /**
     * Saves object in the database.
     * @param object Generic object to save.
     * @return Saved generic object.
     */
    @Transactional
    public T save(final T object){
        Session session = sessionFactory.getCurrentSession();
        return (T) sessionFactory.getCurrentSession().save(object);
    }

    /**
     * Saves or updates(if exists) object in the database.
     * @param object Generic object to save or update.
     */
    @Transactional
    public void saveOrUpdate(final T object){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(object);
    }

    /**
     * Updates object in the database.
     * @param object Generic object to update.
     */
    @Transactional
    public void update(final T object){
        Session session = sessionFactory.getCurrentSession();
        session.update(object);
    }

    /**
     * Deletes object from database.
     * @param object Generic object to delete.
     */
    @Transactional
    public void delete(final Object object){
        Session session = sessionFactory.getCurrentSession();
        session.delete(object);
    }
}
