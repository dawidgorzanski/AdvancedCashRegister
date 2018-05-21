package cashregister.modules.interfaces;

import cashregister.model.Customer;
import cashregister.model.User;
import javafx.collections.ObservableList;

public interface IUserModule {
    boolean addUser(User user);
    void deleteUser(User user);
    ObservableList<User> getAllUsers();
    User getUserByUserName(String username);

}
