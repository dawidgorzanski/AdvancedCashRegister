package cashregister.modules;

import cashregister.dao.UserDao;
import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import javafx.css.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationModuleTest {

    @Mock
    IUserDao userDaoMock;

    @Mock
    User user1;

    AuthenticationModule authenticationModule;

    @Before
    public void setUp(){
        authenticationModule = new AuthenticationModule(userDaoMock);
    }

    @Test
    public void getLoggedUserReturnsCorrectUserTest(){
        final String username = "user";
        final String password = "pswd";
        Mockito.when(userDaoMock.getUserByUserNameAndPassword(username,password)).thenReturn(user1);
        authenticationModule.login(username,password);
        Assert.assertTrue(authenticationModule.getLoggedUser().equals(user1));
    }

    @Test
    public void loginMethodIsFlagSetCorrectlyWhenUsernameAndPasswordAreCorrectTest(){
        final String username = "user";
        final String password = "pswd";
        Mockito.when(userDaoMock.getUserByUserNameAndPassword(username,password)).thenReturn(user1);

        boolean flag = authenticationModule.login(username,password);

        Assert.assertTrue(flag);
    }

    @Test
    public void loginMethodIsFlagSetCorrectlyWhenUsernameOrPasswordIsIncorrectTest(){
        final String username = "user";
        final String password = "pswd";
        Mockito.when(userDaoMock.getUserByUserNameAndPassword(username,password)).thenReturn(user1);

        boolean flag = authenticationModule.login("error",password);
        Assert.assertFalse(flag);

        flag = authenticationModule.login(username,"error");
        Assert.assertFalse(flag);
    }

    @Test
    public void logoutMethodIsLoggingOutNecessaryTest(){
        Assert.assertFalse(authenticationModule.logout());
    }

    @Test
    public void logoutMethodIsUserLoggedOutWhenNecessaryTest(){
        final String username = "user";
        final String password = "pswd";
        Mockito.when(userDaoMock.getUserByUserNameAndPassword(username,password)).thenReturn(user1);
        authenticationModule.login(username,password);
        Assert.assertTrue(authenticationModule.logout());
    }

}
