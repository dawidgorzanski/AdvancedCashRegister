package cashregister.modules;

import cashregister.dao.CustomerDao;
import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.collections.FXCollections;
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
    ICustomerDao customerDaoMock;

    ICustomerModule customerModule;

    @Before
    public void setUp(){
        customerModule = new CustomerModule(customerDaoMock);
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
        customerModule.deleteCustomer(customer1);
        Assert.assertTrue(customerList.size() == 1);
    }

    @Test
    public void getCustomerByBarcodeReturnsCorrectCustomerTest(){
        //Arrange
        final String barcode = "barcode12345";
        Mockito.when(customerDaoMock.getCustomerByBarcode(barcode)).thenReturn(customer1);

        //Act
        Customer customerTest = customerModule.getCustomerByBarcode(barcode);

        //Assert
        Assert.assertTrue(customerTest.equals(customer1));
    }

    @Test
    public void getCustomerByNameReturnsCorrectCustomerTest(){
        final String name = "test";

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        ObservableList<Customer> customersList = FXCollections.observableArrayList(customers);

        Mockito.when(customerDaoMock.getByNameBarcodeOrMail(name)).thenReturn(customersList);

        Assert.assertTrue(customerModule.getByNameBarcodeOrMail(name) == customersList);
    }

    @Test
    public void getCustomerByNameReturnsCorrectListOfCustomersTest(){
        final String name = "test";

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        ObservableList<Customer> customersList = FXCollections.observableArrayList(customers);

        Mockito.when(customerDaoMock.getByNameBarcodeOrMail(name)).thenReturn(customersList);

        Assert.assertTrue(customerModule.getByNameBarcodeOrMail(name) == customersList);
        Assert.assertTrue(customerModule.getByNameBarcodeOrMail(name).size() == 2);
    }
}
