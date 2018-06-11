package cashregister.dao;

import cashregister.dao.interfaces.ICustomerDao;
import cashregister.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Object for Customer class.
 */
public class CustomerDao extends BaseDao<Customer> implements ICustomerDao {

    public CustomerDao() {super.setClass(Customer.class);}

    /**
     * Returns Customer by barcode.
     * @param barcode Barcode string.
     * @return Customer or null value if barcode not exists.
     */
    @Transactional
    public Customer getCustomerByBarcode(String barcode) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        criteria.add(Restrictions.eq("barcode", barcode));
        List<Customer> results = criteria.list();
        if (results.size() > 0)
            return results.get(0);

        return null;
    }

    /**
     * Returns Customers by name, barcode or mail.
     * @param name Value to search.
     * @return List of Customers which match searching criteria.
     */
    @Transactional
    public ObservableList<Customer> getByNameBarcodeOrMail(String name) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        Criterion criterion1 = Restrictions.like("name", "%"+name+"%").ignoreCase();
        Criterion criterion2 = Restrictions.like("barcode", "%" + name + "%").ignoreCase();
        Criterion criterion3 = Restrictions.like("mail", "%" + name + "%").ignoreCase();
        criteria.add(Restrictions.or(criterion1, criterion2, criterion3));
        List<Customer> results = criteria.list();
        ObservableList<Customer> resultsList = FXCollections.observableArrayList(results);
        return resultsList;
    }
}
