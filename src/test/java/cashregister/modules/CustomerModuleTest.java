package cashregister.modules;

import cashregister.dao.CustomerDao;
import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomerModuleTest {

    @Mock
    Customer customer1, customer2, customer3;

    @Mock
    CustomerDao customerDaoMock;

    @InjectMocks
    ICustomerModule customerModule = new CustomerModule(customerDaoMock);

    @Test
    public void getAllCustomersMethodReturnsAllCustomersFromDatabaseTest(){

        //Arrange
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        Mockito.when(customerDaoMock.getAll()).thenReturn(customerList);

        //Act
        List<Customer> customersFromFunction = customerModule.getAllCustomers();

        //Assert
        Assert.assertEquals(customersFromFunction, customerList);
    }

    @Test
    public void addCustomerMethodAddsCustomerToDatabaseTest(){
        //Arrange
        List<Customer> customerList = new ArrayList<Customer>();

        Mockito.doAnswer( (Answer) invocation -> {
            Customer arg0 = invocation.getArgumentAt(0,Customer.class);
            customerList.add(arg0);
            return null;
        }).when(customerDaoMock).saveOrUpdate(Matchers.anyObject());

        //Act
        customerModule.addCustomer(customer1);
        customerModule.addCustomer(customer2);

        //Assert
        Assert.assertTrue(customerList.size() == 2  );
        customerModule.addCustomer(customer3);
        Assert.assertTrue(customerList.size() == 3  );
    }

    @Test
    public void addCustomerMethodReturnsTrueWhenCustomerIsAddedToDatabaseTest(){
        Assert.assertTrue(customerModule.addCustomer(customer1));
    }

    @Test
    public void deleteCustomerMethodDeletesCustomerFromDatabaseTest(){

        //Arrange
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);

        Mockito.doAnswer( (Answer)invocation-> {
            customerList.remove(invocation.getArgumentAt(0,Customer.class));
            return null;
        }).when(customerDaoMock).delete(Matchers.anyObject());

        //Act
        customerModule.deleteCustomer(customer2);
        //Assert
        Assert.assertTrue(customerList.size() == 2);
    }

}
