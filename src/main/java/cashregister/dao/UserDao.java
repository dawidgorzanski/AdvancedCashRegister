package cashregister.dao;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Object for User class.
 */
public class UserDao extends BaseDao<User> implements IUserDao {
    public UserDao() {
        super.setClass(User.class);
    }

    /**
     * Returns User by username and password.
     * @param username UserName
     * @param password Password
     * @return User
     */
    @Transactional
    public User getUserByUserNameAndPassword(String username, String password){
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        criteria.add(Restrictions.eq("password", password));
        List<User> results = criteria.list();
        if (results.size() > 0)
            return results.get(0);

        return null;
    }

    /**
     * Returns User by username.
     * @param username UserName
     * @return User
     */
    @Transactional
    public User getUserByUserName(String username){
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("name", username));
        List<User> results = criteria.list();
        if (results.size() > 0)
            return results.get(0);

        return null;
    }
}
