package cashregister.dao;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;

public class CustomerDao extends BaseDao<Customer> implements ICustomerDao{

    public CustomerDao() {super.setClass(Customer.class);}
}
