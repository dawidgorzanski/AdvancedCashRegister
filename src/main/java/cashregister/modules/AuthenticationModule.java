package cashregister.modules;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import cashregister.modules.interfaces.IAuthenticationModule;

public class AuthenticationModule implements IAuthenticationModule {
    private IUserDao userDao;
    private User loggedUser;

    /**
     * Initializes instance with IUserDao object
     * @param userDao allows to operator with User objects on database
     */
    public AuthenticationModule(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Logs in user with username and password passed
     * @param username username of User
     * @param password password of User
     * @return true if operation is succeeded, false otherwise
     */
    public boolean login(String username, String password) {
        User user = userDao.getUserByUserNameAndPassword(username, password);
        if (user == null)
            return false;

        this.loggedUser = user;
        return true;
    }

    /**
     * Logs out current user
     * @return false if there is no user currently logged in, true otherwise
     */
    public boolean logout(){
        if (this.loggedUser == null)
            return false;

        this.loggedUser = null;
        return true;
    }

    /**
     * Returns currently logged in User
     * @return logged in user object
     */
    public User getLoggedUser() {
        return this.loggedUser;
    }

    /**
     * Checks if User is logged in
     * @param user user to check
     * @return true if user is logged in, false otherwise
     */
    public boolean isLogged(User user) {
        return user.getId() == getLoggedUser().getId();
    }

}
