package cashregister.modules.interfaces;

import cashregister.model.Customer;

public interface ICustomerModule {
    boolean addCustomer(Customer customer);
    Customer getCustomerByBarcode(String barcode);
}
