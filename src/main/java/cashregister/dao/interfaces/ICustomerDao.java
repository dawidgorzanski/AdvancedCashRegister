package cashregister.dao.interfaces;

import cashregister.model.Customer;
import javafx.collections.ObservableList;

public interface ICustomerDao extends IBaseDao<Customer>{
    Customer getCustomerByBarcode(String barcode);
    ObservableList<Customer> getByName(String name);
}
