package cashregister.modules.interfaces;

import cashregister.model.User;

public interface IAuthenticationModule {
    boolean login(String username, String password);
    boolean logout();
    User getLoggedUser();
    boolean isLogged(User user);
}
