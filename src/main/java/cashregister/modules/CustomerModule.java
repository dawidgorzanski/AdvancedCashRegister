package cashregister.modules;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * Realizes operations on customer
 */
public class CustomerModule implements ICustomerModule {
    private ICustomerDao customerDao;

    /**
     * Initializes instance with ICustomerDao object passed as argument
     * @param customerDao ICustomerDao object that allows to operate on customers saved in database
     */
    public CustomerModule(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Creates ObservableList from all customers from db
     * @return created ObservableList object
     */
    public ObservableList<Customer> getAllCustomers()
    {
        List<Customer> customers = customerDao.getAll();
        ObservableList<Customer> customersList = FXCollections.observableArrayList(customers);
        return customersList;
    }

    /**
     * Adds customer to database
     * @param customer Customer object to add
     * @return true if success, false otherwise
     */
    public boolean addCustomer(Customer customer) {
        customerDao.saveOrUpdate(customer);

        //TODO sprawdzanie czy kod kreskowy nie jest uzyty
        return true;
    }

    /**
     * Deletes customer from database
     * @param customer Customer object to delete
     */
    public void deleteCustomer(Customer customer) { customerDao.delete(customer);}

    /**
     * Gets Customer by corresponding to him barcode
     * @param barcode String that determines Customer
     * @return desirable Customer object
     */
    public Customer getCustomerByBarcode(String barcode) {
        return customerDao.getCustomerByBarcode(barcode);
    }

    /**
     * Returns Customer list searching by name, barcode or mail.
     * @param name name, barcode or mail to search customers with
     * @return desirable CustomerList
     */
    public ObservableList<Customer> getByNameBarcodeOrMail(String name)
    {
        ObservableList<Customer> prooductsList = customerDao.getByNameBarcodeOrMail(name);
        return prooductsList;
    }
}
