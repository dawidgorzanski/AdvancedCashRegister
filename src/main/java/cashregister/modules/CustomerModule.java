package cashregister.modules;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import cashregister.modules.interfaces.ICustomerModule;

import javax.swing.*;

public class CustomerModule implements ICustomerModule {
    private ICustomerDao customerDao;

    public CustomerModule(ICustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public boolean addCustomer(Customer customer) {
        customerDao.save(customer);

        //TODO sprawdzanie czy kod kreskowy nie jest uzyty
        return true;
    }


    public Customer getCustomerByBarcode(String barcode) {
        return customerDao.getCustomerByBarcode(barcode);
    }
}
