package cashregister.modules;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.dao.interfaces.IUserDao;
import cashregister.model.Customer;
import cashregister.model.User;
import cashregister.modules.interfaces.ICustomerModule;
import cashregister.modules.interfaces.IUserModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModule implements IUserModule {
    private IUserDao userDao;

    public UserModule(IUserDao userDao) {
        this.userDao = userDao;
    }

    public ObservableList<User> getAllUsers()
    {
        List<User> users = userDao.getAll();
        ObservableList<User> usersList = FXCollections.observableArrayList(users);
        return usersList;
    }

    public User getUserByUserName(String username){ return userDao.getUserByUserName(username); }

    public boolean addUser(User user) {
        userDao.saveOrUpdate(user);

        return true;
    }

    public void deleteUser(User user)
    {
        userDao.delete(user);
    }

}
