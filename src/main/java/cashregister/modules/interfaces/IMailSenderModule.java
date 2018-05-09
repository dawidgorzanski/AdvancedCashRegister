package cashregister.modules.interfaces;

import cashregister.model.Mail;
import cashregister.model.Receipt;

public interface IMailSenderModule {
    Mail createMail(Receipt receipt, String paperReceipt);
    void sendMail(Mail mail);
}
