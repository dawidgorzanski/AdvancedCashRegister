package cashregister.modules;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import cashregister.modules.interfaces.IAuthenticationModule;

public class AuthenticationModule implements IAuthenticationModule {
    private IUserDao userDao;
    private User loggedUser;

    public AuthenticationModule(IUserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(String username, String password) {
        User user = userDao.getUserByUserNameAndPassword(username, password);
        if (user == null)
            return false;

        this.loggedUser = user;
        return true;
    }

    public boolean logout(){
        if (this.loggedUser == null)
            return false;

        this.loggedUser = null;
        return true;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public boolean isLogged(User user) {
        return user.getId() == getLoggedUser().getId();
    }

}
