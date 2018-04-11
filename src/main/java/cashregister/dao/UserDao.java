package cashregister.dao;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;

public class UserDao extends BaseDao<User> implements IUserDao {
    public UserDao() {
        super.setClass(User.class);
    }
}
