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

    /**
     * initializes instance with IUserDao object
     * @param userDao IUserDao object that allows to operate on users in database
     */
    public UserModule(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * returns ObservableList initialized with all users
     * @return desirable ObservableList object
     */
    public ObservableList<User> getAllUsers()
    {
        List<User> users = userDao.getAll();
        ObservableList<User> usersList = FXCollections.observableArrayList(users);
        return usersList;
    }

    /**
     * returns User with username passed as argument
     * @param username desired username
     * @return desirable User object
     */
    public User getUserByUserName(String username){ return userDao.getUserByUserName(username); }

    /**
     * adds user to database
     * @param user User object to add
     * @return true if success, false otherwise
     */
    public boolean addUser(User user) {
        userDao.saveOrUpdate(user);

        return true;
    }

    /**
     * deletes user from database
     * @param user User to delete
     */
    public void deleteUser(User user)
    {
        userDao.delete(user);
    }

}
