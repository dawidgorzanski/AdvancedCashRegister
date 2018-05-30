package cashregister.modules;

import cashregister.dao.CustomerDao;
import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CustomerModuleTest {

    @Mock
    Customer customer1;

    @Mock
    Customer customer2;

    @Mock
    Customer customer3;

    @Mock
    CustomerDao customerDaoMock;

    ICustomerModule customerModule;
    List<Customer> customerList;

    @Before
    public void setUp(){
        //customerModule = new CustomerModule(customerDaoMock);
        customerDaoMock = new CustomerDao();
        customerList = new ArrayList<Customer>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
    }

    @Test
    public void doesGetAllCustomersMethodReturnsAllCustomersTest(){

        Mockito.when(customerDaoMock.getAll()).thenReturn(customerList);

        Mockito.verify(customerDaoMock).getAll().containsAll(customerList);

    }

}
