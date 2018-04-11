package cashregister.dao;

import cashregister.dao.interfaces.IProductDefinitionDao;
import cashregister.model.ProductDefinition;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import javax.transaction.Transactional;
import java.util.List;

public class ProductDefinitionDao extends BaseDao<ProductDefinition> implements IProductDefinitionDao {
    public ProductDefinitionDao() {
        super.setClass(ProductDefinition.class);
    }

    @Transactional
    public ProductDefinition getByBarcode(String barcode) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ProductDefinition.class);
        criteria.add(Restrictions.eq("barcode", barcode));
        List<ProductDefinition> results = criteria.list();
        if (results.size() > 0)
            return results.get(0);

        return null;
    }
}
