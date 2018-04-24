package cashregister.dao.interfaces;

import cashregister.model.User;

public interface IUserDao extends IBaseDao<User> {
    User getUserByUserNameAndPassword(String username, String password);
}
