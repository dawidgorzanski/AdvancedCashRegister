package cashregister.dao.interfaces;

import cashregister.model.User;
import javafx.collections.ObservableList;

public interface IUserDao extends IBaseDao<User> {
    User getUserByUserNameAndPassword(String username, String password);
    User getUserByUserName(String username);
}
