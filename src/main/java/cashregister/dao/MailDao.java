package cashregister.dao;

import cashregister.dao.interfaces.IMailDao;
import cashregister.model.Mail;

/**
 * Data Access Object for Mail class.
 */
public class MailDao extends BaseDao<Mail> implements IMailDao {
    public MailDao()
    {
        super.setClass(Mail.class);
    }
}
