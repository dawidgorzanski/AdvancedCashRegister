package cashregister.modules;

import cashregister.model.Mail;
import cashregister.modules.interfaces.IMailSenderModule;

public class EmailRunnable implements Runnable {
    private IMailSenderModule mailSenderModule;
    private Mail mail;
    public EmailRunnable(IMailSenderModule mailSenderModule, Mail mail)
    {
        this.mailSenderModule = mailSenderModule;
        this.mail = mail;
    }
    public void run()
    {
        mailSenderModule.sendMail(mail);
    }
}
