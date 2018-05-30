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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Before
    public void setUp(){

    }

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

}
