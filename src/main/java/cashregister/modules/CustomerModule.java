package cashregister.modules;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class CustomerModule implements ICustomerModule {
    private ICustomerDao customerDao;

    public CustomerModule(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public ObservableList<Customer> getAllCustomers()
    {
        List<Customer> customers = customerDao.getAll();
        ObservableList<Customer> customersList = FXCollections.observableArrayList(customers);
        return customersList;
    }

    public boolean addCustomer(Customer customer) {
        customerDao.saveOrUpdate(customer);

        //TODO sprawdzanie czy kod kreskowy nie jest uzyty
        return true;
    }

    public void deleteCustomer(Customer customer) { customerDao.delete(customer);}


    public Customer getCustomerByBarcode(String barcode) {
        return customerDao.getCustomerByBarcode(barcode);
    }

    public ObservableList<Customer> getByNameBarcodeOrMail(String name)
    {
        ObservableList<Customer> prooductsList = customerDao.getByNameBarcodeOrMail(name);
        return prooductsList;
    }
}
