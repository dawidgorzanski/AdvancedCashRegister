package cashregister.dao;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.transaction.Transactional;
import java.util.List;

public class CustomerDao extends BaseDao<Customer> implements ICustomerDao {

    public CustomerDao() {super.setClass(Customer.class);}

    @Transactional
    public Customer getCustomerByBarcode(String barcode) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("barcode", barcode));
        List<Customer> results = criteria.list();
        if (results.size() > 0)
            return results.get(0);

        return null;
    }

    @Transactional
    public ObservableList<Customer> getByName(String name) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.like("name", "%"+name+"%").ignoreCase());
        List<Customer> results = criteria.list();
        ObservableList<Customer> resultsList = FXCollections.observableArrayList(results);
        return resultsList;
    }
}
