package cashregister.dao;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Receipt;

/**
 * Data Access Object for Receipt class.
 */
public class ReceiptDao extends BaseDao<Receipt> implements IReceiptDao {
    public ReceiptDao()
    {
        super.setClass(Receipt.class);
    }
}
