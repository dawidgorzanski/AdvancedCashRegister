package cashregister.dao;

import cashregister.dao.interfaces.IMailDao;
import cashregister.model.Mail;

public class MailDao extends BaseDao<Mail> implements IMailDao {
    public MailDao()
    {
        super.setClass(Mail.class);
    }
}
