package cashregister.dao.interfaces;

import cashregister.model.Customer;

public interface ICustomerDao extends IBaseDao<Customer>{
    Customer getCustomerByBarcode(String barcode);
}
