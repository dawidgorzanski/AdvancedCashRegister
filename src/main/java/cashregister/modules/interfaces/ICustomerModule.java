package cashregister.modules.interfaces;

import cashregister.model.Customer;
import javafx.collections.ObservableList;

public interface ICustomerModule {
    boolean addCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    Customer getCustomerByBarcode(String barcode);
    ObservableList<Customer> getAllCustomers();
    ObservableList<Customer> getByNameBarcodeOrMail(String name);
}
