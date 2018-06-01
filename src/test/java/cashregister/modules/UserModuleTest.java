package cashregister.modules;

import cashregister.dao.interfaces.IUserDao;
import cashregister.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserModuleTest {

    @Mock
    IUserDao userDaoMock;

    @Mock
    User user1, user2, user3;

    UserModule userModule;

    @Before
    public void setUp(){
        userModule = new UserModule(userDaoMock);
    }

    @Test
    public void getAllUserMethodReturnsAllCustomerFromDatabaseTest(){
        List<User> usersList = new ArrayList<User>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
        Mockito.when(userDaoMock.getAll()).thenReturn(usersList);

        List<User> usersReceivedFromMethod = userModule.getAllUsers();

        Assert.assertEquals(usersReceivedFromMethod,usersList);
    }

    @Test
    public void getUserByUserNameMethodReturnsUserFromDatabaseTest(){
        final String username = "user";
        Mockito.when(userDaoMock.getUserByUserName(username)).thenReturn(user1);

        User userReceivedFromMethod = userModule.getUserByUserName(username);

        Assert.assertEquals(userReceivedFromMethod,user1);
    }

    @Test
    public void addUserMethodAddsUserToDatabaseTest(){
        List<User> usersList = new ArrayList<User>();
        usersList.add(user1);

        Mockito.doAnswer( (Answer) invocation -> {
            User arg0 = invocation.getArgumentAt(0,User.class);
            usersList.add(arg0);
            return null;
        }).when(userDaoMock).saveOrUpdate(Matchers.anyObject());

        userModule.addUser(user2);

        Assert.assertEquals(usersList.get(1),user2);
    }

    @Test
    public void deleteUserMethodDeletesUserFromDatabase(){
        List<User> usersList = new ArrayList<User>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);

       Mockito.doAnswer( (Answer) invocation -> {
            User arg0 = invocation.getArgumentAt(0,User.class);
            usersList.remove(arg0);
            return null;
        }).when(userDaoMock).delete(Matchers.anyObject());

    userModule.deleteUser(user1);

    Assert.assertTrue( usersList.size() == 2 );

        userModule.deleteUser(user3);

        Assert.assertTrue( usersList.size() == 1 );

    }
}