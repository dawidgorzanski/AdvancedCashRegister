package cashregister.dao;

import cashregister.dao.interfaces.IReceiptDao;
import cashregister.model.Receipt;

public class ReceiptDao extends BaseDao<Receipt> implements IReceiptDao {
    public ReceiptDao()
    {
        super.setClass(Receipt.class);
    }
}
