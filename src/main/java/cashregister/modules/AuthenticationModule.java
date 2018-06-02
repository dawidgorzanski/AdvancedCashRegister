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
        //System.out.println(""+user.getName()+user.getPassword()+user.getIsAdmin()+user.getId()+"\n");
        //System.out.println(""+getLoggedUser().getName()+getLoggedUser().getPassword()+getLoggedUser().getIsAdmin()+getLoggedUser().getId()+"\n");

        return user.getName().equals(getLoggedUser().getName()) && user.getPassword().equals(getLoggedUser().getPassword()) && user.getIsAdmin() == getLoggedUser().getIsAdmin() && user.getId() == getLoggedUser().getId();
        //I dont know why it doesnt work when returned as below
        //return getLoggedUser().equals(userDao.getUserByUserNameAndPassword(user.getName(), user.getPassword()));

    }

}
